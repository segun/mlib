/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoftinc.mlib.util;

import com.trinisoft.baselib.util.MStrings;
import javax.microedition.midlet.MIDlet;

/**
 *
 * @author trinisoftinc
 */
public class URLConstants {
    public static final String BASE_URL = "http://nerdlines.com/mobile/";    
    //public static final String BASE_URL = "http://localhost/nerdlines/mobile/";    
    
    public static final String BASE_CHECK_VERSION_URL = BASE_URL + "version.php?action=GET_VERSION_NUMBER";
    
    public static final String ADS_URL = BASE_URL + "mobilead.php?";       
    
    public static final String BASE_NEW_VERSION_URL = BASE_URL + "version.php?action=GET_VERSION_URL&model=NONE";

    public static String getConnectionParams() {
        return "";
    }

    public static String getAPN(String not_used) {
        return "";
    }
}
