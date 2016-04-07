package com.dos.md.afbcs.v;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2015/12/10.
 */
public class CircleView extends View {

    public CircleView(Context context) {
        //super(context);
        this(context, null);
        //paint.setColor(color);
    }

    public CircleView(Context context, AttributeSet attrs) {
//        super(context, attrs);
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取xml中自定义属性

    }

    private int color= Color.RED;
    private Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);

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
        Bundle bundle = new Bundle();
        // 在这里保存当前状态
        return bundle;

    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        // 恢复保存的状态
    }
}
