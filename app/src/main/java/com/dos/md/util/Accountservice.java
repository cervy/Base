package com.dos.md.util;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by DOS on 2015/12/14.
 */
public class Accountservice {

    //异步获取验证码并倒计时time s, 设置time s内重复点击只响应一次
    public static void getCaptch(String phone, int b, final TextView getCaptcha) {
        long time = 0;
        if (!U_Validate.validateMobile(phone)) return;
        long currentTime = System.currentTimeMillis();
        if (System.currentTimeMillis() - time > b) {
            getCaptcha(phone);
            new CountDownTimer(b, 1000) {
                public void onTick(long millisUntilFinished) {
                    getCaptcha.setText("" + millisUntilFinished / 1000);
                }

                public void onFinish() {
                    getCaptcha.setText("获取验证码");
                }
            }.start();
        }
        time = currentTime;
    }

    public static void logI(String phone, String pass, String captcha, String surePass) {

        if (!pass.isEmpty() && !captcha.isEmpty() && surePass.equals(pass) && !U_Validate.validateMobile(phone)) {
            logIn(phone, surePass, captcha);
        }
    }

    public static void getCaptcha(String phone) {
    }

    public static void logIn(String phone, String captcha, String surePass) {

    }

    public static void logIn(String phone, String surePass) {

    }

}
