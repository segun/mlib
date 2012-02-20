/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.trinisoft.baselib.db.decorator;

import com.trinisoft.baselib.db.BaseStore;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 *
 * @author segun
 */
public abstract class StoreDecoratorAdapter implements BaseStore {

    protected StoreDecoratorAdapter(BaseStore baseStore) {
    }

    public void setId(int id) {

    }

    public boolean delete(RecordStore rs, int recordID) throws RecordStoreException {
        return true;
    }

    public int save(RecordStore rs) throws Exception {
        return 1;
    }

    public boolean update(RecordStore rs, byte[] newData, int recordID) throws RecordStoreException {
        return true;
    }

    public void fromJSONString(String json) throws Exception {

    }

    public String toJSONString() {
        return "";
    }



}