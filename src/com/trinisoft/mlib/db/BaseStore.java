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
 * @author segun
 */
public abstract class BaseStore {
    public abstract byte[] toByteArray() throws IOException;
    public abstract boolean fromByteArray(byte[] data) throws IOException;
    public abstract int save(RecordStore rs) throws RecordStoreException, IOException;
    public abstract boolean delete(RecordStore rs, int recordID) throws RecordStoreException, IOException;
    public abstract boolean update(RecordStore rs, byte[] newData, int recordID) throws RecordStoreException, IOException;
}
