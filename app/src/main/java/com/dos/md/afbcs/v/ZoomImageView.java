package com.dos.md.afbcs.v;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.ImageView;


/**
 * Created by DOS on 002/2/4/2016.
 */
public class ZoomImageView extends ImageView implements ViewTreeObserver.OnGlobalLayoutListener, ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener {

    private boolean mOnce = false;
    private float mInitScale;
    private float mMidScale;// TODO: 003/3/4/2016
    private float mmaxScale;
    private ScaleGestureDetector mScaleGestureDetector;
    private Matrix mScaleMatrix;


    private int mLastPointerCount;
    private float mLastX, mLastY;
    private int mTouchSloop;
    private boolean isCanDrag;
    private boolean isCheckLeftAndRight, isCheckTopAndBottom;


    private GestureDetector mGestureDetector;

    public ZoomImageView(Context context) {
        this(context, null);
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScaleMatrix = new Matrix();
        setScaleType(ScaleType.MATRIX);
        setOnTouchListener(this);
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        mTouchSloop = ViewConfiguration.get(context).getScaledTouchSlop();
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (isAutoScale) return true;
                float x = e.getX();
                float y = e.getY();
                if (getScale() < mMidScale) {

                   /* mScaleMatrix.postScale(mMidScale / getScale(), mMidScale / getScale(), x, y);
                    setImageMatrix(mScaleMatrix);// TODO: 024/24/4/2016 设置矩阵*/
                    postDelayed(new AutoRunnable(mMidScale, x, y), 16);
                    isAutoScale = true;
                } else {
                    /*mScaleMatrix.postScale(mInitScale / getScale(), mInitScale / getScale(), x, y);
                    setImageMatrix(mScaleMatrix);*/
                    postDelayed(new AutoRunnable(mInitScale, x, y), 16);
                    isAutoScale = true;

                }

                return true;
            }
        });
    }

    private boolean isAutoScale;//避免狂击

    /**
     * 自动变换（scale）
     */
    private class AutoRunnable implements Runnable {
        private float mTargetScale, x, y, tmpScale;//目标缩放值与缩放中心
        private final float BIGGER = 1.07f, SMALLER = 0.93F;//缩放梯度

        public AutoRunnable(float targetScale, float x, float y) {
            this.mTargetScale = targetScale;
            this.x = x;
            this.y = y;
            if (getScale() < mTargetScale) {
                tmpScale = BIGGER;

            }
            if (getScale() > mTargetScale) tmpScale = SMALLER;
        }

        @Override
        public void run() {
            mScaleMatrix.postScale(tmpScale, tmpScale, x, y);
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);
            float currentScale = getScale();
            if ((tmpScale < 1.0f && currentScale > tmpScale) || (tmpScale > 1.0f && currentScale < tmpScale)) {//scale
                postDelayed(this, 16);//自调
            } else {
                //设置为目标scale值
                float scale = mTargetScale / currentScale;
                mScaleMatrix.postScale(scale, scale, x, y);
                setImageMatrix(mScaleMatrix);
                isAutoScale = false;
            }
        }
    }

    @Override
    public void onGlobalLayout() {
        if (!mOnce) {
            //控件大小
            int width = getWidth();
            int height = getHeight();

            //得到图片及其宽高
            Drawable d = getDrawable();
            if (d == null) return;
            int dw = d.getIntrinsicWidth();
            int dh = d.getIntrinsicHeight();

            float scale = 1.0f;
            if (dw > width && dh < height) {
                scale = width * 1.0f / dw;

            }
            if (dw > height && dw < width) {
                scale = height * 1.0f / dh;
            }
            if ((dw > width && dh > height) || (dw < width && dh < height)) {
                scale = Math.min(width * 1.0f / dw, height * 1.0f / dw);
            }
            mInitScale = scale;
            mmaxScale = 4 * mInitScale;
            mMidScale = 2 * mInitScale;

//将图片移至控件中心
            int dx = (width - dw) / 2;
            int dy = (height - dh) / 2;
            mScaleMatrix.postTranslate(dx, dy);

            mScaleMatrix.postScale(mInitScale, mInitScale, width / 2, height / 2);
            setImageMatrix(mScaleMatrix);//3*3
            mOnce = true;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }


    public float getScale() {
        float values[] = new float[9];
        mScaleMatrix.getValues(values);//初始化矩阵

        return values[Matrix.MSCALE_X];//取矩阵第一个值
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scaleFactor = detector.getScaleFactor();
        float scale = getScale();
        if (getDrawable() == null) return true;
        //缩放范围的控制
        if ((scale < mmaxScale && scaleFactor > 1.0f) || (scale > mmaxScale && scale < 1.0f)) {
            if (scale * scaleFactor < mInitScale) {
                scaleFactor = mInitScale / scale;

            }
            if (scale * scaleFactor > mmaxScale) {
                scale = mmaxScale / scale;
            }
            mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY()); //不定缩放中心
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);
        }
        return true;
    }

    private void checkBorderAndCenterWhenScale() {
        RectF rectF = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;
        int width = getWidth();
        int height = getHeight();
        if (rectF.width() >= width) {
            if (rectF.left > 0) {
                deltaX = -rectF.left;
            }
            if (rectF.right < width) {
                deltaX = width - rectF.right;
            }
        }
        if (rectF.height() >= height) {
            if (rectF.top > 0) {
                deltaY = -rectF.top;
            }
            if (rectF.bottom < height) {
                deltaY = height - rectF.bottom;
            }
        }
        if (rectF.width() < width) {//让面积小于控件的图片居中
            deltaX = width / 2f - rectF.right + rectF.width() / 2f;


        }
        if (rectF.height() < height) {
            deltaY = height / 2f - rectF.bottom + rectF.height() / 2f;

        }
        mScaleMatrix.postScale(deltaX, deltaY);

    }

    private RectF getMatrixRectF() {//获取图片放大/缩小后的宽高和margin
        Matrix matrix = mScaleMatrix;
        RectF rectF = new RectF();
        Drawable d = getDrawable();
        if (d != null) {
            rectF.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rectF);

        }


        return rectF;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event)) return true;
        mScaleGestureDetector.onTouchEvent(event);

        float x = 0;
        float y = 0;
        int pointerCount = event.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }
        x /= pointerCount;
        y /= pointerCount;
        if (mLastPointerCount != pointerCount) {
            isCanDrag = false;
            mLastX = x;
            mLastY = y;
        }
        RectF rectF = getMatrixRectF();

        mLastPointerCount = pointerCount;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://滑动拦截eg.与ViewPager嵌套
                if (rectF.width() > getWidth() + 0.01 || rectF.height() > getHeight() + 0.01) {

                    if (getParent() instanceof ViewPager)
                        getParent().requestDisallowInterceptTouchEvent(true);//不允许父控件拦截子自己的事件

                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (rectF.width() > getWidth() + 0.01 || rectF.height() > getHeight() + 0.01) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    float dx = x - mLastX;
                    float dy = y - mLastY;
                    if (!isCanDrag) isCanDrag = isMoveAction(dx, dy);
                    else {
                        // RectF rectF = getMatrixRectF();
                        if (getDrawable() != null) {
                            isCheckLeftAndRight = isCheckTopAndBottom = true;

                            if (rectF.width() < getWidth()) {
                                isCheckLeftAndRight = false;
                                dx = 0;
                            }
                            if (rectF.height() < getHeight()) {
                                isCheckTopAndBottom = false;
                                dy = 0;

                            }
                            mScaleMatrix.postScale(dx, dy);
                            checkBorderWhenTranslate();
                            setImageMatrix(mScaleMatrix);
                        }
                    }
                    mLastY = y;
                    mLastX = x;
                    break;
                }

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mLastPointerCount = 0;

                break;

        }
        return true;
    }

    /**
     * 检查边界
     */

    private void checkBorderWhenTranslate() {
        RectF rect = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;
        int width = getWidth();
        int height = getHeight();
        if (rect.top > 0 && isCheckTopAndBottom) {
            deltaY = -rect.top;

        }
        if (rect.bottom < height && isCheckTopAndBottom) {
            deltaY = height - rect.bottom;

        }
        if (rect.left > 0 && isCheckLeftAndRight) {
            deltaX = -rect.left;
        }
        if (rect.right < width && isCheckLeftAndRight) {
            deltaX = width - rect.right;

        }
        mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    /**
     * touchsloop
     *
     * @param dx
     * @param dy
     * @return
     */
    private boolean isMoveAction(float dx, float dy) {

        return Math.sqrt(dx * dx + dy * dy) > mTouchSloop;
    }
}
