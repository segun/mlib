/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.baselib.db;

import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 *
 * @author trinisoftinc
 */
public interface BaseStore {
    public void setId(int id);
    public int getId();
    public int save(RecordStore rs) throws Exception;
    public boolean delete(RecordStore rs, int recordID) throws RecordStoreException;
    public boolean update(RecordStore rs) throws RecordStoreException;
    public BaseStore find(RecordStore rs, int id);
    public void fromJSONString(String json) throws Exception;
    public String toJSONString() throws Exception;
}
