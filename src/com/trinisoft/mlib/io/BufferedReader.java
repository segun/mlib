/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.trinisoft.mlib.io;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author segun
 */
public class BufferedReader {
    InputStream is = null;
    public BufferedReader(InputStream is) {
        this.is = is;
    }

    public String readLine() throws IOException{
        String line = null;
        StringBuffer sb = new StringBuffer();
        int ch;

        while((ch = is.read()) != '\n') {
            sb.append((char) ch);
        }
        sb.append('\n');
        line = sb.toString();
        return line;
    }

    public int read() throws IOException {
        return is.read();
    }
    
    public void close() throws IOException {
        is.close();
    }
}
