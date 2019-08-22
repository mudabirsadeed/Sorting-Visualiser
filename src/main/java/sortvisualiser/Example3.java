package sortvisualiser;

import sortvisualiser.screens.IntroScreen;
import sortvisualiser.screens.MainMenuScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Button;
import java.awt.Color;
import java.util.Random;
import java.util.regex.*;


// @Author: Jubin Joy
public class Example3 implements ActionListener{
        private JFrame frame = new JFrame("Input");//frame
        private JTextArea textArea = new JTextArea("Please enter input values for the sort seperated by comma's");//only textarea
        //scroll is attached to textarea
        private JButton button1 = new JButton("Random");//created random button
        private JButton button2 = new JButton("Submit");// created submit button

        static int[] input = null;
        public MainApp app;


        public Example3(MainApp app){
            JScrollPane scroll = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            textArea.setSize(400,400);//area size
            textArea.setLineWrap(true);// helping to continue to new line if text length is long
            textArea.setMargin(new Insets(20,20,20,20));
            scroll.setPreferredSize(new Dimension(600, 400));//set size
            this.app=app;

            JPanel panel1 = new JPanel();//first panel
            panel1.setLayout(new BorderLayout());//layout
            panel1.setSize(400,400);//panel size
            panel1.add(scroll);//adding scroll in panel1


            JPanel panel2 = new JPanel(new BorderLayout());//secon panel
            panel2.add(button1,BorderLayout.NORTH);
            panel2.add(button2,BorderLayout.SOUTH);
            button1.addActionListener(this);//button action listeners
            button2.addActionListener(this);
            frame.add(panel1,BorderLayout.NORTH);
            frame.add(panel2,BorderLayout.SOUTH);
            frame.pack();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setSize(screenSize.width,screenSize.height);
            frame.setSize(600,500);// frame size
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            //frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            WindowListener exitListener = new WindowAdapter() {

                @Override
                public void windowClosing(WindowEvent e) {
                   IntroScreen.flag =false;
                    frame.dispose();
                }
            };

            WindowListener changeFocusListener = new WindowAdapter() {

                @Override
                public void windowDeactivated(WindowEvent e)  {
                    frame.requestFocus();
                }
            };

            frame.addWindowListener(exitListener);
            frame.addWindowListener(changeFocusListener);


        }


        public boolean checkInput(String s) {

            Pattern pattern = Pattern.compile(".*[^,0-9]+.*");
            Matcher matcher = pattern.matcher(s);

            return matcher.matches();
        }


        public void actionPerformed(ActionEvent e) {
            Object check = e.getSource();
            boolean saver = checkInput(textArea.getText());

            if (check == button1) {
                textArea.setText(randomN());
            }
            else if(check == button2 ) {
                if(saver) {
                    JFrame errorCode = new JFrame();
                    JOptionPane.showMessageDialog(errorCode,
                            "Please Check the text box and type Numbers.",
                            "Input error",
                            JOptionPane.ERROR_MESSAGE);
                    System.out.println("asddd");
                }
                else {
                    {

                        String s = textArea.getText();
                        String[] sArr = s.split(",");
                        input = new int[sArr.length];
                        for(int i=0;i<sArr.length;i++) {
                            input[i]=Integer.parseInt(sArr[i]);
                        }
                        app.setInputNumbers(input);
//                        new MainApp(frame, window).setUp();
//                        frame.dispose();
//                        window.dispose();

                        app.pushScreen(new MainMenuScreen(app));
                        frame.dispose();
//                        jf.setVisible(false);
                    }
                }
            }
        }

    public static String randomN(){//this method is for the random button
        Random rd = new Random(); // creating Random object
        StringBuilder gn = new StringBuilder();// instance of stringbuilder
        int[] arr = new int[256];//instance of 6 int array --> should change to 256
        for (int i = 0; i < arr.length; i++) {//itterating the array
            arr[i] = rd.nextInt(500)+1; // storing between 1 to 500 random integer
            gn.append(arr[i]);//adding array index to gn stringbuilder
            gn.append(",");//adding comma after each number.
        }
        gn.deleteCharAt(gn.length()-1);//deleting the last comma

        //String getNumber = gn.toString();// initialising get Number, converting gn to string.

        return gn.toString();
    }

//        public static void main(String [] args) {
//            new Example3();
//        }

    public int[] getInput() {
        return input;
    }


}




