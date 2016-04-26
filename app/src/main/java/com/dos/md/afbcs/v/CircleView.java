package com.dos.md.afbcs.v;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by Administrator on 2015/12/10.
 */
public class CircleView extends View {
private int mTouchSlop;
    public CircleView(Context context) {
        //super(context);
        this(context, null);
        //paint.setColor(color);
          mTouchSlop=ViewConfiguration.get(context).getScaledTouchSlop ();

    }

    public CircleView(Context context, AttributeSet attrs) {
//        super(context, attrs);
        this(context, attrs, 0);        mTouchSlop=ViewConfiguration.get(context).getScaledTouchSlop ();

    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop=ViewConfiguration.get(context).getScaledTouchSlop ();

        //获取xml中自定义属性

    }

    private int color= Color.RED;
    private final Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);

    @Override
    protected void onDraw(Canvas canvas) {
        final int paddingLeft=getPaddingLeft();
        final int paddingRight=getPaddingRight();
        final int paddingTop=getPaddingTop();
        final int paddingButtom=getPaddingBottom();


        int width=getWidth()-paddingLeft-paddingRight;
        int height=getHeight()-paddingButtom-paddingTop;
        int radius=Math.min(width, height)/2;
        canvas.drawCircle(paddingLeft+width/2, paddingTop+height/2, radius, paint);

    }

    @Override
    protected Parcelable onSaveInstanceState() {
        super.onSaveInstanceState();
        // 在这里保存当前状态
        return new Bundle();

    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        // 恢复保存的状态
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {/*//todo：单点拖动屏幕相应内容移动:在ACTION_DOWN事件发生时，调用getX和getY函数获得事件发生的x,y坐标值，并记录在mLastX和mLastY变量中。在ACTION_MOVE事件发生时，调用getX和getY函数获得事件发生的x,y坐标值,将其与mLastX和mLastY比较，如果二者差值大于一定限制（ScaledTouchSlop）,就执行scrollBy函数，进行滚动,最后更新mLastX和mLastY的值。在ACTION_UP和ACTION_CANCEL事件发生时，清空mLastX，mLastY*/
        int actionId = MotionEventCompat.getActionMasked(event);
        switch (actionId) {
            case MotionEvent.ACTION_DOWN:
                mLastX = event.getX();
                mLastY = event.getY();
                mIsBeingDragged = true;

                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;

            case MotionEvent.ACTION_MOVE:
                float curX = event.getX();
                float curY = event.getY();
                int deltaX = (int) (mLastX - curX);
                int deltaY = (int) (mLastY - curY);
                if (!mIsBeingDragged && (Math.abs(deltaX)> mTouchSlop ||
                        Math.abs(deltaY)> mTouchSlop)) {
                    mIsBeingDragged = true;
                    // 让第一次滑动的距离和之后的距离不至于差距太大
                    // 因为第一次必须>TouchSlop,之后则是直接滑动
                    if (deltaX > 0) {
                        deltaX -= mTouchSlop;
                    } else {
                        deltaX += mTouchSlop;
                    }
                    if (deltaY > 0) {
                        deltaY -= mTouchSlop;
                    } else {
                        deltaY += mTouchSlop;
                    }
                }
                // 当mIsBeingDragged为true时，就不用判断> touchSlopg啦，不然会导致滚动是一段一段的

                if (mIsBeingDragged) {
                    scrollBy(deltaX, deltaY);
                    mLastX = curX;
                    mLastY = curY;
                }
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mIsBeingDragged = false;
                mLastY = 0;
                mLastX = 0;
                break;
            default:
        }
        return mIsBeingDragged;
        /*监听并处理多触点的事件，我们还需要对ACTION_POINTER_DOWN和ACTION_POINTER_UP事件进行监听，并且在ACTION_MOVE事件时，要记录所有触摸点事件发生的x,y值。

        当ACTION_POINTER_DOWN事件发生时，我们要记录第二触摸点事件发生的x,y值为mSecondaryLastX和mSecondaryLastY，和第二触摸点pointer的id为mSecondaryPointerId
        当ACTION_MOVE事件发生时，我们除了根据第一触摸点pointer的x，y值进行滚动外，也要更新mSecondayLastX和mSecondaryLastY
        当ACTION_POINTER_UP事件发生时，我们要先判断是哪个触摸点手指被抬起来啦，如果是第一触摸点，那么我们就将坐标值和pointer的id都更换为第二触摸点的数据；如果是第二触摸点，就只要重置一下数据即可。
        switch (actionId) {
            case MotionEvent.ACTION_POINTER_DOWN:
                activePointerIndex = MotionEventCompat.getActionIndex(event);
                mSecondaryPointerId = MotionEventCompat.findPointerIndex(event,activePointerIndex);
                mSecondaryLastX = MotionEventCompat.getX(event,activePointerIndex);
                mSecondaryLastY = MotionEventCompat.getY(event,mActivePointerId);
                break;
            case MotionEvent.ACTION_MOVE:

                // handle secondary pointer move
                if (mSecondaryPointerId != INVALID_ID) {
                    int mSecondaryPointerIndex = MotionEventCompat.findPointerIndex(event, mSecondaryPointerId);
                    mSecondaryLastX = MotionEventCompat.getX(event, mSecondaryPointerIndex);
                    mSecondaryLastY = MotionEventCompat.getY(event, mSecondaryPointerIndex);
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                //判断是否是activePointer up了
                activePointerIndex = MotionEventCompat.getActionIndex(event);
                int curPointerId  = MotionEventCompat.getPointerId(event,activePointerIndex);
                Log.e(TAG, "onTouchEvent: "+activePointerIndex +" "+curPointerId +" activeId"+mActivePointerId+
                        "secondaryId"+mSecondaryPointerId);
                if (curPointerId == mActivePointerId) { // active pointer up
                    mActivePointerId = mSecondaryPointerId;
                    mLastX = mSecondaryLastX;
                    mLastY = mSecondaryLastY;
                    mSecondaryPointerId = INVALID_ID;
                    mSecondaryLastY = 0;
                    mSecondaryLastX = 0;
                    //重复代码，为了让逻辑看起来更加清晰
                } else{ //如果是secondary pointer up
                    mSecondaryPointerId = INVALID_ID;
                    mSecondaryLastY = 0;
                    mSecondaryLastX = 0;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mIsBeingDragged = false;
                mActivePointerId = INVALID_ID;
                mLastY = 0;
                mLastX = 0;
                break;
            default:
        }*/

    }private float mLastX, mLastY;
    private boolean mIsBeingDragged;
    private int activePointerIndex, mSecondaryPointerId;
    private float mSecondaryLastX, mSecondaryLastY;
}
