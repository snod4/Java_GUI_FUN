import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.event.MouseEvent;


public class ImageData {
    protected boolean visible = true;
    protected BufferedImage image;
    protected int x;
    protected int y;
    protected ArrayList<ImageData> linkedImages = new ArrayList<ImageData>();
    protected String name = null;
    protected boolean moveable = false;
   
   public ImageData(BufferedImage image, int x, int y){
       this.image = image;
       this.x = x;
       this.y = y;
   }

   public ImageData(BufferedImage image, int x, int y, String name){
    this.image = image;
    this.x = x;
    this.y = y;
    this.name = name;
}

   public ArrayList<ImageData> getLinkedImages(){
       return linkedImages;
   }

   public void setMoveable(boolean move){
       moveable = true;
   }

   public void setColor(Color color){
       image.getGraphics().setColor(color);
       image.getGraphics().fillRect(0, 0, image.getWidth(), image.getHeight());
   }

   public boolean mouseInside(MouseEvent e){
    int imgX = x;
    int imgY = y;
    if(this.visible && (e.getX() >= imgX && e.getX() <= imgX + this.image.getWidth())
            && (e.getY() >= imgY && e.getY() <= imgY + this.image.getHeight())){
                return true;
            }
    return false;
   }



}