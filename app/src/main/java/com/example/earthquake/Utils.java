package com.example.earthquake;

import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;

import java.util.Date;

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
}
