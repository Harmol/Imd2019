package com.example.huaronggame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.text.style.BackgroundColorSpan;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends FrameLayout {
    private TextView label;
    public String name;
    private int rowspan = 1, columnspan = 1;

    public Card(Context context, String name) {
        super(context);

        label = new TextView(getContext());
        this.name = name;
        //label.setText(name+"");
        //label.setTextSize(24);
        //label.setGravity(Gravity.CENTER);
        //setBackgroundColor();

        setBackgroundImg();

        LayoutParams lp = new LayoutParams(-1, -1);
        lp.setMargins(10, 10, 0, 0);
        addView(label, lp);

    }

    public Card(Context context, String name, int rowspan, int columnspan) {
        super(context);

        label = new TextView(getContext());
        this.name = name;
//        label.setText(name+"");
////        label.setTextSize(24);
////        label.setTextColor(Color.WHITE);
////        label.setGravity(Gravity.CENTER);
////        setBackgroundColor();
        setBackgroundImg();

        setRowspan(rowspan);
        setColumnspan(columnspan);

        LayoutParams lp = new LayoutParams(-1, -1);
        lp.setMargins(10, 10, 0, 0);
        addView(label, lp);

    }

    public int getRowspan() {
        return rowspan;
    }

    public void setRowspan(int rowspan) {
        this.rowspan = rowspan;
        //setBackgroundColor();
        setBackgroundImg();
    }

    public int getColumnspan() {
        return columnspan;
    }

    public void setColumnspan(int columnspan) {
        this.columnspan = columnspan;
        //setBackgroundColor();
        setBackgroundImg();
    }

    public void setBackgroundColor(){
        label.setTextColor(Color.WHITE);
        if(rowspan == 2 && columnspan == 2)
            label.setBackgroundColor(Color.GREEN);
        else if(rowspan == 1 && columnspan == 2)
            label.setBackgroundColor(Color.RED);
        else if(rowspan == 2 && columnspan == 1)
            label.setBackgroundColor(Color.BLUE);
        else{
            label.setBackgroundColor(Color.WHITE);
            label.setTextColor(Color.BLACK);
        }
    }

    public void setBackgroundImg(){
        Resources resources=getResources();
        Drawable drawable=resources.getDrawable(R.drawable.animal_1);
        if(rowspan == 2 && columnspan == 2)
            drawable = resources.getDrawable(R.drawable.caocao);
        else if(rowspan == 1 && columnspan == 2)
            drawable = resources.getDrawable(R.drawable.animal_4);
        else if(rowspan == 2 && columnspan == 1)
            drawable = resources.getDrawable(R.drawable.animal_3);

        label.setBackgroundDrawable(drawable);
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        super.setOnTouchListener(l);
    }
}
