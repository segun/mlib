/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.baselib.db;

import com.trinisoft.baselib.util.Echo;
import java.io.IOException;
import javax.microedition.rms.RecordFilter;

/**
 *
 * @author trinisoftinc
 */
public class FindBaseStoreFilter implements RecordFilter {

    int id;
    BaseStore storeClass;

    public FindBaseStoreFilter(int id, BaseStore storeClass) {
        this.id = id;
        this.storeClass = storeClass;
    }

    public boolean matches(byte[] candidate) {
        try {
            storeClass.fromJSONString(new String(candidate));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if(storeClass.getId() == id) {
            return true;
        }
        return false;
    }
}
