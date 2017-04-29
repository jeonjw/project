package com.ajou.jinwoo.drawcanvasexample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CanvasView extends View {

    Context context;
    private Paint mPaint;
    private float mX, mY;
    private static final float TOLERANCE = 5;
    private List<CustomPath> pathList;
    private CustomPath path;

    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(4f);
        pathList = new ArrayList<>();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (path != null)
            canvas.drawPath(path, mPaint); // 현재 그려지고 있는 path를 실시간으로 그려지는걸 보여주기 위해 구현

        for (CustomPath path : pathList)
            canvas.drawPath(path, path.getPaint());
    }

    private void startTouch(float x, float y) {
        path = new CustomPath();
        path.setPaint(mPaint);
        mX = x;
        mY = y;
        path.moveTo(mX, mY);
    }

    private void moveTouch(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            mX = x;
            mY = y;
            path.lineTo(mX, mY);
        }

    }

    public void clearCanvas() {
        pathList.clear();
        Toast.makeText(context, "Cleared", Toast.LENGTH_SHORT).show();
        invalidate();
    }

    public void SetPenColor(int color) {
        switch (color) {
            case 0:
                mPaint.setColor(Color.BLACK);
                break;
            case 1:
                mPaint.setColor(Color.parseColor("#FF69B4"));
                break;
            case 2:
                mPaint.setColor(Color.GREEN);
                break;
        }
        invalidate();
    }

    private void upTouch() {
        pathList.add(path);
        path = null;
    }

    public void undoPath() {
        if (pathList.size() != 0)
            pathList.remove(pathList.size() - 1);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTouch(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x, y);
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                break;
        }
        invalidate();
        return true;
    }
}
