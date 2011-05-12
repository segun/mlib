/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.mlib.views;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.GridLayout;
import com.sun.lwuit.util.Resources;
import javax.microedition.midlet.MIDlet;

/**
 *
 * @author trinisoftinc
 */
public class BaseForm extends Form {

    MIDlet midlet;
    //BaseDialog errorDialog, loginDialog, messageDialog;    
    Image advertImage;
    boolean isRunning = false;
    public CommonTransitions in;

    public BaseForm(MIDlet midlet) {
        super("");
        setLayout(new GridLayout(10, 1));
        this.midlet = midlet;
        in = CommonTransitions.createFade(500);
        setTransitionInAnimator(in);
    }

    public BaseForm() {
        super("");
    }
    
    

    public boolean showMessageDialog(String title, String message, boolean display) {
        return Dialog.show(title, message, "OK", "Cancel");
//        return messageDialog;
    }

    protected void addLabel(String label) {
        addComponent(new Label(label));
    }    
    
    public Dialog showPleaseWait(String pleaseWaitTitle, String pleaseWaitText, Resources r, String loadingID) {
        //Dialog.show(pleaseWaitTitle, pleaseWaitText, Dialog.TYPE_INFO, r.getImage(loadingID), "OK", "Cancel") ;
        Dialog d = new Dialog(pleaseWaitTitle);
        d.setTransitionInAnimator(in);
        //d.setLayout(new GridLayout(10, 1));
        TextArea text = new TextArea(pleaseWaitText);
        text.setEditable(false);
        Label l = new Label();
        l.setIcon(r.getImage(loadingID));
        l.setAlignment(Label.CENTER);
        d.addComponent(text);
        d.addComponent(l);
        Command c = new Command("OK");
        d.addComponent(new Button(c));
        d.setBackCommand(c);

        d.addCommandListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
            }
        });
        d.showModeless();
        return d;
    }
}
