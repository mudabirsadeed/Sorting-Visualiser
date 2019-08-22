package sortvisualiser;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

import static sortvisualiser.MainApp.WIN_HEIGHT;
import static sortvisualiser.MainApp.WIN_WIDTH;

@SuppressWarnings("serial")



public class Drawing extends JPanel{
//
//    @Override
//    public Dimension getPreferredSize() {
//        return new Dimension(WIN_WIDTH, WIN_HEIGHT-40);
//    }

    private char[] message = ("ENTER THE ALGORITHM").toCharArray();
    private int FONTSIZE = 32, THREADNUM = WIN_WIDTH/FONTSIZE;
    private thread1[] thArr = new thread1[THREADNUM];
    private boolean flag=false;
    public Drawing(){
        for (int i = 0; i < thArr.length; i++) {
            thArr[i] = new thread1(i*FONTSIZE);
        }
    }
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
        g.fillRect(0, 0, WIN_WIDTH, WIN_WIDTH);
        g.setColor(Color.BLACK);
        Font font = new Font("Monospaced", Font.BOLD, FONTSIZE);
        g2.setFont(font);
        createThreads(g2);
        try{Thread.sleep(30);}catch(Exception ex){}
        repaint();
    }
    private void createThreads(Graphics2D g2) {
        for (int i = 0; i < thArr.length; i++) {
            drawThread(g2,thArr[i], (i+1));
        }
    }
    private void drawThread(Graphics2D g2, thread1 th, int pos){
        for (int i = 0; i < th.len; i++) {
            if(th.randInt(0, th.len) == i){
                th.chArr[i][0] = th.randChar();
            }
            if(i == th.len-1) {
                g2.setColor(Color.WHITE);
                setMessage(th, pos, message);
            } else if (pos==(THREADNUM/2)+1&&i<=th.len-3 && i>=th.len-7) {
                insertArrow(g2, th);
            } else {
                if(pos%2==0){
                    g2.setColor(new Color(230, 80,5));
                } else if (pos%3==0) {
                    g2.setColor(Color.BLACK);
                } else {
                    g2.setColor(new Color(250, 100,10));
                }
            }
            g2.drawChars(th.chArr[i] ,0 ,1 ,th.x , th.y + (i*FONTSIZE));
        }
        if((th.y+FONTSIZE*th.len)>WIN_HEIGHT-13 ) {
            if(pos==(THREADNUM/2)+1) {
                flag = true;
            }
        } else {
            th.y+=th.vel;
        }
    }
    public void insertArrow (Graphics2D g2, thread1 th) {
        for(int i =th.len-7; i<=th.len-3;i++)
            if(i==th.len-3) {
                Font temp = g2.getFont();
                g2.setFont(new Font("Calibri", Font.BOLD, FONTSIZE));
                th.chArr[i][0] = 'v';
                g2.setColor(Color.WHITE);
                g2.setFont(temp);
            } else {
                th.chArr[i][0] = '|';
                g2.setColor(Color.WHITE);
            }
        th.vel=3;
    }
    public void setMessage(thread1 th, int pos, char[] message) {
        int threadMid = (THREADNUM/2) +1 ;
        int messageMid = message.length/2;
        int startPos = threadMid-messageMid;
        for(int i = 0;i<message.length+1;i++) {
            if(pos==startPos-1 || pos==startPos+message.length) {
                th.chArr[th.len-1][0] = ' ';
            } else if (pos<startPos+message.length+1&&pos>=startPos) {
                for(int k = 0;k<message.length;k++) {
                    if(pos==startPos+k) {
                        th.chArr[th.len-1][0] = message[k];
                    }
                }
            } else {
                char rand = th.randChar();
                if (rand != ' ') {
                    th.chArr[th.len-1][0] = rand;
                } else {
                    th.chArr[th.len-1][0] = '%';
                }
                if(flag) {
                    th.chArr[th.len-1][0] = '-';
                }
            }
        }
    }
    public class thread1{
        int vel, len, x, y;
        char[][] chArr;
        thread1(int x){
            this.x = x;
            len = randInt(8,30);
            chArr = new char[len][1];
            chArr = populateArrWithChars(chArr);
            vel = randInt(4,9);
            this.y = (-1)*len*FONTSIZE;
        }
        private char[][] populateArrWithChars(char[][] arr){
            for (int i = 0; i < arr.length; i++) {
                arr[i][0] = randChar();
            }
            return arr;
        }
        private char randChar(){
            String alphabet;
            if(flag) {
                alphabet = "0123456789QWERTYUIOPASDFGHJKLZXCVBNN?/%&£";
            } else {
                alphabet = "0123456789QWERTYUIOPASDFGHJKLZXCVBNN?/%&£     ";
            }

            final int N = alphabet.length();
            Random r = new Random();
            return alphabet.charAt(r.nextInt(N));
        }
        private int randInt(int min, int max) {
            Random rand = new Random();
            return rand.nextInt((max - min) + 1) + min;
        }

    }
}
