/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.tests.local;

import com.trinisoft.mlib.Color;
import com.trinisoft.mlib.ui.CanvasTable;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.*;

/**
 * @author trinisoftinc
 */
public class CanvasTableTest extends MIDlet {

    public void startApp() {

        String header[] = {
            "A", "B", "C","D","E","F","G","H"
        };

        String data[][] = {
            {"one", "two", "three","1","2","3","four","five"},
            {"four", "five", "six","1","2","3","four","five"},
            {"7", "8", "9","1","2","3","four","five"},
            {"10", "eleven", "twelve","1","2","3","four","five"},
            {"10", "eleven", "twelve","1","2","3","four","five"},
            {"10", "eleven", "twelve","1","2","3","four","five"},
            {"10", "eleven", "twelve","1","2","3","four","five"},
            {"10", "eleven", "twelve","1","2","3","four","five"}};

        CanvasTable cvTable = null;
        
        try {
            cvTable = new CanvasTable(header, data);
            cvTable.setBgColor(new Color(0, 0, 0));
            cvTable.setFontColor(new Color(255, 255, 255));
            cvTable.setHeaderBGColor(new Color(100, 100, 100));
            Display.getDisplay(this).setCurrent(cvTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }
}
