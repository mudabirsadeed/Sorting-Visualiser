package sortvisualiser.screens;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

import sortvisualiser.MainApp;
import sortvisualiser.algorithms.*;
import sortvisualiser.testorange;


public final class MainMenuScreen extends Screen {
    private static final Color BACKGROUND_COLOUR = Color.DARK_GRAY;
    private final ArrayList<AlgorithmCheckBox> checkBoxes;
    
    public MainMenuScreen(MainApp app) {
        super(app);
        app.getWindow().setResizable(true);
        checkBoxes = new ArrayList<>();
        setUpGUI();
    }
    
    private void addCheckBox(ISortAlgorithm algorithm, JPanel panel) {
        JCheckBox box = new JCheckBox("", true);
        box.setAlignmentX(Component.LEFT_ALIGNMENT);
        box.setBackground(BACKGROUND_COLOUR);
        box.setForeground(Color.WHITE);
        checkBoxes.add(new AlgorithmCheckBox(algorithm, box));
        panel.add(box);
    }

    private void addLabel() {
        JLabel box = new JLabel("THE TIGERS");
        box.setAlignmentX(Component.CENTER_ALIGNMENT);
        box.setBackground(BACKGROUND_COLOUR);
        box.setForeground(new Color(230, 80,5));
        Font font = new Font("TimesRoman", Font.BOLD, 32);
        box.setFont(font);
        add(box, BorderLayout.NORTH);
    }

    public class backListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            IntroScreen.flag=false;
            app.popScreen();
        }
    }

    private void addBackButton() {
        JButton button = new JButton("Back");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(new backListener());
        add(button, BorderLayout.SOUTH);
    }
    
    private void initContainer(JPanel p) {
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
        p.setBackground(BACKGROUND_COLOUR);
       // p.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    }
    
    public void setUpGUI() {
        JPanel sortAlgorithmContainer = new JPanel();
        JPanel optionsContainer = new JPanel();
        JPanel outerContainer = new JPanel();

        initContainer(this);
        initContainer(optionsContainer);
        initContainer(sortAlgorithmContainer);
        
        outerContainer.setBackground(BACKGROUND_COLOUR);
        outerContainer.setLayout(new BoxLayout(outerContainer, BoxLayout.LINE_AXIS));
        addLabel();

        try {

            BufferedImage image = ImageIO.read(new File("resources/Tiger.png"));

            JLabel label = new JLabel(new ImageIcon(image));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(label);
        } catch (IOException e) {
            System.out.println("Unable to load logo");
            e.printStackTrace();
        }
        
        sortAlgorithmContainer.setAlignmentX(Component.CENTER_ALIGNMENT);

        addCheckBox(new BubbleSort(),       sortAlgorithmContainer);
        addCheckBox(new QuickSort(),        sortAlgorithmContainer);
        addCheckBox(new MergeSort(),        sortAlgorithmContainer);
        addCheckBox(new InsertionSort(),    sortAlgorithmContainer);

        JCheckBox soundCheckBox = new JCheckBox("Play Sounds");
        soundCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        soundCheckBox.setBackground(BACKGROUND_COLOUR);
        soundCheckBox.setForeground(Color.WHITE);
        
        //optionsContainer.add(soundCheckBox);
       
        JButton startButton = new JButton("Begin Visual Sorter");
        startButton.addActionListener((ActionEvent e) -> {
            ArrayList<ISortAlgorithm> algorithms = new ArrayList<>();
            for (AlgorithmCheckBox cb : checkBoxes) {
                if (cb.isSelected()) {
                    algorithms.add(cb.getAlgorithm());
                }
            }
             app.pushScreen(
                new SortingVisualiserScreen(
                            algorithms,
                            soundCheckBox.isSelected(),
                            app
                        ));
        });
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        outerContainer.add(optionsContainer);
        outerContainer.add(Box.createRigidArea(new Dimension(5,0)));
        outerContainer.add(sortAlgorithmContainer);

        int gap = 15;
        add(Box.createRigidArea(new Dimension(0, gap)));
        add(outerContainer);
        add(Box.createRigidArea(new Dimension(0, gap)));
        add(startButton);
        add(Box.createRigidArea(new Dimension(0, gap)));
        addBackButton();
    }

    @Override
    public void onOpen() {
        checkBoxes.forEach((box) -> {
            box.unselect();
            if(box.algorithm.getName().equals("Insertion Sort")) {
                box.select();
            }
        });

    }
    
    private class AlgorithmCheckBox {
        private final ISortAlgorithm algorithm;
        private final JCheckBox box;
        
        public AlgorithmCheckBox(ISortAlgorithm algorithm, JCheckBox box) {
            this.algorithm = algorithm;
            this.box = box;
            this.box.setText(algorithm.getName());
            this.box.setForeground(new Color(250, 100,10));
        }
        
        public void unselect() {
            box.setSelected(false);
        }

        public void select() {box.setSelected(true);}

        public boolean isSelected() {
            return box.isSelected();
        }
        
        public ISortAlgorithm getAlgorithm() {
            return algorithm;
        }
    }


    }

