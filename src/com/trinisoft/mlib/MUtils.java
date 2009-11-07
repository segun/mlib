/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.mlib;

import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author root
 */
public class MUtils {

    public static AnimationTimerTask task;

    public static int hasElement(Enumeration enu, Object element) {
        int counter = 0;
        while (enu.hasMoreElements()) {
            Object ne = enu.nextElement();
            if (ne.equals(element)) {
                return counter;
            } else {
                counter++;
            }
        }
        return -1;
    }

    public static Image resizeImage(Image src, Canvas canvas) {
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int screenHeight = canvas.getHeight();
        int screenWidth = canvas.getWidth();
        Image tmp = Image.createImage(screenWidth, srcHeight);
        Graphics g = tmp.getGraphics();
        g.drawImage(tmp, srcWidth, srcWidth, srcWidth);
        int ratio = (srcWidth << 16) / screenWidth;
        int pos = ratio / 2;

        //Horizontal Resize

        for (int x = 0; x < screenWidth; x++) {
            g.setClip(x, 0, 1, srcHeight);
            g.drawImage(src, x - (pos >> 16), 0, Graphics.LEFT | Graphics.TOP);
            pos += ratio;
        }

        Image resizedImage = Image.createImage(screenWidth, screenHeight);
        g = resizedImage.getGraphics();
        ratio = (srcHeight << 16) / screenHeight;
        pos = ratio / 2;

        //Vertical resize

        for (int y = 0; y < screenHeight; y++) {
            g.setClip(0, y, screenWidth, 1);
            g.drawImage(tmp, 0, y - (pos >> 16), Graphics.LEFT | Graphics.TOP);
            pos += ratio;
        }
        return resizedImage;

    }//resize image

    public static void clearScreen(Graphics g, Color c, int width, int height) {
        c.set(g);
        g.fillRect(0, 0, width, height);
    }

    public void peaceOut(final AnimationBaseCanvas c, final AnimationBaseCanvas nextScreen, Display display, int style) {
        if(style == AnimationBaseCanvas.BOX) {
            nextScreen.height = nextScreen.getHeight();
            nextScreen.width = nextScreen.getWidth();
        }
        task = new AnimationTimerTask(c, style, display, nextScreen);
        Timer t = new Timer();
        t.schedule(task, 0, Constants.ANIMATION_SPEED);
    }

    class AnimationTimerTask extends TimerTask {

        AnimationBaseCanvas canvas, nextScreen;
        int style;
        Display display;

        public AnimationTimerTask(AnimationBaseCanvas canvas, int style, Display display, AnimationBaseCanvas nextScreen) {
            this.style = style;
            this.canvas = canvas;
            this.nextScreen = nextScreen;
            nextScreen.height = nextScreen.getHeight();
            this.display = display;
        }

        private void doBlind() {
            canvas.hide = true;
            canvas.x_pos = 0;
            canvas.y_pos = 0;
            canvas.height += 5;
            canvas.width = canvas.getWidth();
            if (canvas.height > canvas.getHeight()) {
                canvas.hide = false;
                display.setCurrent(nextScreen);
                nextScreen.show = true;
                nextScreen.x_pos = 0;
                nextScreen.y_pos = 0;
                nextScreen.height -= 5;
                nextScreen.width = nextScreen.getWidth();
                if (nextScreen.height < 0) {
                    this.cancel();
                } else {
                    nextScreen.repaint();
                }
            } else {
                canvas.repaint();
            }
        }

        private void doBox() {
            canvas.hide = true;
            canvas.height += 5;
            canvas.width += 5;
            canvas.x_pos = (canvas.getWidth() - canvas.width) / 2;
            canvas.y_pos = (canvas.getHeight() - canvas.height) / 2;
            if (canvas.height > canvas.getHeight()) {
                canvas.hide = false;
                display.setCurrent(nextScreen);
                nextScreen.show = true;
                nextScreen.height -= 5;
                nextScreen.width -= 5;
                nextScreen.x_pos = (nextScreen.getWidth() - nextScreen.width) / 2;
                nextScreen.y_pos = (nextScreen.getHeight() - nextScreen.height) / 2;
                if (nextScreen.height < 0) {
                    this.cancel();
                } else {
                    nextScreen.repaint();
                }
            } else {
                canvas.repaint();
            }
        }

        public void run() {
            if (this.style == AnimationBaseCanvas.BLIND) {
                doBlind();
            }
            if (this.style == AnimationBaseCanvas.BOX) {
                doBox();
            }
        }
    }
}