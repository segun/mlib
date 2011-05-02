/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.mlib.views;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.GridLayout;
import com.sun.lwuit.plaf.Border;
import javax.microedition.midlet.MIDlet;

/**
 *
 * @author trinisoftinc
 */
public class BaseForm extends Form {

    MIDlet midlet;
    BaseDialog errorDialog, loginDialog, messageDialog;    
    Image advertImage;
    boolean isRunning = false;
    public CommonTransitions in, out;

    public BaseForm(MIDlet midlet) {
        super("");
        setLayout(new GridLayout(10, 1));
        this.midlet = midlet;
        in = CommonTransitions.createFade(500);
        out = CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, 500);
        setTransitionInAnimator(in);
    }

    public BaseDialog getMessageDialog(String message, boolean display) {
        messageDialog = new BaseDialog("MESSAGE");
        messageDialog.getStyle().setBorder(Border.createLineBorder(5, 0x00faaf));
        messageDialog.addComponent(new Button(new Command(message)));
        Command okCommand = new Command("OK");
        messageDialog.setBackCommand(okCommand);
        messageDialog.addCommand(okCommand);                
        messageDialog.addCommandListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                messageDialog.dispose();
            }
        });
        messageDialog.setTransitionInAnimator(in);
        if (display) {
            messageDialog.showDialog();
        }
        return messageDialog;
    }

    public BaseDialog getErrorDialog(String error, boolean display) {
        errorDialog = new BaseDialog("ERROR");
        errorDialog.getStyle().setBorder(Border.createLineBorder(5, 0xff0000));
        errorDialog.addComponent(new Button(new Command(error)));
        Command okCommand = new Command("OK");
        errorDialog.setBackCommand(okCommand);
        errorDialog.addCommand(okCommand);
        errorDialog.addCommandListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                errorDialog.dispose();
            }
        });
        errorDialog.setTransitionInAnimator(in);
        if (display) {
            errorDialog.showDialog();
        }        
        return errorDialog;
    }

    protected void addLabel(String label) {
        addComponent(new Label(label));
    }    
}
