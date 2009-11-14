/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.trinisoft.mlib.ui;

import com.trinisoft.mlib.util.Controller;
import com.trinisoft.mlib.util.Properties;
import java.util.Date;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.DateField;
import javax.microedition.lcdui.Gauge;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;

/**
 *
 * @author trinisoftinc
 */
public class Form extends javax.microedition.lcdui.Form {

    Controller controller;

    public Form(String title, Controller controller) {
        super(title);
        this.controller = controller;        
    }

    public void submit() {
        Properties properties = new Properties();
        int itemCount = this.size();
        for(int i = 0; i < itemCount; i++) {
            Item item = this.get(i);
            String key = item.getClass().getName();

            if(item instanceof ChoiceGroup) {
                ChoiceGroup choiceGroup = (ChoiceGroup) item;
                String value = choiceGroup.getString(choiceGroup.getSelectedIndex());
                properties.setParameter(key, value);
            }

            if(item instanceof DateField) {
                DateField dateField = (DateField) item;
                Date value = dateField.getDate();
                properties.setParameter(key, value);
            }

            if(item instanceof StringItem) {
                StringItem stringItem = (StringItem) item;
                String value = stringItem.getText();
                properties.setParameter(key, value);
            }

            if(item instanceof TextField) {
                TextField textField = (TextField) item;
                String value = textField.getString();
                properties.setParameter(key, value);
            }

            if(item instanceof Gauge) {
                Gauge gauge = (Gauge) item;
                Integer value = new Integer(gauge.getValue());
                properties.setParameter(key, value);
            }
        }

        controller.service(properties);
    }
}
