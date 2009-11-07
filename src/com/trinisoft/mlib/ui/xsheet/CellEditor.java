/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.trinisoft.mlib.ui.xsheet;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;

/**
 *
 * @author trinisoftinc
 */
public class CellEditor extends TextBox {

        public CellEditor(int currentRow, int currentCol, String[][] data, CanvasTable canvasTable) {
            super("Cell Editor", data[currentRow - 1][currentCol], 255, TextField.ANY);
            this.addCommand(new Command("Update", Command.OK, 0));
            this.addCommand(new Command("Cancel", Command.BACK, 1));
            this.setCommandListener(new CanvasTableCommander(this, canvasTable));
        }
}
