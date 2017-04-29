package com.ajou.jinwoo.drawcanvasexample;

import android.graphics.Paint;
import android.graphics.Path;


public class CustomPath extends Path {

    private Paint mPaint;

    public void setPaint(Paint mPaint) {
        this.mPaint = new Paint(mPaint);
    }

    public Paint getPaint() {
        return mPaint;
    }

    public CustomPath() {
        super();
    }
}
