package com.reissmann.earthquake;

import android.view.View;

/**
 * Created by sebas on 26.05.2017.
 */

public interface ClickListener{
    public void onClick(View view, int position);
    public void onLongClick(View view,int position);
}
