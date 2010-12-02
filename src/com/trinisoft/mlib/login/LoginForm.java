package com.trinisoft.mlib.login;

import com.trinisoft.mlib.login.db.UserClass;
import com.trinisoft.mlib.ui.TickerForm;
import java.io.IOException;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextField;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author segun
 */
public class LoginForm extends TickerForm implements CommandListener {

    String username;
    String password;
    
    RecordStore recordStore;
    int method;
    TextField usernameField, passwordField;
    Command cmdOK = new Command("Login", Command.OK, 0);
    Displayable loggedInDisplayable;
    Displayable errorDisplayable;
    Display display;

    public LoginForm(String title, String username, String password, Displayable loggedInDisplayable, Displayable errorDisplayable, Display display, String initialTickerText, String tickerURL) {
        super(title, initialTickerText, tickerURL);
        this.username = username;
        this.password = password;
        this.loggedInDisplayable = loggedInDisplayable;
        this.errorDisplayable = errorDisplayable;
        this.display = display;

        method = 0;
        init();
    }

    public LoginForm(String title, RecordStore recordStore, Displayable loggedInDisplayable, Displayable errorDisplayable, Display display, String initialTickerText, String tickerURL) {
        super(title, initialTickerText, tickerURL);
        this.recordStore = recordStore;

        this.loggedInDisplayable = loggedInDisplayable;
        this.errorDisplayable = errorDisplayable;
        this.display = display;

        method = 1;

        init();
    }

    void init() {
        usernameField = new TextField("Username", "", 255, TextField.ANY);
        passwordField = new TextField("Password", "", 255, TextField.PASSWORD);

        this.append(usernameField);
        this.append(passwordField);

        addCommand(cmdOK);
        setCommandListener(this);
    }

    public boolean login() throws RecordStoreNotOpenException, RecordStoreException, IOException {
        String formUsername = usernameField.getString();
        String formPassword = passwordField.getString();

        if (method == 0) {
            if (username.equals(formUsername) && password.equals(formPassword)) {
                return true;
            }
        } else if (method == 1) {
            RecordEnumeration re = recordStore.enumerateRecords(null, null, true);            
            while (re.hasNextElement()) {
                UserClass recordUserClass = new UserClass();                
                recordUserClass.fromByteArray(re.nextRecord());
                System.out.println("formusername: " + formUsername + " RE: " + recordUserClass.getUsername());
                System.out.println("formpassword: " + formPassword + " RE: " + recordUserClass.getPassword());
                if (formUsername.equals(recordUserClass.getUsername()) &&
                        formPassword.equals(recordUserClass.getPassword())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void commandAction(Command c, Displayable d) {
        try {
            if (login()) {
                System.out.println("Logged");
                if (loggedInDisplayable != null) {
                    display.setCurrent(loggedInDisplayable);
                }
            } else {
                if(errorDisplayable != null) {
                    display.setCurrent(errorDisplayable);
                }
                
                System.out.println("bounced");

            }
        } catch (RecordStoreException rse) {
        } catch (IOException ioe) {
        }
    }
}