/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.tests.local;

import com.trinisoft.mlib.login.LoginForm;
import java.io.IOException;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.*;
import javax.microedition.rms.RecordStoreException;

/**
 * @author segun
 */
public class LoginFormTests extends MIDlet {

    public void startApp() {
        final LoginForm loginForm = new LoginForm("Hello", "fred", "smith", null, null, Display.getDisplay(this));
        loginForm.addCommand(new Command("Cancel", Command.CANCEL, 1));

        loginForm.setCommandListener(new CommandListener() {

            public void commandAction(Command c, Displayable d) {
                if (c.getLabel().equals("Cancel")) {
                    System.out.println("Cancelled");
                }
                if (c.getLabel().equals("Login")) {
                    try {
                        if (loginForm.login()) {
                            System.out.println("Logged");
                        } else {
                            System.out.println("bounced");
                        }
                    } catch (RecordStoreException rse) {
                        rse.printStackTrace();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
        });

        Display.getDisplay(this).setCurrent(loginForm);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }
}
