package sortvisualiser;


import sortvisualiser.screens.*;

import sun.applet.Main;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class MainApp {
    private final JFrame window;

    public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    
    public static final int WIN_WIDTH = screenSize.width-20;
    public static final int WIN_HEIGHT = screenSize.height-100;
    
    private final ArrayList<Screen> screens;

    public static int[] inputNumbers;


    
    public MainApp() {
        screens = new ArrayList<>();
        window = new JFrame ("Sort visualiser");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

    }
    
    public Screen getCurrentScreen() {
        return screens.get(screens.size() - 1);
    }
    
    public void pushScreen(Screen screen) {
        if (!screens.isEmpty()) {
        	window.remove(getCurrentScreen());
        }
        screens.add(screen);
        window.setContentPane(screen);
        window.validate();
        screen.onOpen();
    }
    
    public void popScreen() {
        if (!screens.isEmpty()) {
            Screen prev = getCurrentScreen();
            screens.remove(prev);
            window.remove(prev);
            if (!screens.isEmpty()) {
            	Screen current = getCurrentScreen();
            	window.setContentPane(current);
            	window.validate();
                current.onOpen();
            }
            else {
                window.dispose();
            }
        }
    }
    
    public void start() {
        //pushScreen(new MainMenuScreen(this));
        pushScreen(new IntroScreen(this));
        window.pack();
    }
    
    public static void main(String... args) {
        System.setProperty("sun.java2d.opengl", "true");
        SwingUtilities.invokeLater(() -> {
            new MainApp().start();
        });

    }


    public void setInputNumbers(int[] inputNumbers) {
        MainApp.inputNumbers = inputNumbers;
    }

    public static int[] getInputNumbers() {
        return inputNumbers;
    }

    public JFrame getWindow() {
        return window;
    }
}
