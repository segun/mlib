/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.mlib.util;

/**
 *
 * @author trinisoftinc
 */
public class URLEncoder {

    public static String encode(String str) {
        if (str == null) {
            return null;
        }

        StringBuffer resultStr = new StringBuffer(str.length());
        char tmpChar;

        for (int ix = 0; ix < str.length(); ix++) {
            tmpChar = str.charAt(ix);
            switch (tmpChar) {
                case ' ':
                    resultStr.append("%20");
                    break;
                case '-':
                    resultStr.append("%2D");
                    break;
                case '/':
                    resultStr.append("%2F");
                    break;
                case ':':
                    resultStr.append("%3A");
                    break;
                case '=':
                    resultStr.append("%3D");
// System.out.println( "tmpChar = '=' " "add %3D " );
                    break;
                case '?':
                    resultStr.append("%3F");
// System.out.println( "tmpChar = '?' " "add %3F " );
                    break;
                case '#':
                    resultStr.append("%23");
                    break;
                case '\r':
                    resultStr.append("%0D");
                    break;
                case '\n':
                    resultStr.append("%0A");
                    break;
                default:
                    resultStr.append(tmpChar);
                    break;
            }
        }
        return resultStr.toString();
    }
}
