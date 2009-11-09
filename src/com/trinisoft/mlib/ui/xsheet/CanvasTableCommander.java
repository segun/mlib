/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.mlib.ui.xsheet;

import java.io.DataInput;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;

/**
 *
 * @author trinisoftinc
 */
public class CanvasTableCommander implements CommandListener {

    CellEditor cellEditor;
    CanvasTable canvasTable;

    public CanvasTableCommander(CellEditor cellEditor, CanvasTable canvasTable) {
        this.cellEditor = cellEditor;
        this.canvasTable = canvasTable;
    }

    public void commandAction(Command c, Displayable d) {
        if (c.getLabel().equals("Edit Cell")) {
            cellEditor.setString(canvasTable.data[canvasTable.currentRow - 1][canvasTable.currentCol]);
            canvasTable.display.setCurrent(cellEditor);
        }

        if (c.getLabel().equals("Copy Cell")) {
            canvasTable.cellClipBoard = canvasTable.data[canvasTable.currentRow - 1][canvasTable.currentCol];
            canvasTable.arrayClipBoard = null;
            try {
                canvasTable.removeCommand(canvasTable.pasteCommand);
            } catch (Exception e) {
            }
            canvasTable.addCommand(canvasTable.pasteCommand);
        }

        if (c.getLabel().equals("Copy Row")) {
            canvasTable.cellClipBoard = null;
            canvasTable.arrayClipBoard = new String[canvasTable.numCol];
            for (int i = 0; i < canvasTable.numCol; i++) {
                canvasTable.arrayClipBoard[i] = canvasTable.data[canvasTable.currentRow - 1][i];
            }
            try {
                canvasTable.removeCommand(canvasTable.pasteCommand);
            } catch (Exception e) {
            }
            canvasTable.addCommand(canvasTable.pasteCommand);
            canvasTable.clipBoard = "row";
        }

        if (c.getLabel().equals("Copy Column")) {
            canvasTable.cellClipBoard = null;
            canvasTable.arrayClipBoard = new String[canvasTable.numRow];

            for (int i = 0; i < canvasTable.numRow; i++) {
                canvasTable.arrayClipBoard[i] = canvasTable.data[i][canvasTable.currentCol];
            }
            try {
                canvasTable.removeCommand(canvasTable.pasteCommand);
            } catch (Exception e) {
            }
            canvasTable.addCommand(canvasTable.pasteCommand);
            canvasTable.clipBoard = "col";
        }

        if (c.getLabel().equals("Paste")) {
            if (canvasTable.cellClipBoard != null) {
                canvasTable.data[canvasTable.currentRow - 1][canvasTable.currentCol] = canvasTable.cellClipBoard;
            } else {
                if (canvasTable.clipBoard.equals("row")) {
                    for (int i = 0; i < canvasTable.arrayClipBoard.length; i++) {
                        canvasTable.data[canvasTable.currentRow - 1][i] = canvasTable.arrayClipBoard[i];
                    }
                }
                if (canvasTable.clipBoard.equals("col")) {
                    for (int i = 0; i < canvasTable.arrayClipBoard.length; i++) {
                        canvasTable.data[i][canvasTable.currentCol] = canvasTable.arrayClipBoard[i];
                    }
                }
            }
            canvasTable.repaint();
        }

        if (c.getLabel().equals("Add Row")) {
            String[][] temp = canvasTable.data;
            canvasTable.numRow++;
            canvasTable.data = new String[canvasTable.numRow][canvasTable.numCol];
            for (int i = 0; i < temp.length; i++) {
                for (int j = 0; j < temp[i].length; j++) {
                    canvasTable.data[i][j] = temp[i][j];
                }
            }

            for (int i = 0; i < canvasTable.numCol; i++) {
                canvasTable.data[canvasTable.numRow - 1][i] = "";
            }

            canvasTable.repaint();
        }

        if(c.getLabel().equals("Add Column")) {
            String[][] temp = canvasTable.data;
            canvasTable.numCol++;
            canvasTable.data = new String[canvasTable.numRow][canvasTable.numCol];
            for (int i = 0; i < temp.length; i++) {
                for (int j = 0; j < temp[i].length; j++) {
                    canvasTable.data[i][j] = temp[i][j];
                }
            }
            for (int i = 0; i < canvasTable.numRow; i++) {
                canvasTable.data[i][canvasTable.numCol] = "";
            }

            canvasTable.repaint();
        }

        if(c.getLabel().equals("Insert Row")) {
            String[][] temp = canvasTable.data;
            int currentRow = canvasTable.currentRow - 1;
            canvasTable.numRow++;
            canvasTable.data = new String[canvasTable.numRow][canvasTable.numCol];

            for(int i = 0; i < canvasTable.numRow; i++) {
                for(int j = 0; j < canvasTable.numCol; j++) {
                    if(i == currentRow) {
                        for(int k = 0; k < temp[i].length; k++) {
                            canvasTable.data[i][k] = "";
                        }
                    } else if(i > currentRow) {
                        canvasTable.data[i][j] = temp[i - 1][j];
                    } else if(i < currentRow) {
                        canvasTable.data[i][j] = temp[i][j];
                    }
                }
            }

            canvasTable.repaint();
        }

        //Cell Editor
        if (d.equals(cellEditor) && c.getLabel().equals("Update")) {
            canvasTable.data[canvasTable.currentRow - 1][canvasTable.currentCol] = cellEditor.getString();
            canvasTable.repaint();
            canvasTable.display.setCurrent(canvasTable);
        }
        if (d.equals(cellEditor) && c.getLabel().equals("Cancel")) {
            canvasTable.display.setCurrent(canvasTable);
        }
    }
}
