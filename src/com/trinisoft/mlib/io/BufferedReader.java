/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.trinisoft.mlib.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

/**
 *
 * @author segun
 */
public class BufferedReader {
    InputStream is = null;
    private Vector lines;
    int currentLine = 0;
    
    public BufferedReader(InputStream is) {
        this.is = is;
        lines = new Vector();
        readIntoVector();
    }

    private void readIntoVector() {
        int ch;
        StringBuffer buffer = new StringBuffer();
        try {
            while ((ch = is.read()) != -1) {
                if ((char) ch != '\n') {
                    buffer.append((char) ch);
                } else {
                    lines.addElement(buffer.toString());
                    buffer = new StringBuffer();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String readLine() throws IOException{
        if(currentLine < lines.size()) {
            return lines.elementAt(currentLine++).toString();
        } else {
            return null;
        }
    }

    public int read() throws IOException {
        return is.read();
    }
    
    public void close() throws IOException {
        is.close();
    }

    public void reset() {
        currentLine = 0;
    }
}
