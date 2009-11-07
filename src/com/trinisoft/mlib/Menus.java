/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.mlib;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author root
 */
public class Menus {

    public MenuCanvas returningCanvas;
    Font largeFont = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_LARGE);
    Font defaultFont = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_LARGE);
    Display display;

    /**
     *
     * @param menus
     * @param screens
     * @param initialColor
     * @param rollOverColor
     * @param display 
     * @return
     */
    public Canvas verticalMenus(String[] menus, Canvas[] screens, Color initialColor, Color rollOverColor, Display display) {
        returningCanvas = new MenuCanvas(menus, screens, initialColor, rollOverColor, Constants.VERTICAL);
        this.display = display;
        return returningCanvas;
    }

    class MenuCanvas extends Canvas implements CommandListener {

        Color initialColor, rollOverColor;
        Command canvasCommand = new Command("", Command.OK, 0);
        String menus[];
        Canvas screens[];
        boolean start = true;
        int x, y, index = 0;
        int verticalScrollSize, horizontalScrollSize;
        int[] yIndexPos, xIndexPos;
        int orientation;

        public void commandAction(Command c, Displayable d) {
            display.setCurrent(screens[index]);
        }

        public MenuCanvas(String[] menus, Canvas[] screens, Color initialColor, Color rollOverColor, int menuOrientation) {
            this.initialColor = initialColor;
            this.rollOverColor = rollOverColor;
            this.menus = menus;
            this.screens = screens;
            this.orientation = menuOrientation;
            this.setCommandListener(this);
        }

        public void paint(Graphics g) {
            Color bgColor = new Color(255, 255, 255);
            MUtils.clearScreen(g, bgColor, this.getWidth(), this.getHeight());

            Color fgColor = new Color(0, 0, 0);
            fgColor.set(g);
            g.setFont(largeFont);
            drawMenus(g);
        }

        public void drawMenus(Graphics g) {
            int oneMenuHeight = largeFont.getHeight();
            int allMenusHeight = oneMenuHeight * menus.length;

            yIndexPos = new int[menus.length];
            xIndexPos = new int[menus.length];

            int yPos = 0;
            int xPos = 0;

            if (orientation == Constants.VERTICAL) {
                if (this.getHeight() > allMenusHeight) {
                    yPos = (this.getHeight() - allMenusHeight) / 2;
                }
                y = yPos;

                for (int i = 0; i < this.menus.length; i++) {
                    g.drawString(menus[i], xPos, y + largeFont.getHeight(), Graphics.BOTTOM | Graphics.LEFT);
                    yIndexPos[i] = y;
                    y += largeFont.getHeight();
                }
            } else {
            }

            highlight(g, index);
        }

        public void highlight(Graphics g, int index) {
            try {
                this.removeCommand(canvasCommand);
            } catch (Exception e) {



                rollOverColor.set(g);
                if (orientation == Constants.VERTICAL) {
                    g.fillRoundRect(xIndexPos[index], yIndexPos[index], this.getWidth(), largeFont.getHeight(), 5, 5);
                }

                canvasCommand = new Command(menus[index], Command.OK, 0);
                this.addCommand(canvasCommand);
                initialColor.set(g);
                g.drawString(menus[index], 0, yIndexPos[index] + largeFont.getHeight(), Graphics.BOTTOM | Graphics.LEFT);
                this.index = index;
            }
        }

        public void keyPressed(int keyCode) {
            int gameAction = getGameAction(keyCode);
            switch (gameAction) {
                case UP:
                    System.out.println("UP");
                    move(Constants.DIR_UP);
                    break;
                case DOWN:
                    System.out.println("DPWN");
                    move(Constants.DIR_DOWN);
                    break;
                case FIRE:
                    display.setCurrent(screens[index]);
                //System.out.println("Fired " + menus[index]);
            }
        }

        public void move(int dir) {
            switch (dir) {
                case Constants.DIR_UP:
                    if (index == 0) {
                        index = menus.length - 1;
                        repaint();
                    } else {
                        index--;
                        repaint();
                    }
                    break;
                case Constants.DIR_DOWN:
                    if (index == (menus.length - 1)) {
                        index = 0;
                        repaint();
                    } else {
                        index++;
                        repaint();
                    }
                    break;
            }
        }
    }
}
