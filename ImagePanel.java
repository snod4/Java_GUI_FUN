import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;

import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.LayoutManager;
import java.awt.Color;




public class ImagePanel extends JPanel {

    private ArrayList<ImageData> imgList;

    public ImagePanel(int width, int height){
        super(new BorderLayout());
        super.setSize(width,height);
        imgList = new ArrayList<ImageData>();
    }

    public ImagePanel(LayoutManager layout){
        
        super(layout);
        imgList = new ArrayList<ImageData>();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        BufferedImage thisImage;
        for (ImageData image : imgList) {
            if(image instanceof DropDownBar){
           //     System.out.println("Drawing Bar");
                DropDownBar dropDownBar = (DropDownBar) image;
                dropDownBar.draw(g, this);
                
            }
            else{
            thisImage = image.image;
            g.drawImage(thisImage, image.x, image.y, this);
            if(image.name != null){
                g.drawString(image.name, image.x, image.y);
            }
        }
            
        }

        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.setColor(Color.red);
        g.drawString("TEST", 0,0);

    }

    

    public ImageData addImage(BufferedImage img){
        ImageData item = new ImageData(img,0,0);
        imgList.add(item);
        return item; 
     }

    public ImageData addImage(BufferedImage img, int x, int y){
        ImageData item = new ImageData(img,x,y);
       imgList.add(item); 
       return item;
    }

    public ImageData addImageData(ImageData data){
        imgList.add(data);
        return data;
    }

    public ImageData returnImage(MouseEvent e){
        int imgX, imgY;
        ImageData obj;
        for(int i = 0; i < imgList.size(); i++){
            obj = imgList.get(i);
           // System.out.println("Object X:" + imgX + ", Obj Y: " + imgY + " Object Width:" + obj.image.getWidth() + ", Obj Height: " + obj.image.getHeight() + "Mouse X: " + e.getX() + "Mouse Y: " + e.getY());
            if(obj.mouseInside(e)){
                return obj;
            }
        }
        return null;
    }
}