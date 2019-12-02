package com.itzzy.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class dateutil {
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH-mm-ss";

    public static String date2string(Date datepram, String patten) {
        if (datepram == null) {
            return "";
        }
        SimpleDateFormat sim = new SimpleDateFormat(patten);
        String format = sim.format(datepram);
        return format;
    }

    public static Date string2date(String strpram, String patten) {
        if (strpram == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(patten);
        Date date = null;
        try {
            date = format.parse(strpram);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
