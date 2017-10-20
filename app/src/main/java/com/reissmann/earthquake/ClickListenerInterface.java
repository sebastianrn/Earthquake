package com.reissmann.earthquake;

import android.view.View;

public interface ClickListenerInterface {
    public void onClick(View view, int position);
    public void onLongClick(View view, int position);
}
