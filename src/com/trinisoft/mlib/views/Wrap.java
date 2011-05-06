/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.trinisoft.mlib.views;

import com.sun.lwuit.Font;
import java.util.Enumeration;

/**
 *
 * @author unicorn
 */
public class Wrap {

    private Font font;
    private Enumeration lineEnumeration;
    private int width;
    private int position;
    private int length;
    private int start;
    private String text;

    public Wrap(Font font, String content, int width) {
        this.font = font;
        this.text = content;
        this.width = width;
        this.length = this.text.length();
    }

    public boolean hasMoreElements() {
        return (position < (length - 1));
    }

    private int next() {
        int i = position;
        int lastbreak = -1;
        for (; i < length && font.stringWidth(text.substring(position, i)) <= width; i++) {
            if (text.charAt(i) == ' ') {
                lastbreak = i;
            } //else if (text.charAt(i) == '\n') {
//                lastbreak = i;
//                break;
//            }
        }

        if (i == length) {
            position = i;
        } else if (lastbreak <= position) {
            position = i;
        } else {
            position = lastbreak;
        }
        return position;
    }

    public Object nextElement() {
        try {
            return text.substring(start, (start = next()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}