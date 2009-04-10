/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.trinisoft.mlib;

import java.util.Enumeration;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author root
 */
public class MUtils {
    public static int hasElement(Enumeration enu, Object element) {
        int counter = 0;
        while(enu.hasMoreElements()) {
            Object ne = enu.nextElement();
            if(ne.equals(element)) {
                return counter;
            }
            else {
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
}