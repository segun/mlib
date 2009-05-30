/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.trinisoft.mlib.login.db;

import com.trinisoft.mlib.db.BaseStore;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 *
 * @author segun
 */
public class UserClass extends BaseStore {

    String username;
    String password;
    
    public boolean delete(RecordStore rs, int recordID) throws RecordStoreException {        
        return true;
    }
    
    public boolean update(RecordStore rs, byte[] newData, int recordID) throws RecordStoreException {
        return true;
    }
    
    public boolean fromByteArray(byte[] data) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);

        this.username = dis.readUTF();
        this.password = dis.readUTF();
        
        return true;
    }
    
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeUTF(username);
        dos.writeUTF(password);

        byte data[] = baos.toByteArray();
        return data;
    }

    public int save(RecordStore rs) throws RecordStoreException, IOException {
        return 1;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }    
}
