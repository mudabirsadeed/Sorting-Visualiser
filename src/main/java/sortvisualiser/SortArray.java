package sortvisualiser;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.*;

import sortvisualiser.algorithms.ISortAlgorithm;
import java.text.DecimalFormat;


public class SortArray extends JPanel {
    public static final int DEFAULT_WIN_WIDTH = MainApp.WIN_WIDTH;
    public static final int DEFAULT_WIN_HEIGHT = MainApp.WIN_HEIGHT;
    //private static final int DEFAULT_BAR_WIDTH = 5;
    /**
     * This is the percent of the panel the bars will consume.
     * Based on the original 256 bars each being 2x their height
     * and 720px window height, or 512/720
     */
    private static final double BAR_HEIGHT_PERCENT = 512.0/720.0;
    private static final int NUM_BARS = MainApp.inputNumbers.length;
    
    private final int[] array;
    private final int[] barColours;
    private String algorithmName = "";
    private ISortAlgorithm algorithm;
    private long algorithmDelay = 0;

    private MidiSoundPlayer player;
    private JSpinner spinner;
    private boolean playSounds;
    public static long startTime = 0;
    private static float currentTime = 0;
    private static String currentTimeS="0";
    private static DecimalFormat df=new DecimalFormat("0.0");

    private int arrayChanges = 0; // Number of changes to the array the current algorithm has taken so far

    public SortArray(boolean playSounds) {
        setBackground(Color.DARK_GRAY);
        array = new int[NUM_BARS];
        barColours = new int[NUM_BARS];
        for (int i = 0; i < NUM_BARS; i++) {
            array[i] = MainApp.inputNumbers[i];
            barColours[i] = 0;
        }
        player = new MidiSoundPlayer(NUM_BARS);
        this.playSounds = playSounds;
        spinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
        spinner.addChangeListener((event) -> {
            algorithmDelay = (Integer) spinner.getValue();
            algorithm.setDelay(algorithmDelay);
        });
        add(spinner,BorderLayout.LINE_START);
    }

    public int arraySize() {
        return array.length;
    }

    public int getValue(int index) {
        return array[index];
    }


    public int getMaxValue() {
    	return Arrays.stream(array).max().orElse(Integer.MIN_VALUE);
    }
    
    private void finaliseUpdate(int value,  long millisecondDelay, boolean isStep) {
        repaint();
        try {
            Thread.sleep(millisecondDelay);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        if (playSounds) {
            player.makeSound(value);
        }
        if (isStep) {
            arrayChanges++;
            SortArray.currentTime = System.nanoTime()-SortArray.startTime;
            SortArray.currentTimeS = df.format(currentTime/1000000000f);
        }

    }

    public void swap(int firstIndex, int secondIndex, long millisecondDelay, boolean isStep) {
        int temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;

        barColours[firstIndex] = 100;
        barColours[secondIndex] = 100;

        finaliseUpdate((array[firstIndex] + array[secondIndex]) / 2, millisecondDelay, isStep);
    }

    public void updateSingle(int index, int value, long millisecondDelay, boolean isStep) {
        array[index] = value;
        barColours[index] = 100;
       

        finaliseUpdate(value, millisecondDelay, isStep);
        repaint();
    }

    public void restore() {
        arrayChanges = 0;
        int[] input = MainApp.getInputNumbers();
        for (int i = 0; i < arraySize(); i++) {
           // int swapWithIndex = rng.nextInt(arraySize() - 1);
            //swap(i, swapWithIndex, 5, false);
                array[i] = MainApp.inputNumbers[i];
                barColours[i] = 0;
                updateSingle(i, array[i], 5, false);
        }
        arrayChanges = 0;
    }

    public void shuffle() {
        arrayChanges = 0;
        Random rng = new Random();
        for (int i = 0; i < arraySize(); i++) {
            int swapWithIndex = rng.nextInt(arraySize() - 1);
            swap(i, swapWithIndex, 5, false);
        }
        arrayChanges = 0;

    }




    public void highlightArray() {
        for (int i = 0; i < arraySize(); i++) {
            updateSingle(i, getValue(i), 5, false);
        }
    }

    /**
     * Gets the canvas size
     *
     * @return size
     */


    public void resetColours() {
        for (int i = 0; i < NUM_BARS; i++) {
            barColours[i] = 0;
        }
        repaint();
    }

    /**
     * Draws the array
     *
     * @param g The graphics device for drawing
     */
    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
        Graphics2D panelGraphics = (Graphics2D) g.create();

		try
		{
			Map<RenderingHints.Key, Object> renderingHints = new HashMap<>();
			renderingHints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			panelGraphics.addRenderingHints(renderingHints);
			panelGraphics.setColor(Color.WHITE);
			panelGraphics.setFont(new Font("Monospaced", Font.BOLD, 20));
			panelGraphics.drawString(" Current algorithm: " + algorithmName, 10, 30);
			panelGraphics.drawString("Current step delay: " + algorithmDelay + "ms", 10, 55);
			panelGraphics.drawString("     Array Changes: " + arrayChanges, 10, 80);
            panelGraphics.drawString("       Time taken : " + currentTimeS + "s", 10, 105);

			drawBars(panelGraphics);
		} finally {
        	panelGraphics.dispose();
        }
    }

	private void drawBars(Graphics2D panelGraphics)
	{
		int barWidth = getWidth() / NUM_BARS;
		int bufferedImageWidth = barWidth * NUM_BARS;
		int bufferedImageHeight = getHeight();
        
		if(bufferedImageHeight > 0 && bufferedImageWidth > 0) {
			if(bufferedImageWidth < 256) {
				bufferedImageWidth = 256;
			}
			
			double maxValue = getMaxValue();
		
			BufferedImage bufferedImage = new BufferedImage(bufferedImageWidth, bufferedImageHeight, BufferedImage.TYPE_INT_ARGB);
			makeBufferedImageTransparent(bufferedImage);
			Graphics2D bufferedGraphics = null;
			try
			{
				bufferedGraphics = bufferedImage.createGraphics();
				
				for (int x = 0; x < NUM_BARS; x++) {
					double currentValue = getValue(x);
					double percentOfMax = currentValue / maxValue;
					double heightPercentOfPanel = percentOfMax * BAR_HEIGHT_PERCENT;
					int height = (int) (heightPercentOfPanel * (double) getHeight());
					int xBegin = x + (barWidth - 1) * x;
					int yBegin = getHeight() - height;
					
					int val = barColours[x] * 2;
					if (val > 190) {
						bufferedGraphics.setColor(new Color(0,0,0));
					}
					else {
					    int g;
					    int b;

                        g = 120-(val/3);
                        b = 20-(val/5);

					    if(g<0) {g=0;}
					    if(b<0) {b=0;}

						bufferedGraphics.setColor(new Color(255, g, b));
					}
					bufferedGraphics.fillRect(xBegin, yBegin, barWidth, height);
					if (barColours[x] > 0) {
						barColours[x] -= 5;
					}
				}
			}
			finally
			{
				if(bufferedGraphics != null)
				{
					bufferedGraphics.dispose();
				}
			}
			
			panelGraphics.drawImage(bufferedImage, 0, 0, getWidth(), getHeight(), 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
		}
	}
	
    private void makeBufferedImageTransparent(BufferedImage image)
    {
    	Graphics2D bufferedGraphics = null;
		try
		{
			bufferedGraphics = image.createGraphics();
			
			bufferedGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
			bufferedGraphics.fillRect(0, 0, image.getWidth(), image.getHeight());
			bufferedGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		}
		finally
		{
			if(bufferedGraphics != null)
			{
				bufferedGraphics.dispose();
			}
		}
    }




    @Override
    public void setName(String algorithmName) {
        this.algorithmName = algorithmName;
    }
    
    public void setAlgorithm(ISortAlgorithm algorithm) {
        this.algorithm = algorithm;
        algorithmDelay = algorithm.getDelay();
        spinner.setValue((int) algorithm.getDelay());
    }
    public long getAlgorithmDelay(){
        return algorithmDelay;
    }
}
