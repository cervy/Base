package com.dos.md;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.dos.md.afbcs.A_Main;
import com.dos.md.util.U_SP;


//欢迎页(带欢迎页( 渐变。。。)的首页)， 亦可以是渐变后进入引导页
public class A_Splash extends Activity {
    private View view;
    private AlphaAnimation start_anima;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        firstStart();//0：是则直接去引导页    layout.splash 设为背景图
        view = View.inflate(this, R.layout.splash, null);
        setContentView(view);
        getWindow().setBackgroundDrawable(null);
        //1, 动画结束里启动showMain();
        //3, 隐藏欢迎页
        new Handler().postDelayed(new SplashRunnable(), 1500);//0,否则开线程去主页

    }

    private void showMain() {
        //可以是布局是一张图片，来段动画（透明渐变之类）再在兼听动画结束里跳到主界面
        start_anima = new AlphaAnimation(0.3f, 1.0f);
        start_anima.setDuration(1500);
        view.startAnimation(start_anima);
        start_anima.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(getApplicationContext(), A_Main.class));
            }
        });
    }

    class SplashRunnable implements Runnable {
        public void run() {
            startActivity(new Intent(getApplicationContext(), A_Main.class));
            finish();
            return;
        }
    }

    //判断程序是否首次被启动
    private void firstStart() {
        if ((boolean) (U_SP.get(this, SF.FIRST_IN_FLAG, false))) {
            //去引导页startActivity(intent.setClass(this, GuideActivity.class));  setContentView(findViewById(R.id.viewStub).inflate());
            U_SP.putAndApply(this, SF.FIRST_IN_FLAG, true);
            finish();
            return;
        }

    }
}