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
public class StoreDecorator extends StoreDecoratorAdapter {

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
        }
        return -1;
    }

    public boolean delete(RecordStore rs, int recordID) throws RecordStoreException {
        baseStore.delete(rs, recordID);
        rs.deleteRecord(recordID);
        return true;
    }

    public boolean update(RecordStore rs, byte[] newData, int recordID) throws RecordStoreException {
        baseStore.update(rs, newData, recordID);
        rs.setRecord(recordID, newData, 0, newData.length);
        return true;
    }

    
}
