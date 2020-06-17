import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.BorderLayout;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



public class Runner{

    public static void main(String args[]){
        // JWindow window = new JWindow();
        // window.setSize(400, 400);
        JFrame frame = new JFrame();
        frame.setSize(400, 430);
        ImagePanel pane = new ImagePanel(400,400);
        //window.add(pane);
        frame.getContentPane().add(pane);
        System.out.println(pane.getSize().getHeight());
        //frame.setContentPane(pane);

        /*************** DropDownBarContent **************/

        DropDownBar bar = new DropDownBar(pane.getWidth(), 30, 0, 0, Color.WHITE);
        bar.addCategory("Test1");
        bar.addCategory("test2");
        bar.addCategory("test3");
        bar.addSubcategoryItem("Test1", "hello");
        bar.addSubcategoryItem("Test1", "hello2");


        /************ Adding Items to Panel **************/

        
        pane.addImageData(bar);



        /********************************************** */



        MouseInputListener listener = new MouseInputListener(){
            private static final int NOT_PRESSED = -1;
            private int pressedPosX = NOT_PRESSED;
            private int pressedPosY = NOT_PRESSED;
            @Override
            public void mouseMoved(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }
        
            @Override
            public void mouseDragged(MouseEvent e) {
                System.out.println("Dragged");
                ImageData obj;
                if((obj = pane.returnImage(e)) != null && obj.moveable){
                    System.out.println("Inside object");
                    obj.x+=(e.getX() - pressedPosX);
                    obj.y+= (e.getY() - pressedPosY);
                    pressedPosX = e.getX();
                    pressedPosY = e.getY();
                }
                
            }
        
            @Override
            public void mouseReleased(MouseEvent e) {
                pressedPosX = NOT_PRESSED;
                pressedPosY = NOT_PRESSED;
                
            }
        
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Pressed");
                if(pressedPosX == NOT_PRESSED && pressedPosY == NOT_PRESSED){
                    pressedPosX = e.getX();
                    pressedPosY = e.getY();
                }

                
            }
        
            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }
        
            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }
        
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Clicked");
                ImageData obj = pane.returnImage(e);
                bar.deselectCategory();
                if(obj != null){
                    if(obj instanceof DropDownBar){
                        DropDownBar dropDownBar = (DropDownBar) obj;
                        System.out.println("Selected Category");
                        dropDownBar.drawSelectedCategory(e);
                    }
                }
            }
        };

        // KeyAdapter kAdapter = new KeyAdapter(){
        //     @Override
        //     public void keyTyped(KeyEvent e){
        //         System.out.println(e.getKeyCode());
        //         if(e.getKeyCode() == 27){
        //             System.exit(0);
        //         }
        //     }
        // };


        /**
         * Key input not working even though focus is set... Look into key bindings maybe?
         */



       
        //window.pack();
       // pane.setFocusable(true);
        frame.addWindowFocusListener(new WindowAdapter(){
            public void windowGainedFocus(WindowEvent e) {
                pane.requestFocusInWindow();
            }
        });

        System.out.println(pane.isDisplayable());
       boolean focus = pane.requestFocusInWindow();
       System.out.println("Obtained Focus: " +  focus);

        //pane.addKeyListener(input);
        pane.addMouseListener(listener);
        pane.addMouseMotionListener(listener); 
        pane.getInputMap().put(KeyStroke.getKeyStroke((char) 27), "exit");
        pane.getActionMap().put("exit", new AbstractAction(){
            
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        
        frame.setVisible(true);
        pane.setVisible(true);



        while(true){
            pane.repaint();
        }

    }

    
}