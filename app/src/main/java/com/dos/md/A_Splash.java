package com.dos.md;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.dos.md.afbcs.A_Guide;
import com.dos.md.afbcs.A_Main;
import com.dos.md.util.U_ssp;


//带欢迎页( 渐变。。。)的首页), 首次打开则欢迎页进引导页再首页
public class A_Splash extends Activity {
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.splash, null);
        showMain();        //动画结束里启动showMain();
        getWindow().setBackgroundDrawable(null);

        setContentView(view);


    }

    public static final int THOUSNADANDHALF = 1500;

    private void showMain() {
        AlphaAnimation start_anima = new AlphaAnimation(0.3f, 1.0f);
        start_anima.setDuration(THOUSNADANDHALF);
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
                firstStart();
            }
        });
    }



    private void firstStart() {
        if ((boolean) (U_ssp.get(SF.FIRST_IN_FLAG, true))) {
            U_ssp.saveUpdate(SF.FIRST_IN_FLAG, false);
            startActivity(new Intent(getApplicationContext(), A_Guide.class)); //去引导页
        } else startActivity(new Intent(getApplicationContext(), A_Main.class));
        finish();

    }
}