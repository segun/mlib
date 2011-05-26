/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.mlib.views;

import com.sun.lwuit.Calendar;
import com.sun.lwuit.Command;
import com.sun.lwuit.events.ActionListener;
import com.trinisoft.baselib.util.Date;
import javax.microedition.midlet.MIDlet;

/**
 *
 * @author trinisoftinc
 */
public class ShowDateForm extends BaseForm {

    MIDlet parentMidlet;
    Date dateToUpdate;
    public static Calendar dateCal = new Calendar(System.currentTimeMillis());
    public static Command okCommand;    
    public static final int SELECT_DATE_ACTION = 2022002;

    public ShowDateForm(MIDlet parentMidlet) {
        super(parentMidlet);
        this.parentMidlet = parentMidlet;
        init();
    }

    public void setActionListener(ActionListener listener) {        
        addCommandListener(listener);
    }

    private void init() {
        setTitle("Select Day");
        addComponent(dateCal);
        addCommand((okCommand = new Command("OK", SELECT_DATE_ACTION)));
        setBackCommand(okCommand);
    }
}
