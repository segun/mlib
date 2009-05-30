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
public abstract class BaseStoreAdapter extends BaseStore {

    protected BaseStoreAdapter(BaseStore baseStore) {        
    }

    public boolean delete(RecordStore rs, int recordID) throws RecordStoreException {
        return true;
    }

    public boolean fromByteArray(byte[] data) throws IOException {
        return true;
    }

    public int save(RecordStore rs) throws RecordStoreException, IOException {
        return 1;
    }

    public byte[] toByteArray() throws IOException {
        return new byte[1000];
    }

    public boolean update(RecordStore rs, byte[] newData, int recordID) throws RecordStoreException {
        return true;
    }


}