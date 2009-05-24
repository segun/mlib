/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.trinisoft.mlib;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author root
 */
public abstract class AnimationBaseCanvas extends Canvas {
    public int x_pos, y_pos;
    public int width, height;
    public boolean hide = false;
    public boolean show = false;
    public static int BLIND = 1;
    public static int BOX = 2;
    Graphics g;
}
