package com.reissmann.earthquake;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by sebas on 26.05.2017.
 */

public class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener{

    private ClickListenerInterface clicklistener;
    private GestureDetector gestureDetector;

    public RecyclerViewTouchListener(Context context, final RecyclerView recycleView, final ClickListenerInterface clickListenerInterface){

        this.clicklistener = clickListenerInterface;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recycleView.findChildViewUnder(e.getX(), e.getY());
                if(child != null && clickListenerInterface != null){
                    clickListenerInterface.onLongClick(child, recycleView.getChildAdapterPosition(child));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if(child != null && clicklistener != null && gestureDetector.onTouchEvent(e)){
            clicklistener.onClick(child, rv.getChildAdapterPosition(child));
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
