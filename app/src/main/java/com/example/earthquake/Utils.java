package com.example.earthquake;

import android.icu.text.SimpleDateFormat;

import java.util.Date;

/**
 * Created by sebas on 21.05.2017.
 */

public class Utils {

    public Utils() {}

    public String getFormattedDate(Long time){
        Date date = new Date(time);
        SimpleDateFormat df2 = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        String dateText = df2.format(date);

        return dateText;
    }
}
