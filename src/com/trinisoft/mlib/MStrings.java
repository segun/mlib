/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.mlib;

import java.util.Vector;

/**
 *
 * @author root
 */
public class MStrings {

    public static Vector strToVector(String csv) {
        Vector retVector = new Vector();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < csv.length(); i++) {
            if (csv.charAt(i) == ',') {
                retVector.addElement(sb);
                sb = new StringBuffer();
            } else {
                sb.append(csv.charAt(i));
            }
        }
        return retVector;
    }

    public static void split(String string, String delimiter) {
        String myString = string;
        while (myString.length() > 0) {
            try {
                String one = myString.substring(0, myString.indexOf(delimiter));
                myString = myString.substring(one.length() + delimiter.length(), myString.length());
                System.out.println(one);
                System.out.println(myString);
            } catch (StringIndexOutOfBoundsException siobe) {
                break;
            }
        }
    }
}
