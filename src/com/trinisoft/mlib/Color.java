/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.trinisoft.mlib;

import javax.microedition.lcdui.Graphics;

/**
 *
 * @author root
 */
public class Color {
    int r, g, b;
    public Color(int r, int g, int b) {
        this.r = r;
        this.b = b;
        this.g = g;
    }

    public void set(Graphics graphics) {
        graphics.setColor(r, g, b);
    }
}
