/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.trinisoft.mlib.util;

import java.util.Calendar;

/**
 *
 * @author trinisoftinc
 */
public class Date extends java.util.Date {

    public Date(long date) {
        super(date);
    }

    public Date() {
    }

    public boolean isSameDateAs(java.util.Date date) {
        if(this.getTime() == date.getTime()) {
            return true;
        }
        return false;
    }
    
    public boolean isAfter(java.util.Date date) {
        if(this.getTime() > date.getTime()) {
            return true;
        }
        return false;
    }

    public boolean isBefore(java.util.Date date) {
        if(this.getTime() < date.getTime()) {
            return true;
        }
        return false;
    }

    public Date afterSeconds(int secs) {
        Date newDate = this;
        newDate.setTime(this.getTime() + (secs * 1000));
        return newDate;
    }

    public Date afterMinutes(int mins) {
        Date newDate = this;
        newDate.setTime(this.getTime() + (mins * 60 * 1000));
        return newDate;
    }

    public Date afterHours(int hours) {
        Date newDate = this;
        newDate.setTime(this.getTime() + (hours * 60 * 60 * 1000));
        return newDate;
    }

    public Date afterDays(int days) {
        Date newDate = this;
        newDate.setTime(this.getTime() + (days * 24 * 60 * 60 * 1000));
        return newDate;
    }

    public Date afterWeeks(int weeks) {
        Date newDate = this;
        newDate.setTime(this.getTime() + (weeks * 7 * 24 * 60 * 60 * 1000));
        return newDate;
    }

    public String toString() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        int hour = calendar.get(Calendar.HOUR);
        int min = calendar.get(Calendar.MINUTE);
        int am_pm = calendar.get(Calendar.AM_PM);
        String am_pm_s = (am_pm == Calendar.AM) ? "AM" : "PM";

        return "" + day + "/" + month + "/" + "/" + year + "  " + hour + ":" + min + " " + am_pm_s;
    }
}
