/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.mlib.views;

import com.sun.lwuit.Component;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextField;
import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.events.DataChangedListener;
import com.sun.lwuit.events.FocusListener;
import com.sun.lwuit.layouts.GridLayout;
import com.trinisoft.baselib.util.Echo;

/**
 *
 * @author Akintayo A. Olusegun
 */
public class BaseDialog extends Dialog {

    public BaseDialog(String title) {
        super(title);
        setLayout(new GridLayout(10, 1));
        CommonTransitions in = CommonTransitions.createFade(500);
        setTransitionInAnimator(in);        
    }
    
    public void addLabel(String labString) {
        addComponent(new Label(labString));
    }
}
