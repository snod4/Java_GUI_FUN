import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;
import java.awt.Toolkit; 
import java.awt.event.MouseEvent;


public class DropDownBar extends ImageData {
    private HashMap<String, ArrayList<ImageData>> categories;
    private int count;
    private int overallWidth;
    private int overallHeight;
    private Color menuColor;
    private String visibleCategory = null;
    private static final int PIXEL_SIZE = 10;
    private static final int FONT_SIZE = (int) (PIXEL_SIZE * Toolkit.getDefaultToolkit().getScreenResolution() / 72.0);

    public DropDownBar(int width, int height, int x, int y, Color color) {
        super(new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB), x, y);
        categories = new HashMap<String, ArrayList<ImageData>>();
        count = 0;
        overallHeight = height;
        overallWidth = width;
        menuColor = color;
        this.setColor(menuColor);
        this.moveable = true;
        this.visible = true;

    }

    public void addCategory(String name) {
        count++;
        int width = overallWidth / count;

        ImageData newImage = new ImageData(new BufferedImage(width, overallHeight, BufferedImage.TYPE_INT_RGB),
        linkedImages.size() * width, 
        y, 
        name);

        newImage.setColor(menuColor);
        newImage.moveable = true;
        linkedImages.add(newImage);
        categories.put(name, new ArrayList<>());

        updateListWidth(linkedImages);
        for (int i = 0; i < linkedImages.size(); i++) {
            updateListWidth(categories.get(linkedImages.get(i).name));
            linkedImages.get(i).x = i * width;
        }
    }

    public void addSubcategoryItem(String category, String name) {
        ArrayList<ImageData> list = categories.get(category);

        if(list == null){
            throw new Error("This category does not exist");
        }

        int startX = 0;
        for(int i = 0; i < linkedImages.size(); i++){
            if(linkedImages.get(i).name.equals(category)){
                startX = linkedImages.get(i).x;
            }
        }

        ImageData newImage = new ImageData(new BufferedImage(overallWidth / count, 
        overallHeight, BufferedImage.TYPE_INT_RGB),
        startX,
        y + overallHeight * (list.size() + 1), 
        name);

        newImage.setColor(menuColor);
        list.add(newImage);


        Graphics2D g = newImage.image.createGraphics();
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, newImage.image.getWidth() - 1, newImage.image.getHeight() - 1);
        
        g.setFont(new Font("TimesRoman", Font.PLAIN, FONT_SIZE));
        g.setColor(Color.red);
        int strWidth = g.getFontMetrics().stringWidth(newImage.name);
        g.drawString(newImage.name, (newImage.image.getWidth() - strWidth)/2, (newImage.image.getHeight() - PIXEL_SIZE)/2 + PIXEL_SIZE);
        g.setColor(menuColor);

    }

    private void updateListWidth(ArrayList<ImageData> list) {
        ImageData item;
        for (int i = 0; !list.isEmpty() && i < list.size(); i++) {
            item = list.get(i);
            item.image = new BufferedImage(overallWidth / count, image.getHeight(), BufferedImage.TYPE_INT_RGB);
            item.setColor(menuColor);

            Graphics2D g = item.image.createGraphics();
            g.setColor(Color.BLACK);
            System.out.println("Drawing: " + i + " Category");
            g.drawRect(0, 0, item.image.getWidth() - 1, item.image.getHeight() - 1);

            g.setFont(new Font("TimesRoman", Font.PLAIN, FONT_SIZE));
            g.setColor(Color.red);
            int strWidth = g.getFontMetrics().stringWidth(item.name);
            g.drawString(item.name, (item.image.getWidth() - strWidth)/2, (item.image.getHeight() - PIXEL_SIZE)/2 + PIXEL_SIZE);
            g.setColor(menuColor);
        }
    }


    public void draw(Graphics g, ImageObserver observer){
        ArrayList<ImageData> subList;
        if(this.visible){  
            g.drawImage(this.image, this.x, this.y, observer);
        }
        
        for(int i = 0; i < linkedImages.size(); i++){
            ImageData item = linkedImages.get(i);
            g.drawImage(item.image, item.x, item.y, observer);
            if(visibleCategory != null){
                subList = categories.get(item.name);
                ImageData subItem;
                for(int a = 0; a < subList.size(); a++){
                    subItem = subList.get(a);
                    g.drawImage(subItem.image, subItem.x, subItem.y, observer);
                }
            }
         
        }
    }


    public void drawSelectedCategory(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();
        ImageData obj;
        
        for(int i = 0; i < linkedImages.size(); i++){
            obj = linkedImages.get(i);
            if(obj.mouseInside(e)){
                visibleCategory = obj.name;
                
            }
        }
        
    }

    public void deselectCategory(){
        visibleCategory = null;
    }

}
