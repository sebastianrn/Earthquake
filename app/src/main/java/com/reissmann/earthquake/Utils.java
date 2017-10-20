package com.reissmann.earthquake;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {

    public Utils() {}

    public Date getFormattedDate(Long time){
        Date date = new Date(time);
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.GERMANY);
        df.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
        df.format(date);
        return date;
    }

    public String getFormattedMagnitude(Double mag) {
        DecimalFormat df2 = new DecimalFormat("0.00");
        return df2.format(mag);
    }

    public String getFormattedDepth(Double depth) {
        DecimalFormat df2 = new DecimalFormat("0.00");
        return df2.format(depth) + " km";
    }
}
