/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.trinisoft.baselib.util;

import java.util.Hashtable;

/**
 *
 * @author trinisoftinc
 */
public class Properties extends Hashtable {
    public void setParameter(Object key, Object value) {
        this.put(key, value);
    }

    public Object getParameter(Object key) {
        return this.get(key);
    }
}
