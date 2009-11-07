/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.mlib.ui;

import com.trinisoft.mlib.Color;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;

/**
 *
 * @author trinisoftinc
 */
public class CanvasTable extends Canvas {

    private int numRow;
    private int numCol;
    private String[][] data;
    private String header[];
    private Color bgColor;
    private Color headerBGColor;
    private Color lineColor;
    private Color fontColor;
    private int yPlus = 25;
    private int xPlus = 50;
    private int clipX = 0;
    private int clipY = 0;
    private int currentRow = 1;
    private int currentCol = 0;
    private String labels[] = {
        "Edit Cell", "Copy Cell", "Add Row", "Add Column", "Insert Row", "Insert Column", "Copy Row", "Copy Column",
        "Delete Row", "Delete Column",};
    private Command commands[];
    private Display display;
    private MIDlet parent;
    private CellEditor cellEditor;
    private CanvasTable canvasTable;

    public CanvasTable(String header[], int numRow, int numCol) throws Exception {
        this.numCol = numCol;
        this.numRow = numRow;
        this.header = header;
        if (header.length != numCol) {
            throw new Exception("Header length mismatch");
        }

        data = new String[numRow][numCol];
        cellEditor = new CellEditor();

        init();

        canvasTable = this;
    }

    public CanvasTable(String header[], String[][] data) throws Exception {
        this.numRow = data.length;
        this.numCol = data[0].length;
        this.header = header;
        System.out.println(header.length + " : " + numRow);
        if (header.length != numCol) {
            throw new Exception("Header length mismatch");
        }

        this.data = data;

        cellEditor = new CellEditor();

        init();

        canvasTable = this;
    }

    private void init() {
        commands = new Command[labels.length];
        System.out.println(commands);
        for (int i = 0; i < labels.length; i++) {
            commands[i] = new Command(labels[i], Command.OK, i);
            addCommand(commands[i]);
        }
        
        this.setCommandListener(new Commander());
    }

    public void addData(int row, int col, String value) {
        if (row < numRow) {
            if (col < numCol) {
                data[row][col] = value;
            }
        }
    }

    public void addRow() {
        String[][] oldData = data;
        data = new String[numRow + 1][numCol];

        for (int i = 0; i < oldData.length; i++) {
            for (int j = 0; j < oldData[i].length; j++) {
                data[i][j] = oldData[i][j];
            }
        }
    }

    public void addColumn() {
        String[][] oldData = data;
        data = new String[numRow][numCol + 1];

        for (int i = 0; i < oldData.length; i++) {
            for (int j = 0; j < oldData[i].length; j++) {
                data[i][j] = oldData[i][j];
            }
        }
    }

    protected void keyReleased(int keyCode) {
        int gameAction = this.getGameAction(keyCode);
        switch (gameAction) {
            case RIGHT:
                currentCol++;
                if ((currentCol + 1) > data[0].length) {
                    currentCol = 0;
                    clipX = 0;
                }
                if (((currentCol * xPlus) + xPlus) > getWidth()) {
                    clipX += xPlus;
                }
                repaint();
                break;
            case LEFT:
                currentCol--;
                if (currentCol <= 0) {
                    currentCol = 0;
                    clipX = 0;
                }

                if (((currentCol * xPlus) + xPlus) > getWidth()) {
                    clipX -= xPlus;
                }
                repaint();
                break;
            case UP:
                currentRow--;
                if (currentRow <= 0) {
                    currentRow = 1;
                    clipY = 0;
                }

                if (((currentRow * yPlus) + yPlus) > getHeight()) {
                    clipY -= yPlus;
                }
                repaint();
                break;
            case DOWN:
                currentRow++;
                if (currentRow > data.length) {
                    currentRow = 1;
                    clipY = 0;
                }

                if (((currentRow * yPlus) + yPlus) > getHeight()) {
                    clipY += yPlus;
                }
                repaint();
                break;
            default:
                break;
        }
    }

    protected void paint(Graphics g) {
        bgColor.set(g);
        g.fillRect(0, 0, getWidth() + clipX, getHeight() + clipY);

        //highlight current cell
        Color highlightColor = new Color(50, 50, 150);
        highlightColor.set(g);
        if (currentRow == 0) {
            setTitle(data[currentRow][currentCol]);
        } else {
            setTitle(data[currentRow - 1][currentCol]);
        }
        int xx = currentCol * xPlus - clipX;
        int yy = currentRow * yPlus - clipY;

        int ww = xPlus;
        int hh = yPlus;
        g.fillRect(xx, yy, ww, hh);

        //draw grid lines (numRow +1, to take care of the header;
        for (int i = 0; i <= numRow + 1; i++) {
            lineColor.set(g);
            int y = i * yPlus;
            int x1 = 0;
            int x2 = numCol * xPlus;

            //draw a thick line
            for (int k = 0; k < 2; k++) {
                g.drawLine(x1 + k - clipX, y + k - clipY, x2 + k - clipX, y + k - clipY);
            }
        }

        for (int j = 0; j <= numCol; j++) {
            lineColor.set(g);
            int y1 = 0;
            int y2 = (numRow + 1) * yPlus;
            int x = j * xPlus;

            for (int k = 0; k < 2; k++) {
                g.drawLine(x + k - clipX, y1 + k - clipY, x + k - clipX, y2 + k - clipY);
            }
        }

        //draw header
        for (int i = 0; i < numCol; i++) {
            headerBGColor.set(g);
            g.fillRect(i * xPlus - clipX, 0 - clipY, xPlus, yPlus);
            fontColor.set(g);
            g.drawString(header[i], (i * xPlus) + 3 - clipX, 0 - clipY, Graphics.LEFT | Graphics.TOP);
        }

        //draw data
        for (int i = 0; i < numRow; i++) {
            for (int j = 0; j < numCol; j++) {
                fontColor.set(g);
                int y = (i + 1) * yPlus + 2 - clipY;
                int x = j * xPlus + 2 - clipX;

                //truncate data if longer than cell
                String s = data[i][j];
                char[] chars = s.toCharArray();
                int stringWidth = Font.getDefaultFont().charsWidth(chars, 0, chars.length);

                while(stringWidth > xPlus) {                    
                    s = s.substring(0, s.length() - 1);
                    chars = s.toCharArray();
                    stringWidth = Font.getDefaultFont().charsWidth(chars, 0, chars.length);
                }
                
                g.drawString(s, x, y, Graphics.LEFT | Graphics.TOP);
            }
        }
    }

    private class CellEditor extends TextBox {

        public CellEditor() {
            super("Cell Editor", data[currentRow - 1][currentCol], 255, TextField.ANY);
            this.addCommand(new Command("Update", Command.OK, 0));
            this.addCommand(new Command("Cancel", Command.BACK, 1));
            this.setCommandListener(new Commander());
        }
    }

    private class Commander implements CommandListener {

        public void commandAction(Command c, Displayable d) {
            if (c.getLabel().equals("Edit Cell")) {
                cellEditor.setString(data[currentRow - 1][currentCol]);
                display.setCurrent(cellEditor);
            }

            //Cell Editor
            if (d.equals(cellEditor) && c.getLabel().equals("Update")) {
                data[currentRow - 1][currentCol] = cellEditor.getString();
                canvasTable.repaint();
                display.setCurrent(canvasTable);
            }
            if (d.equals(cellEditor) && c.getLabel().equals("Cancel")) {
                display.setCurrent(canvasTable);
            }
        }
    }

    public Color getBgColor() {
        return bgColor;
    }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

    public String[][] getData() {
        return data;
    }

    public void setData(String[][] data) {
        this.data = data;
    }

    public int getNumCol() {
        return numCol;
    }

    public void setNumCol(int numCol) {
        this.numCol = numCol;
    }

    public int getNumRow() {
        return numRow;
    }

    public void setNumRow(int numRow) {
        this.numRow = numRow;
    }

    public Color getFontColor() {
        return fontColor;
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    public String[] getHeader() {
        return header;
    }

    public void setHeader(String[] header) {
        this.header = header;
    }

    public Color getHeaderBGColor() {
        return headerBGColor;
    }

    public void setHeaderBGColor(Color headerBGColor) {
        this.headerBGColor = headerBGColor;
        this.lineColor = headerBGColor;
    }

    public Display getDisplay() {
        return display;
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    public MIDlet getParent() {
        return parent;
    }

    public void setParent(MIDlet parent) {
        this.parent = parent;
    }
}
