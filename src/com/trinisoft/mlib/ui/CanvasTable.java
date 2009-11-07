/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.mlib.ui;

import com.trinisoft.mlib.Color;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

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
    
    public CanvasTable(String header[], int numRow, int numCol) throws Exception {
        this.numCol = numCol;
        this.numRow = numRow;
        this.header = header;
        if(header.length != numCol) {
            throw new Exception("Header length mismatch");
        }

        data = new String[numRow][numCol];
    }

    public CanvasTable(String header[], String[][] data) throws Exception {
        this.numRow = data.length;
        this.numCol = data[0].length;
        this.header = header;
        System.out.println(header.length + " : " + numRow);
        if(header.length != numCol) {
            throw new Exception("Header length mismatch");
        }
        
        this.data = data;
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
        switch(gameAction) {
            case RIGHT:
                currentCol++;
                if((currentCol + 1) > data[0].length) {
                    currentCol = 0;
                    clipX = 0;
                }
                if(((currentCol * xPlus) + xPlus) > getWidth()) {
                    clipX += 50;
                }
                repaint();
                break;
            case LEFT:
                currentCol--;
                if(currentCol <= 0) {
                    currentCol = 0;
                    clipX = 0;
                }

                if(((currentCol * xPlus) + xPlus) > getWidth()) {
                    clipX -= 50;
                }
                repaint();
                break;
            case DOWN:
                currentRow++;
                if((currentRow + 1) > data.length) {
                    currentRow = 0;
                    clipY = 0;
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
            for(int k = 0; k < 2; k++) {
                g.drawLine(x1 + k - clipX, y + k - clipY, x2 + k - clipX, y + k - clipY);
            }
        }

        for(int j = 0; j <= numCol; j++) {
            lineColor.set(g);
            int y1 = 0;
            int y2 = (numRow + 1) * yPlus;
            int x = j * xPlus;

            for(int k = 0; k < 2; k++) {
                g.drawLine(x + k - clipX, y1 + k - clipY, x + k - clipX, y2 + k - clipY);
            }
        }

        //draw header
        for(int i = 0; i < numCol; i++) {
            headerBGColor.set(g);
            g.fillRect(i * xPlus - clipX, 0 - clipY, xPlus, yPlus);
            fontColor.set(g);
            g.drawString(header[i], (i * xPlus) + 3 - clipX, 0 - clipY, Graphics.LEFT|Graphics.TOP);
        }
        
        //draw data
        for(int i = 0; i < numRow; i++) {
            for(int j = 0; j < numCol; j++) {
                fontColor.set(g);
                int y = (i + 1) * yPlus + 2 - clipY;
                int x = j * xPlus + 2 - clipX;
                g.drawString(data[i][j], x, y, Graphics.LEFT|Graphics.TOP);
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
}
