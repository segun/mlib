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
    public static int isBB = platform.indexOf("BlackBerry");
    public static String getAPN(String network) {        
        Echo.outln("ISBB: " + (isBB >= 0));
        if (isBB < 0) {            
            return "";
        } else if (network.equals("Zain")) {
            return ZAIN_APN;
        } else if (network.equals("MTN")) {
            return MTN_APN;
        } else if (network.equals("GLO")) {
            return GLO_APN;
        } else return null;
    }
    public static final String USE_DEVICE_SIDE_STRING = ";deviceside=true";
    public static String MTN_APN = USE_DEVICE_SIDE_STRING + ";apn=web.gprs.mtnnigeria.net;"
            + "tunnelauthusername=web;tunnelauthpassword=web";
    public static String ZAIN_APN = USE_DEVICE_SIDE_STRING + ";apn=internet.ng.zain.com;"
            + "tunnelauthusername=web;tunnelauthpassword=web";
    public static String GLO_APN = USE_DEVICE_SIDE_STRING + ";apn=glowap;"
            + "tunnelauthusername=wap;tunnelauthpassword=wap";
}
