/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.baselib.util;

import java.util.Vector;

/**
 *
 * @author root
 */
public class MStrings {

    public static boolean contains(String from, String check) {
        return from.indexOf(check) >= 0 ? true : false;
    }

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

    public static String replace(String from, String oldString, String newString) {
        int oldStringStartIndex = from.indexOf(oldString);
        int oldStringEndIndex = oldStringStartIndex + oldString.length();
        String newFrom = from.substring(0, oldStringStartIndex) + newString + from.substring(oldStringEndIndex, from.length());
        //System.out.println(newFrom);
        return newFrom;
    }

    public static String replaceAll(String from, String oldString, String newString) {
        int oldStringStartIndex = from.indexOf(oldString);
        String newFrom = from;
        while (oldStringStartIndex >= 0) {
            int oldStringEndIndex = oldStringStartIndex + oldString.length();
            newFrom = newFrom.substring(0, oldStringStartIndex) + newString + newFrom.substring(oldStringEndIndex, newFrom.length());
            oldStringStartIndex = newFrom.indexOf(oldString);
        }
        //System.out.println(newFrom);
        return newFrom;
    }

    public static String[] sort(String toSort[], boolean asc) {
        String[] retVal = toSort;
        for (int i = 0; i < retVal.length; i++) {
            for (int j = i; j < retVal.length; j++) {
                if (asc) {
                    if (retVal[i].compareTo(retVal[j]) > 0) {
                        String temp = retVal[i];
                        retVal[i] = retVal[j];
                        retVal[j] = temp;
                    }
                } else {
                    if (retVal[i].compareTo(retVal[j]) < 0) {
                        String temp = retVal[i];
                        retVal[i] = retVal[j];
                        retVal[j] = temp;
                    }
                }
            }
        }
        return retVal;
    }

    public static String[] add(String[] a, String[] b) {
        String[] retVal = new String[a.length + b.length];
        int i = 0;
        for (i = 0; i < a.length; i++) {
            retVal[i] = a[i];
        }
        for (int j = 0; j < b.length; j++) {
            retVal[j + i] = b[j];
        }

        return retVal;
    }

    public static boolean isNumeric(String a) {
        try {
            Long.parseLong(a);
            return true;
        } catch (NumberFormatException nfe) {
            try {
                Integer.parseInt(a);
                return true;
            } catch (NumberFormatException nfe2) {
                return false;
            }
        }
    }

    public static String encode(String normal) {
        byte[] data = normal.getBytes();
        String retval = "";
        for (int i = 0; i < data.length; i++) {
            retval += data[i] + "_";
        }        
        return retval;
    }

    public static String decode(String encoded) {
        Vector splitted = splitString(encoded, "_");
        byte[] data = new byte[splitted.size()];

        for (int i = 0; i < data.length; i++) {
            if (isNumeric(splitted.elementAt(i).toString())) {
                byte c = Byte.parseByte(splitted.elementAt(i).toString());
                data[i] = c;
            }
        }

        String decoded = new String(data);
        return decoded;
    }
}
