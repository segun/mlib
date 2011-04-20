/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.mlib.util;

/**
 *
 * @author trinisoftinc
 */
public class URLConstants {
    public static String platform = System.getProperty("microedition.platform");
    
    public static String MTN_APN = ";apn=web.gprs.mtnnigeria.net;"
            + "tunnelauthusername=web;tunnelauthpassword=web";
    public static String ZAIN_APN = ";apn=internet.ng.zain.com;"
            + "tunnelauthusername=web;tunnelauthpassword=web";
    public static String GLO_APN = ";apn=glowap;"
            + "tunnelauthusername=wap;tunnelauthpassword=wap";
    public static final String ADS_URL = "http://www.statusforsale.net/MANUAL_EXECUTION/mobilead.php";
}
