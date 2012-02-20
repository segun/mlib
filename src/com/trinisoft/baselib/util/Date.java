/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.baselib.util;

import java.util.Calendar;
import java.util.Vector;

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

    public void setTimePart(int h, int m, int s) {
        Calendar c = Calendar.getInstance();
        c.setTime(this);
        c.set(Calendar.HOUR_OF_DAY, h);
        c.set(Calendar.MINUTE, m);
        c.set(Calendar.SECOND, s);
        this.setTime(c.getTime().getTime());
    }

    public void setDatePart(int y, int m, int d) {
        Calendar c = Calendar.getInstance();
        c.setTime(this);
        c.set(Calendar.YEAR, y);
        c.set(Calendar.MONTH, m);
        c.set(Calendar.DAY_OF_MONTH, d);
        this.setTime(c.getTime().getTime());
    }

    public boolean isSameDateAs(Date date) {
        if (this.getTime() == date.getTime()) {
            return true;
        }
        return false;
    }

    public boolean isAfter(Date date) {
        if (this.getTime() > date.getTime()) {
            return true;
        }
        return false;
    }

    public boolean isBefore(Date date) {
        if (this.getTime() < date.getTime()) {
            return true;
        }
        return false;
    }    

    public boolean isSameDayAs(Date date) {
        if (this.getYear() == date.getYear()) {
            if (this.getMonth() == date.getMonth()) {
                if (this.getDay() == date.getDay()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isSameMonthAs(Date date) {
        if (this.getYear() == date.getYear()) {
            if (this.getMonth() == date.getMonth()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isBetween(Date d1, Date d2, boolean inclusive) {
        if(inclusive) {
            if((this.isSameDateAs(d1) || this.isAfter(d1)) && (this.isSameDateAs(d2) || this.isBefore(d2))) {
                return true;
            }            
        } else {
            if(this.isAfter(d1) && this.isBefore(d2)) {
                return true;
            }                        
        }
        return false;
    }

    public boolean isSameYearAs(Date date) {
        if (this.getYear() == date.getYear()) {
            return true;
        }
        return false;
    }

    public int compare(Date date) {
        if (isSameDateAs(date)) {
            return 0;
        } else if (isBefore(date)) {
            return -1;
        } else {
            return 1;
        }
    }

    public void afterSeconds(int secs) {
        this.setTime(this.getTime() + (secs * 1000));
    }

    public void afterMinutes(int mins) {
        this.setTime(this.getTime() + (mins * 60 * 1000));
    }

    public void afterHours(int hours) {
        this.setTime(this.getTime() + (hours * 60 * 60 * 1000));
    }

    public void afterDays(int days) {
        this.setTime(this.getTime() + (days * 24 * 60 * 60 * 1000));
    }

    public void afterWeeks(int weeks) {
        this.setTime(this.getTime() + (weeks * 7 * 24 * 60 * 60 * 1000));
    }

    public String toString() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String dd = day < 10 ? "0" + day : "" + day;
        int month = calendar.get(Calendar.MONTH) + 1;
        String mm = month < 10 ? "0" + month : "" + month;
        int year = calendar.get(Calendar.YEAR);
        String yy = year < 10 ? "0" + year : "" + year;

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String h = hour < 10 ? "0" + hour : "" + hour;
        int min = calendar.get(Calendar.MINUTE);
        String m = min < 10 ? "0" + min : "" + min;
        int am_pm = calendar.get(Calendar.AM_PM);
        String am_pm_s = (am_pm == Calendar.AM) ? "AM" : "PM";

        return "" + dd + "/" + mm + "/" + yy + " " + h + ":" + m + "  " + am_pm_s;
    }

    public void fromString(String s) {
        Vector dateTimePart = MStrings.splitString(s, " ");
        Vector datePart = MStrings.splitString(dateTimePart.elementAt(0).toString(), "/");
        if(datePart.elementAt(0).toString().length() > 2) {
            String temp = datePart.elementAt(0).toString();
            datePart.setElementAt(datePart.elementAt(2), 0);
            datePart.setElementAt(temp, 2);            
        }
        Vector timePart = MStrings.splitString(dateTimePart.elementAt(1).toString(), ":");
        this.setDatePart(Integer.parseInt(datePart.elementAt(2).toString()),
                Integer.parseInt(datePart.elementAt(1).toString()) - 1,
                Integer.parseInt(datePart.elementAt(0).toString()));
        this.setTimePart(Integer.parseInt(timePart.elementAt(0).toString()),
                Integer.parseInt(timePart.elementAt(1).toString()), 0);
    }

    public int getDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this);
        return calendar.get(Calendar.MONTH);
    }

    public int getYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this);
        return calendar.get(Calendar.YEAR);
    }

    public int getHour() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this);
        return calendar.get(Calendar.HOUR);
    }

    public int getMinute() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this);
        return calendar.get(Calendar.MINUTE);
    }

    public int getSeconds() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this);
        return calendar.get(Calendar.SECOND);
    }
}
