package sortvisualiser.screens;

import sortvisualiser.Drawing;
import sortvisualiser.Example3;
import sortvisualiser.MainApp;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class IntroScreen extends Screen {
    public static boolean flag = false;
    public IntroScreen(MainApp app) {
        super(app);
        app.getWindow().setResizable(false);
        add(new Drawing());
        JButton button= new JButton("Enter");
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        button.setAlignmentX(0.4999999f);
        setBackground(new Color(51,51,51));
        add(button, BorderLayout.SOUTH);
        button.addActionListener(new EnterButtonListener());

    }

    public void onOpen() {

    }

   // static final int WIDTH =  screenSize.width;
   // static final int HEIGHT = screenSize.height;



    public class EnterButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ev){

            try{
                // MoCode
                //new testorange();
                if(!flag) {
                    new Example3(app);
                }
                flag=true;
                //jf.dispose();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
