/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.mlib.db;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 *
 * @author trinisoftinc
 */
public class StorableList extends Vector implements BaseStore {
        
    public StorableList() {
        super();
    }

    public void addElement(Object o) {
        if(o instanceof byte[]) {
            super.addElement(o);
            
        } else {
            throw new IllegalArgumentException("Please convert " + o + " bytes before adding it to Storable List");
        }
    }
        
    public boolean delete(RecordStore rs, int recordID) throws RecordStoreException {
        return true;
    }

    public boolean fromByteArray(byte[] data) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);
        
        int size = dis.readInt();
        
        this.removeAllElements();
        
        for(int i = 0; i < size; i++) {
            this.addElement(dis.readUTF().getBytes());
        }
        
        return true;
    }

    public int save(RecordStore rs) throws RecordStoreException, IOException {
        return 1;
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        byte[] data = null;
        
        dos.writeInt(this.size());
        Enumeration enu = this.elements();
        while(enu.hasMoreElements()) {            
            dos.writeUTF(new String((byte[]) enu.nextElement()));
        }
        dos.flush();
        data = baos.toByteArray();
        return data;
    }

    public boolean update(RecordStore rs, byte[] newData, int recordID) throws RecordStoreException {
        return true;
    }
    
}
