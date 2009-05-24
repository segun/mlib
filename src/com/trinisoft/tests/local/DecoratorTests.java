/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.tests.local;

import com.trinisoft.mlib.db.BaseStore;
import com.trinisoft.mlib.db.decorator.StoreDecorator;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.microedition.midlet.*;
import javax.microedition.rms.RecordComparator;
import javax.microedition.rms.RecordFilter;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 * @author segun
 */
class Testing extends BaseStore {

    String name;
    int age;

    public boolean delete() {
        return true;
    }

    public boolean fromByteArray(byte[] data) throws IOException {
        System.out.println("class fromByteArray");
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);

        age = dis.readInt();
        name = dis.readUTF();

        return true;
    }

    public byte[] toByteArray() throws IOException {
        System.out.println("class toByteArray");
        byte data[] = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(age);
        dos.writeUTF(name);

        data = baos.toByteArray();
        dos.close();
        baos.close();

        return data;
    }

    public boolean get(RecordComparator rc, RecordFilter rf) {
        return true;
    }

    public int save(RecordStore rs) throws IOException, RecordStoreException {
        return 1;
    }

    public boolean update(byte[] newData) {
        return true;
    }
}

public class DecoratorTests extends MIDlet {

    public void startApp() {
        Testing testing = new Testing();
        testing.age = 123;
        testing.name = "adeolu afolabi 3";
        StoreDecorator storeDecorator = new StoreDecorator(testing);
        try {
            RecordStore store = RecordStore.openRecordStore("TestingDecorators", true);
            storeDecorator.save(store);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch(RecordStoreException rse) {
            rse.printStackTrace();
        }
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }
}
