/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.baselib.db;

import java.io.IOException;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;

/**
 *
 * @author trinisoftinc
 */
public abstract class BaseStoreImpl implements BaseStore {

    public boolean delete(RecordStore rs, int recordID) throws RecordStoreException {
        return true;
    }

    public int save(RecordStore rs) throws RecordStoreException, IOException {
        return rs.getNumRecords();
    }

    public boolean update(RecordStore rs) throws RecordStoreException {
        return true;
    }

    public BaseStore find(RecordStore rs, int id) {
        try {
            RecordEnumeration enu = rs.enumerateRecords(new FindBaseStoreFilter(id, this), null, true);
            if (enu.hasNextElement()) {
                BaseStoreImpl storeImpl = this;
                storeImpl.fromJSONString(new String(enu.nextRecord()));
                return storeImpl;
            }
        } catch (InvalidRecordIDException ex) {
            ex.printStackTrace();
        } catch (RecordStoreNotOpenException ex) {
            ex.printStackTrace();
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        } catch(Exception ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    public void setId(int id) {
    }

    public int getId() {
        return -1;
    }

    public void fromJSONString(String json) throws Exception {

    }

    public String toJSONString() throws Exception {
        return "";
    }


}
