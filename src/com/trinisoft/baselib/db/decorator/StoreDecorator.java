/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.trinisoft.baselib.db.decorator;

import com.trinisoft.baselib.db.BaseStore;
import com.trinisoft.baselib.util.Echo;
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

//    public int save(RecordStore rs, boolean isJSON) throws IOException, RecordStoreException {
//        byte[] data = baseStore.toJSONString().getBytes();
//        return save(rs, data);
//
//    }

    public int save(RecordStore rs) throws Exception {
        byte[] data = baseStore.toJSONString().getBytes();
        return save(rs, data);
    }

    private int save(RecordStore rs, byte[] data) throws Exception {
        if(baseStore.save(rs) >= 0) {
            int id = rs.addRecord(data, 0, data.length);
            baseStore.setId(id);
            data = baseStore.toJSONString().getBytes();
            rs.setRecord(id, data, 0, data.length);
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
    
    public boolean update(RecordStore rs) throws RecordStoreException {
        Echo.outln("StoreDecorator:48 = " + baseStore.getId());
        byte[] newData = null;
        try {
            newData = baseStore.toJSONString().getBytes();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        baseStore.update(rs);
        rs.setRecord(baseStore.getId(), newData, 0, newData.length);
        return true;
    }

    public int getId() {
        return 0;
    }

    public BaseStore find(RecordStore rs, int id) {
        return this;
    }
}
