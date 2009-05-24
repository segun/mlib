/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.trinisoft.mlib.db.decorator;

import com.trinisoft.mlib.db.BaseStore;
import java.io.IOException;
import javax.microedition.rms.RecordComparator;
import javax.microedition.rms.RecordFilter;

/**
 *
 * @author segun
 */
public abstract class BaseStoreAdapter extends BaseStore {

    protected BaseStoreAdapter(BaseStore baseStore) {
        
    }
    
    public boolean delete() {
        return true;
    }

    public boolean fromByteArray(byte[] data) throws IOException {
        System.out.println("decorator fromByteArray");
        return true;
    }

    public byte[] toByteArray() throws IOException {
        System.out.println("decorator toByteArray");
        return new byte[1000];
    }

    public boolean get(RecordComparator rc, RecordFilter rf) {
        return true;
    }
}
