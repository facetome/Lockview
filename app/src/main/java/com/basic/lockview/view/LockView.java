package com.basic.lockview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.basic.lockview.utils.Constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 九宫格解锁view.
 */
public class LockView extends View {
    private static final String TAG = "LockView";
    private List<CircleView> mCircleList = new ArrayList<>();
    private static final int CIRCLE_COUNT = 9;
    private static final int CIRCLE_RADIUS_DENOMINATOR = 10;
    private static final float CIRCLE_POSITION_DENOMINATOR = 6F;
    private boolean mIsReady = false;
    private CircleView[] mCircleGroup = new CircleView[CIRCLE_COUNT];
    private MotionEvent mEvent;
    private CompleteListener mListener;

    public LockView(Context context) {
        this(context, null);
    }

    public LockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!mIsReady) {
            initPosition();
        }
        drawToCanvas(canvas);
    }

    private void drawToCanvas(Canvas canvas) {
        mIsReady = true;
        //先绘制背景
        RectF rectF = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.GRAY);
        canvas.drawRect(rectF, paint);

        for (int i = 0; i < mCircleGroup.length; i++) {
            if (mCircleGroup[i] != null) {
                mCircleGroup[i].drawToCanvas(canvas);
            }
        }
        //未选中状态下绘制点和手指间的连线
        int listSize = mCircleList.size();
        if (listSize > 0) {
            CircleView lastCircle = mCircleList.get(listSize - 1);
            if (lastCircle != null && lastCircle.getCheckState() == CircleView.STATE_CHECKOUT) {
                CircleView circleView = new CircleView(mEvent.getX(), mEvent.getY(), 0);
                circleView.setCheckState(CircleView.STATE_CHECKOUT);
                drawLine(canvas, mCircleList.get(listSize - 1), circleView);
            }
        }

        //这是绘制已经选中的点的连线
        if (mCircleList.size() > 1) {
            for (int i = 1; i < mCircleList.size(); i++) {
                drawLine(canvas, mCircleList.get(i - 1), mCircleList.get(i));
            }
        }
    }

    /**
     * 初始化他们的位置信息
     */
    private void initPosition() {
        //进行位置的计算
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int temp = width >= height ? height : width;
        int radius = temp / CIRCLE_RADIUS_DENOMINATOR; //每个圆圈的半径是1/10
        int offest = (height - temp) / 2;
        float originX = 0;
        float originY = 0;
        for (int i = 0; i < mCircleGroup.length; i++) {
            int horizontal = i % 3;
            switch (horizontal) {
                case 0:
                    originX = 1 / CIRCLE_POSITION_DENOMINATOR * width;
                    break;
                case 1:
                    originX = 1 / 2f * width;
                    break;
                case 2:
                    originX = 5 / CIRCLE_POSITION_DENOMINATOR * width;
                    break;
                default:
                    break;
            }

            int vertical = i / 3;
            switch (vertical) {
                case 0:
                    originY = 1 / CIRCLE_POSITION_DENOMINATOR * temp + offest;
                    break;
                case 1:
                    originY = 1 / 2f * temp + offest;
                    break;
                case 2:
                    originY = 5 / CIRCLE_POSITION_DENOMINATOR * temp + offest;
                    break;
                default:
                    break;

            }
            mCircleGroup[i] = new CircleView(originX, originY, radius);
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mEvent = event;
        int action = event.getActionMasked();
        float pointX = 0;
        float pointY = 0;
        boolean isPointUp = true;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isPointUp = false;
                pointX = event.getX();
                pointY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                isPointUp = false;
                pointX = event.getX();
                pointY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                isPointUp = true;
                break;
            default:
                break;
        }
        for (int i = 0; i < mCircleGroup.length; i++) {
            mCircleGroup[i].checkSelectPoint(pointX, pointY);
            if (!mCircleList.contains(mCircleGroup[i]) && mCircleGroup[i].isCheckingNow()) {
                mCircleList.add(mCircleGroup[i]);
                mCircleGroup[i].setId(i);
            }
            if (isPointUp) {
                mCircleGroup[i].setCheckState(CircleView.STATE_UNCHECKOUT);
            }
        }
        if (isPointUp) {
            if (mCircleList.size() != 0) {
                mListener.onComplete(genPassword());
            }
            mCircleList.clear();
        }
        invalidate();
        return true;
    }

    /**
     * 两点间划线.
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas, CircleView first, CircleView second) {
        if (first.getCheckState() == CircleView.STATE_CHECKOUT && second.getCheckState() == CircleView.STATE_CHECKOUT) {
            Line line = new Line(first.getOriginX(), first.getOriginY(), second.getOriginX(),
                    second.getOriginY());
            line.drawToCanvas(canvas);
        }
    }

    private String genPassword() {
        //按照数组的id生成密码.
        Iterator<CircleView> iterator = mCircleList.iterator();
        StringBuffer buffer = new StringBuffer();
        while (iterator.hasNext()) {
            CircleView circle = iterator.next();
            int id = circle.getId();
            String key = Constants.getType(id);
            buffer.append(key);
        }
        return buffer.toString();
    }

    public void setCompleteListener(CompleteListener listener) {
        mListener = listener;
    }

    public interface CompleteListener {
        void onComplete(String password);
    }
}
