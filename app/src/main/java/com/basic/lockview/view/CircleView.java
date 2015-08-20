package com.basic.lockview.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 *
 */
public class CircleView {
    private int mCheckState = STATE_UNCHECKOUT;
    public static final int STATE_CHECKOUT = 1;
    public static final int STATE_UNCHECKOUT = 2;
    private boolean mIsCheckNow = false;
    private int mId;
    //原点位置
    private float mOriginX;
    private float mOriginY;
    //绘制的半径
    private float mRadius;

    public CircleView(float originX, float originY, int radius) {
        mOriginX = originX;
        mOriginY = originY;
        mRadius = radius;
    }

    public float getOriginX() {
        return mOriginX;
    }

    public float getOriginY() {
        return mOriginY;
    }

    public void drawToCanvas(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAlpha(80);
        paint.setAntiAlias(true);
        if (mIsCheckNow || mCheckState == STATE_CHECKOUT) { //在这两种情况下都是选中了的
            paint.setColor(Color.RED);
            canvas.drawCircle(mOriginX, mOriginY, mRadius, paint);
            paint.setColor(Color.GREEN);
            canvas.drawCircle(mOriginX, mOriginY, 30, paint);
        } else {
            paint.setColor(Color.BLUE);
            canvas.drawCircle(mOriginX, mOriginY, mRadius, paint);
            paint.setColor(Color.RED);
            canvas.drawCircle(mOriginX, mOriginY, 30, paint);
        }
    }

    /**
     * 判断点是否已经被选中过.
     *
     * @param state
     */
    public void setCheckState(int state) {
        mCheckState = state;
    }

    public int getCheckState() {
        return mCheckState;
    }

    /**
     * 判断是否是当前被选中的点.
     */
    public void checkSelectPoint(float x, float y) {
        if (Math.abs(x - mOriginX) < mRadius && Math.abs(y - mOriginY) < mRadius) {
            mIsCheckNow = true;
            mCheckState = STATE_CHECKOUT;
        } else {
            mIsCheckNow = false;

        }
    }

    public boolean isCheckingNow() {
        return mIsCheckNow;
    }

    /**
     * 保存id的目的是为了通过i下标索引来获取对应的key，来生成密码
     * @param id
     */
    public void setId(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }


}
