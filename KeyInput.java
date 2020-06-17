import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println(e.getKeyCode());
        if(e.getKeyCode() == 27){
            System.exit(0);
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
       System.out.println("KEY PRESSED");

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }


}