/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.trinisoft.mlib.db.decorator;

import com.trinisoft.mlib.db.BaseStore;
import java.io.IOException;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 *
 * @author segun
 */
public class StoreDecorator extends BaseStoreAdapter {

    public static final int MAXINT = 1000;
    BaseStore baseStore;

    public StoreDecorator(BaseStore baseStore) {
        super(baseStore);
        this.baseStore = baseStore;
    }
    
    public int save(RecordStore rs) throws RecordStoreException, IOException {
        byte[] data = baseStore.toByteArray();
        if(baseStore.save(rs) >= 0) {
            int id = rs.addRecord(data, 0, data.length);
            System.out.println("ID: " + id);
            return id;
        } else {
            System.out.println("class save no return true " + data);
        }
        return -1;
    }

    public boolean update(byte[] newData) {
        return true;
    }

}
