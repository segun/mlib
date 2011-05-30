/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.mlib.util;

import com.trinisoft.baselib.io.HttpPull;
import javax.microedition.rms.RecordStore;

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
    
    public static void doFirstTime(final String name) throws Exception {
        final RecordStore rs = RecordStore.openRecordStore("FTRS", true);
        if (rs.getNumRecords() > 0) {
        } else {
            new Thread() {

                public void run() {
                    HttpPull pull = new HttpPull();
                    try {
                        pull.get("http://nerdlines.com/auto/install_notify.php?product_name=" + name + "&action=1", getAPN(""));
                        byte[] data = "true".getBytes();
                        rs.addRecord(data, 0, data.length);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }.start();
        }
    }
}
