package com.reissmann.earthquake;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by sebas on 21.05.2017.
 */

public class Utils {

    public Utils() {}

    public Date getFormattedDate(Long time){
        Date date = new Date(time);
        SimpleDateFormat df2 = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        df2.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
        String dateText = df2.format(date);

        return date;
    }

    public String getFormattedMagnitude(Double mag) {
        DecimalFormat df2 = new DecimalFormat("0.00");

        return df2.format(mag);
    }
}
