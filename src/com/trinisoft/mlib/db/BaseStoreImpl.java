/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.trinisoft.mlib.db;

import java.io.IOException;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 *
 * @author trinisoftinc
 */
public class BaseStoreImpl implements BaseStore {

    public boolean delete(RecordStore rs, int recordID) throws RecordStoreException {
        return true;
    }

    public boolean fromByteArray(byte[] data) throws IOException {
        return true;
    }

    public int save(RecordStore rs) throws RecordStoreException, IOException {
        return rs.getNumRecords();
    }

    public byte[] toByteArray() throws IOException {
        return new byte[255];
    }

    public boolean update(RecordStore rs, byte[] newData, int recordID) throws RecordStoreException {
        return true;
    }

}
