/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.mlib.util;

/**
 *
 * @author trinisoftinc
 */
public class URLConstants extends com.trinisoft.baselib.util.URLConstants {
    
    public static final String BASE_NEW_VERSION_URL = BASE_URL + "version.php?action=GET_VERSION_URL&model=NONE";    
    public static String platform = System.getProperty("microedition.platform");

    public static String getConnectionParams() {
        return "";
    }

    public static String getAPN(String not_used) {
        return "";
    }
}
