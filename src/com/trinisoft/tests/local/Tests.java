/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.trinisoft.tests.local;

import com.trinisoft.mlib.MStrings;
import com.trinisoft.mlib.MUtils;
import java.util.Enumeration;
import java.util.Vector;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.*;

/**
 * @author root
 */
public class Tests extends MIDlet {
    public void startApp() {
        List list = new List("Test", List.EXCLUSIVE);
        String ddd = "dele, femi, segun, dare";
        Vector vec = MStrings.strToVector(ddd);
        Enumeration enu = vec.elements();
        String s;
        while(enu.hasMoreElements()) {

            list.append(enu.nextElement().toString(), null);
        }
        Vector v = new Vector();
        v.addElement("ade");
        v.addElement("dele");
        v.addElement("seun");
        System.out.println(String.valueOf(MUtils.hasElement(v.elements(), "segun")));
        System.out.println(String.valueOf(MUtils.hasElement(v.elements(), "dele")));
        Display.getDisplay(this).setCurrent(list);
        MStrings.splitString("name=deji:sname=segun:sage=21:sage=19", ":s");
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }
}
