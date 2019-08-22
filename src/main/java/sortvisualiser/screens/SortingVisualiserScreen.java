package sortvisualiser.screens;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import sortvisualiser.MainApp;
import sortvisualiser.SortArray;
import sortvisualiser.algorithms.ISortAlgorithm;


public final class SortingVisualiserScreen extends Screen {
    private final SortArray sortArray;
    private final ArrayList<ISortAlgorithm> sortQueue;


    public SortingVisualiserScreen(ArrayList<ISortAlgorithm> algorithms, boolean playSounds, MainApp app) {
        super(app);
        app.getWindow().setResizable(true);
        setLayout(new BorderLayout());
        sortArray = new SortArray(playSounds);
        add(sortArray, BorderLayout.CENTER);
        sortQueue = algorithms;
        JButton back = new JButton("Back");
        back.addActionListener(new popListener());
        add(back, BorderLayout.SOUTH);
    }
    
    private void longSleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } 
    }

    private void shuffleAndWait() {
        sortArray.shuffle();
        sortArray.resetColours();
        longSleep();
    }

    private void restoreAndWait() {
        sortArray.restore();
        sortArray.resetColours();
        longSleep();
    }

    public class popListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        app.popScreen();
    }}


    public void onOpen() {
        //This would block the EventDispatchThread, and so
        //it must run on a worker thread

        SwingWorker<Void, Void> swingWorker = new SwingWorker<Void,Void>() {

            @Override
            protected Void doInBackground() throws Exception {
                boolean flag = false;
                try {
                    Thread.sleep(250);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } 
                for (ISortAlgorithm algorithm : sortQueue) {
                    if(flag==true){
                        //shuffleAndWait();
                        restoreAndWait();
                    }
                    flag = true;
                    sortArray.setName(algorithm.getName());
                    sortArray.setAlgorithm(algorithm);
                    SortArray.startTime = System.nanoTime();
                    algorithm.runSort(sortArray);
                    sortArray.resetColours();
                    sortArray.highlightArray();
                    sortArray.resetColours();
                    longSleep();
                }
                return null;
            }
            
            @Override
            public void done() {
                for(int i=0;i<5;i++ ){
                    longSleep();
                }

                //app.popScreen();
            }
        };
        
        swingWorker.execute();
    }
}
