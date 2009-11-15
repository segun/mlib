/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.mlib.ui;

import com.trinisoft.mlib.util.Controller;
import java.util.TimerTask;
import javax.microedition.lcdui.Ticker;
import javax.microedition.rms.RecordStore;

/**
 *
 * @author segun
 */
public class TickerForm extends Form {

    public TickerForm(String title, String initialText, String tickerURL, Controller controller) {
        super(title, controller);

        this.setTicker(new Ticker(initialText));
    }

    public TickerForm(String title, String initialText, RecordStore store, Controller controller) {
        super(title, controller);

        this.setTicker(new Ticker(initialText));
    }

    public TickerForm(String title, String initialText, String tickerURL) {
        super(title, null);

        this.setTicker(new Ticker(initialText));
    }

    public TickerForm(String title, String initialText, RecordStore store) {
        super(title, null);

        this.setTicker(new Ticker(initialText));
    }
}

class TickerPull extends TimerTask {

    public TickerPull(String url) {
    }

    public TickerPull(RecordStore recordStore) {
    }

    public void run() {
    }
}
