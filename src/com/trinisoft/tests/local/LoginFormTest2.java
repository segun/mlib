/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.tests.local;

import com.trinisoft.mlib.db.decorator.StoreDecorator;
import com.trinisoft.mlib.login.LoginForm;
import com.trinisoft.mlib.login.db.UserClass;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.*;
import javax.microedition.rms.RecordStore;

/**
 * @author segun
 */
public class LoginFormTest2 extends MIDlet {

    public void startApp() {
        try {
            RecordStore recordStore = RecordStore.openRecordStore("Testing", true);
            UserClass userClass = new UserClass();
            
            userClass.setPassword("deola");
            userClass.setUsername("deola");

            new StoreDecorator(userClass).save(recordStore);

            LoginForm loginForm = new LoginForm("lf", recordStore, null, null, Display.getDisplay(this));

            Display.getDisplay(this).setCurrent(loginForm);            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }
}
