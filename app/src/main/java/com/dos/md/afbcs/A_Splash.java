package com.dos.md.afbcs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.dos.md.A_Main;
import com.dos.md.R;
import com.dos.md.SF;
import com.dos.md.util.U_ssp;

/*DOS*/

public class A_Splash extends Activity {
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);//目的主题
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.splash, null);
        toMainOGuide();
        getWindow().setBackgroundDrawable(null);

        setContentView(view);


    }


    private void toMainOGuide() {
        AlphaAnimation start_anima = new AlphaAnimation(0.3f, 1.0f);
        start_anima.setDuration(SF.THOUSNADANDHALF);
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
        if ((boolean) (U_ssp.get(SF.FIRST, true))) {
            U_ssp.saveUpdate(SF.FIRST, false);
            startActivity(new Intent(getApplicationContext(), A_Guide.class));
        } else startActivity(new Intent(getApplicationContext(), A_Main.class));
        finish();

    }
}