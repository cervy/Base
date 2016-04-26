package com.dos.md.afbcs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.ViewFlipper;

import com.dos.md.R;

public class A_ScrollToEnd extends AppCompatActivity {
    private ViewFlipper flipper;
    private ScrollView s;
    private final int[] image = {R.mipmap.ic_launcher, R.drawable.ic_menu_camera, R.drawable.ic_menu_gallery};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_a__scroll_to_end);
        flipper = (ViewFlipper) findViewById(R.id.flipper);
        flipperAddV();
        flipper.setFlipInterval(1000);
        flipper.setAutoStart(true);
        flipper.startFlipping();

        s = (ScrollView) findViewById(R.id.scroll);
        assert s != null;
        s.scrollTo(0, -30);//（滚动条）向下滚动到30
        s.scrollBy(0, -30);//每次向下滚动30
        s.setOnTouchListener(new View.OnTouchListener() {//兼听scrollView滑动到底部
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_MOVE://0， getScrollY() 滚动条距顶（此处为垂直方向滑动的scrollView）1, getMeasuredHeight()内容总高  2, getHeight()屏高
                        if (0 >= s.getScrollY()) {
                            Log.e("top", "topping");
                            //下拉刷新
                        }
                        if (s.getChildAt(0).getMeasuredHeight() <= s.getHeight() + s.getScrollY()) {//在scrollView顶部 eg.全屏的textView总高<=屏高+滚动条距顶
                            Log.e("buttomO", "buttom");
                            //在scrollView底部， 上拉加载更多
                            //TextView.append(str);

                        }

                        break;
                    default:

                }
                return false;
            }
        });
    }

    private void flipperAddV() {
        for (int anImage : image) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(anImage);
            flipper.addView(imageView);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = 0;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                break;
            case MotionEvent.ACTION_MOVE://手动滑动需禁止初始化时的自动滑动？
                if (x - event.getX() > 100) {
                    /*flipper.setInAnimation();
                    flipper.setOutAnimation();*/
                    flipper.showNext();

                }
                if (x - event.getX() < -100) flipper.showPrevious();

                break;
        }

        return super.onTouchEvent(event);

    }
}
