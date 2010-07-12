/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.trinisoft.mlib.util;

/**
 *
 * @author trinisoftinc
 */
public class Echo {
    public static void outln(Object o) {
        System.out.println(o);
    }

    public static void errln(Object o) {
        System.err.println(o);
    }

    public static void out(Object o) {
        System.out.println(o);
    }

    public static void err(Object o) {
        System.err.println(o);
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
