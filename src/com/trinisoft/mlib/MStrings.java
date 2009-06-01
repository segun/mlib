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

    public static Vector splitString(String string, String delimiter) {
        Vector retValue = new Vector();
        String myString = string + delimiter;
        while (myString.length() > 0) {
            try {
                String one = myString.substring(0, myString.indexOf(delimiter));
                retValue.addElement(one);
                myString = myString.substring(one.length() + delimiter.length(), myString.length());
            } catch (StringIndexOutOfBoundsException siobe) {
                break;
            }
        }
        return retValue;
    }
}
