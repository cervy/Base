package com.dos.md.util;

import android.animation.Animator;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by DOS on 2016/1/6. //��n��������ʧЧview.setEnalbed(false);view.delayedPost(new Runnable(){@Override public void run(){view.setEnabled(ture)��}}, nk);//��ȡview������view.post(new Runnable(){@Override public void run(){view.getHeight();}});
 */
public class U_Android {
    public static void copyToClipBoard(Context context, String text, String success) {
        ClipboardManager manager = (ClipboardManager) context.getSystemService(
                Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(ClipData.newPlainText("meizhi_copy", text));
        Toast.makeText(context, success, Toast.LENGTH_SHORT).show();
    }

    public static void register(Context context) {
        Calendar today = Calendar.getInstance();
        Calendar now = Calendar.getInstance();

        today.set(Calendar.HOUR_OF_DAY, 12);
        today.set(Calendar.MINUTE, 11);
        today.set(Calendar.SECOND, 38);

        if (now.after(today)) {
            return;
        }

        Intent intent = new Intent("me.drakeet.meizhi.alarm");
        //intent.setClass(context, AlarmReceiver.class);

        PendingIntent broadcast = PendingIntent.getBroadcast(context, 520, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        manager.set(AlarmManager.RTC_WAKEUP, today.getTimeInMillis(), broadcast);
    }

    public static final int REVEAL_DURATION = 500;

    public static void showRevealEffect(final View v, int centerX, int centerY,
                                        @Nullable Animator.AnimatorListener lis) {
        if (!U_Validate.isAndroid5()) return;
        v.setVisibility(View.VISIBLE);

        int finalRadius = Math.max(v.getWidth(), v.getHeight());

        Animator anim = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(
                    v, centerX, centerY, 0, finalRadius);
        }

        assert anim != null;
        anim.setDuration(REVEAL_DURATION);

        if (lis != null)
            anim.addListener(lis);

        anim.start();
    }

    /*截取当前Activity的视图并保存。截的图是整个屏幕,也不能截出WebView的动态图表*/

    public static Bitmap captureScreen(Activity activity) {

        activity.getWindow().getDecorView().setDrawingCacheEnabled(true);

        return activity.getWindow().getDecorView().getDrawingCache();

    }
}
