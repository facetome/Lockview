package com.basic.lockview.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by acer on 2015/8/18.
 */
public class Line {
    private float mStartX;
    private float mStartY;
    private float mEndX;
    private float mEndY;

    public Line(float startX, float startY, float endX, float endY) {
        mStartX = startX;
        mStartY = startY;
        mEndX = endX;
        mEndY = endY;
    }

    public void drawToCanvas(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setAntiAlias(true);
        paint.setAlpha(80);
        paint.setStrokeWidth(30);
        canvas.drawLine(mStartX, mStartY, mEndX, mEndY, paint);
    }
}
