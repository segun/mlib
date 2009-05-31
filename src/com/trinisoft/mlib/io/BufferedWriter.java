/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.trinisoft.mlib.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author segun
 */
public class BufferedWriter {
    OutputStream os = null;
    String buffer = null;

    public BufferedWriter(OutputStream os) {
        this.os = os;
    }

    public void write(String line, boolean addNewLine) throws IOException {
        line += ((addNewLine) ? "\n" : "");
        os.write(line.getBytes());
    }

    public void append(String line, boolean addNewLine) {
        if(buffer == null) {
            buffer = line + ((addNewLine) ? "\n" : "");
        } else {
            buffer += line + ((addNewLine) ? "\n" : "");
        }
    }

    public void flush() throws IOException {
        os.write(buffer.getBytes());
        os.flush();
    }

    public void close() throws IOException {
        os.close();
    }
}
