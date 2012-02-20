/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.baselib.db;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import trinisoftinc.json.me.MyJSONArray;

/**
 *
 * @author trinisoftinc
 */
public class StorableList extends Vector implements BaseStore {

    public void setId(int id) {
    }

    public StorableList() {
        super();
    }

    public void addElement(String o) {
        super.addElement(o);
    }

    public boolean delete(RecordStore rs, int recordID) throws RecordStoreException {
        return true;
    }

    public void fromJSONString(String json) throws Exception {
        MyJSONArray array = new MyJSONArray(json);
        for (int i = 0; i < array.length(); i++) {
            this.addElement(array.get(i));
        }
    }

    public String toJSONString() {
        Enumeration enu = this.elements();
        MyJSONArray array = new MyJSONArray();
        while (enu.hasMoreElements()) {
            array.put(enu.nextElement());
        }
        return array.toString();
    }

    public int save(RecordStore rs) throws RecordStoreException, IOException {
        return 1;
    }

    public boolean update(RecordStore rs) throws RecordStoreException {
        return true;
    }

    public BaseStore find(RecordStore rs, int id) {
        return this;
    }

    public int getId() {
        return 0;
    }
}
