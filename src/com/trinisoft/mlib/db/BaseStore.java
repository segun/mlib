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
public interface BaseStore {
    public byte[] toByteArray() throws IOException;
    public boolean fromByteArray(byte[] data) throws IOException;
    public int save(RecordStore rs) throws RecordStoreException, IOException;
    public boolean delete(RecordStore rs, int recordID) throws RecordStoreException;
    public boolean update(RecordStore rs, byte[] newData, int recordID) throws RecordStoreException;    
}
