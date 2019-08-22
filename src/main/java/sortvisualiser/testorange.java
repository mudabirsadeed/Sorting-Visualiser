package sortvisualiser;
import javax.swing.*;  // Importing Java Swing library, lets you make GUI components for your Java applications
import java.awt.event.*; // allows you use action listener methods, used for buttons
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.*; //Contains all of the classes for creating user interfaces
//and for painting graphics and images.
//A user interface object such as a button or a scrollbar



// @Author: Mohammad Esmaeili



public class testorange implements  ActionListener
// implementing Action listener interface
//using the component's addActionListener method. When the action eventoccurs, that object's actionPerformed method isinvoked.



{
    // Global variables declared here

    JFrame fr = new JFrame("hi"); // Creating a frame named hi
    JPanel panel= new JPanel(new BorderLayout()); // Creating the main panel, passing a borderlayout object as a parameter
    JPanel North = new JPanel(new BorderLayout()); // Creating the North Panel,passing a borderlayout object as a parameter
    JPanel south = new JPanel(new BorderLayout()); // Creating the south panel,passing a borderlayout object as a parameter
    JLabel lab = new JLabel(" Loading... ", SwingConstants.CENTER);
    // creating a label, used for positioning and orientingcomponents on the screen (in this case label)



     // JButton button1 = new JButton("press me"); // Creating a button, passing a message to its constructor



    static  Color orange = new Color(230,80,5); // Creating a an object for Color passing the RGB values for dark orange

    public  testorange() // Constructor method is called after the class name its instantiated in the main method

    {
        lab.setFont (lab.getFont ().deriveFont (30.0f)); //changing the size and font of the label writing set to size of 30
        panel.setBackground(orange); // Sets the background color of the main panel to orange
        North.setBackground(orange); // Sets the background color of the North panel to orange
        North.add(lab, BorderLayout.CENTER); //Adds the label to the center of the North panel
        south.setBackground(orange); //Sets the background color of the North panel to orange
       // south.add(button1); // Adds the button to the south panel
        panel.add(south,BorderLayout.SOUTH); // Adds the south panel to south of the main panel
        panel.add(North,BorderLayout.NORTH); // Adds the north panel to north of the main panel



        JPanel imagepanel=new JPanel(); // Creates a  panel for the image
        imagepanel.setBackground(orange); // sets the background color of imagepanel to orange
        imagepanel.setLayout(new GridBagLayout()); // Creates a grid bag layout manager.
        JLabel background = new JLabel(new ImageIcon("resources1/tiger-clip.gif"));
        // Creates a label and an image ImageIcon, imports the image as a label
        background.setLayout(new BorderLayout()); // Allows jlabel to use layout
        imagepanel.add(background); // adds the label to this panel



        panel.add(imagepanel,BorderLayout.CENTER);
        // adds the panel which contains the image as a label to the main panel (in the center)
        //button1.addActionListener(this); // Adds an ActionListener to the button
        fr.add(panel); // adds the main panel which contains all the other panels on the frame
        fr.pack(); // Causes this Window to be sized to fit the preferred sizeand layouts of its subcomponents
        fr.setSize(2350,1030); // Sets the maximum size of the frame
        panel.setSize(1180,620); //sets the size of the main panel
        fr.setVisible(true); // sets frame containing everything to visible (on the screen)
        fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // When the user closes the windows stops the program from running



    }



    @Override
    public void actionPerformed(ActionEvent e) // Invoked when an action occurs (when the user enters the button)
    {
        // TODO Auto-generated method stub
        fr.dispose(); // closes the frame window
        //Example3 ex = new Example3(); // Instantiates the class of the other, and calls their constructor
    }

    public void closeWindow() {
        fr.dispose();
    }

//    public static void main(String [] args) // main method
//    {
//        testorange fr = new testorange();
//        // creating a new object of this class, which will call its constructor method called testorange
//    }
}
