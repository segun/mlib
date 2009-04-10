/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.mlib.animation;

import com.trinisoft.mlib.Constants;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author root
 */
public class Transistor {

    public static void slide(int direction, Canvas to, Display display) {
        switch (direction) {
            case Constants.DIR_UP:
                slideUp(to, display);
        }
    }

    private static void slideUp(final Canvas to, final Display display) {
        Thread fromThread = new Thread() {
            final TransistorCanvas tCanvas = new TransistorCanvas(to, 0);
            public void run() {
                int i = 0;
                while (i < 100) {                    
                    display.setCurrent(tCanvas);
                    i++;
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ie) {
                }
            }
        };
        fromThread.start();
    }

    static class TransistorCanvas extends Canvas {

        Canvas to;
        int yPos;

        public TransistorCanvas(Canvas to, int yPos) {
            this.to = to;
            this.yPos = yPos;
        }

        public void paint(Graphics g) {
            System.out.println("Moving by " + yPos);
            g.setClip(0, yPos, this.getWidth(), this.getHeight());
            to.repaint();
        }
    }
}
