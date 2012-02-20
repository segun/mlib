/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.baselib.io;

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
        new Thread() {

            public void run() {
                int ch;
                StringBuffer buffer = new StringBuffer();
                boolean isNewLine = false;
                try {
                    while ((ch = is.read()) != -1) {
                        if ((char) ch != '\n') {
                            buffer.append((char) ch);
                            isNewLine = true;
                        } else {
                            lines.addElement(buffer.toString());
                            buffer = new StringBuffer();
                            isNewLine = false;
                        }
                    }
                    if (!isNewLine) {
                        lines.addElement(buffer.toString());
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }.start();
    }

    public String readLine() throws IOException {
        if (currentLine < lines.size()) {
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
