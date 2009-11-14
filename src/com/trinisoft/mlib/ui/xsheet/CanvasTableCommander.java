/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.mlib.ui.xsheet;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
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

        if (c.getLabel().equals("Add Column")) {
            String[][] temp = canvasTable.data;
            String tempHeader[] = canvasTable.header;

            canvasTable.numCol++;
            canvasTable.data = new String[canvasTable.numRow][canvasTable.numCol];
            canvasTable.header = new String[canvasTable.numCol];

            //the data
            for (int i = 0; i < temp.length; i++) {
                for (int j = 0; j < temp[i].length; j++) {
                    canvasTable.data[i][j] = temp[i][j];
                }
            }
            for (int i = 0; i < canvasTable.numRow; i++) {
                canvasTable.data[i][canvasTable.numCol - 1] = "";
            }

            canvasTable.computeHeader();

            canvasTable.repaint();
        }

        if (c.getLabel().equals("Insert Row")) {
            String[][] temp = canvasTable.data;
            int currentRow = canvasTable.currentRow - 1;
            canvasTable.numRow++;
            canvasTable.data = new String[canvasTable.numRow][canvasTable.numCol];

            for (int i = 0; i < canvasTable.numRow; i++) {
                for (int j = 0; j < canvasTable.numCol; j++) {
                    if (i == currentRow) {
                        for (int k = 0; k < temp[i].length; k++) {
                            canvasTable.data[i][k] = "";
                        }
                    } else if (i > currentRow) {
                        canvasTable.data[i][j] = temp[i - 1][j];
                    } else if (i < currentRow) {
                        canvasTable.data[i][j] = temp[i][j];
                    }
                }
            }

            canvasTable.repaint();
        }

        if (c.getLabel().equals("Insert Column")) {
            String[][] temp = canvasTable.data;
            int currentCol = canvasTable.currentCol;
            canvasTable.numCol++;
            canvasTable.data = new String[canvasTable.numRow][canvasTable.numCol];

            for (int i = 0; i < canvasTable.numRow; i++) {
                for (int j = 0; j < canvasTable.numCol; j++) {
                    if (j == currentCol) {
                        canvasTable.data[i][currentCol] = "";
                    } else if (j > currentCol) {
                        canvasTable.data[i][j] = temp[i][j - 1];
                    } else if (j < currentCol) {
                        canvasTable.data[i][j] = temp[i][j];
                    }
                }
            }

            canvasTable.computeHeader();
            canvasTable.repaint();
        }

        if (c.getLabel().equals("Delete Row")) {
            final int currentRow = canvasTable.currentRow - 1;
            Alert l = new Alert("xSheet Message", "Are you sure you want to delete row " + (currentRow + 1), null, AlertType.ALARM);
            l.setTimeout(Alert.FOREVER);

            l.addCommand(new Command("OK", Command.OK, 0));
            l.addCommand(new Command("Cancel", Command.BACK, 0));
            l.setCommandListener(new CommandListener() {

                public void commandAction(Command c, Displayable d) {
                    if (c.getLabel().equals("OK")) {
                        String temp[][] = canvasTable.data;
                        canvasTable.numRow--;
                        canvasTable.data = new String[canvasTable.numRow][canvasTable.numCol];

                        for (int i = 0; i < temp.length; i++) {
                            for (int j = 0; j < temp[i].length; j++) {
                                if (i == currentRow) {
                                } else if (i > currentRow) {
                                    canvasTable.data[i - 1][j] = temp[i][j];
                                } else if (i < currentRow) {
                                    canvasTable.data[i][j] = temp[i][j];
                                }
                            }
                        }
                        canvasTable.display.setCurrent(canvasTable);
                        canvasTable.repaint();
                    } else {
                        canvasTable.display.setCurrent(canvasTable);
                        canvasTable.repaint();
                    }
                }
            });
            canvasTable.display.setCurrent(l);
        }

        if (c.getLabel().equals("Delete Column")) {
            final int currentCol = canvasTable.currentCol;
            Alert l = new Alert("xSheet Message", "Are you sure you want to delete column " + canvasTable.header[currentCol], null, AlertType.ALARM);
            l.setTimeout(Alert.FOREVER);

            l.addCommand(new Command("OK", Command.OK, 0));
            l.addCommand(new Command("Cancel", Command.BACK, 0));
            l.setCommandListener(new CommandListener() {

                public void commandAction(Command c, Displayable d) {
                    if (c.getLabel().equals("OK")) {
                        System.out.println("CC: " + canvasTable.currentCol);
                        String temp[][] = canvasTable.data;
                        canvasTable.numCol--;
                        canvasTable.data = new String[canvasTable.numRow][canvasTable.numCol];

                        for (int i = 0; i < temp.length; i++) {
                            for (int j = 0; j < temp[i].length; j++) {
                                if (j == currentCol) {
                                } else if (j > currentCol) {
                                    canvasTable.data[i][j - 1] = temp[i][j];
                                } else if (i < currentCol) {
                                    canvasTable.data[i][j] = temp[i][j];
                                }
                            }
                        }
                        canvasTable.computeHeader();
                        canvasTable.display.setCurrent(canvasTable);
                        canvasTable.repaint();                        
                    } else {
                        canvasTable.computeHeader();
                        canvasTable.display.setCurrent(canvasTable);                        
                        canvasTable.repaint();
                    }
                }
            });
            canvasTable.display.setCurrent(l);
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
