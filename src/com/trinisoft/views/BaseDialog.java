/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.views;

import com.sun.lwuit.Component;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.TextField;
import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.events.DataChangedListener;
import com.sun.lwuit.events.FocusListener;
import com.trinisoft.mlib.util.Echo;

/**
 *
 * @author Akintayo A. Olusegun
 */
public class BaseDialog extends Dialog {

    public BaseDialog(String title) {
        super(title);
        CommonTransitions in = CommonTransitions.createFade(500);
        setTransitionInAnimator(in);        
        addFocusListener(new FocusListener() {

            public void focusGained(Component cmpnt) {
                if (cmpnt instanceof TextField) {
                    final TextField t = (TextField) cmpnt;
                    t.addDataChangeListener(new DataChangedListener() {

                        public void dataChanged(int i, int i1) {
                            t.setCursorPosition(t.getText().length());
                        }
                    });
                }
            }

            public void focusLost(Component cmpnt) {
                Echo.outln("I Lost Focus");
            }
        });
    }
}
