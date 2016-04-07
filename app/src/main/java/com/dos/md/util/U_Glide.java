package com.dos.md.util;

import android.widget.ImageView;


public class U_Glide {


    /**
     * glide����ͼƬ
     *
     * @param view view
     * @param url  url
     */
    public static void display(ImageView view, String url) {
        //displayUrl(view, url, R.mipmap.ic_launcher);
    }


    /**
     * glide����ͼƬ
     *
     * @param view         view
     * @param url          url
     * @param defaultImage defaultImage
     */
    /*private static void displayUrl(final ImageView view, String url, @DrawableRes int defaultImage) {
        // ���ܱ�
        if (view == null) {
            // Logger.e("GlideUtils -> display -> imageView is null");
            return;
        }
        Context context = view.getContext();
        // View�㻹������
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

        try {
            Glide.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(defaultImage)
                    .crossFade()
                    .centerCrop()
                    .into(view)
                    .getSize((width, height) -> {
                        if (!view.isShown()) {
                            view.setVisibility(View.VISIBLE);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void displayNative(final ImageView view, @DrawableRes int resId) {
        // ���ܱ�
        if (view == null) {
            Logger.e("GlideUtils -> display -> imageView is null");
            return;
        }
        Context context = view.getContext();
        // View�㻹������
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

        try {
            Glide.with(context)
                    .load(resId)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade()
                    .centerCrop()
                    .into(view)
                    .getSize((width, height) -> {
                        if (!view.isShown()) {
                            view.setVisibility(View.VISIBLE);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void displayCircleHeader(ImageView view, @DrawableRes int res) {
        // ���ܱ�
        if (view == null) {
            Logger.e("GlideUtils -> display -> imageView is null");
            return;
        }
        Context context = view.getContext();
        // View�㻹������
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

        try {
            Glide.with(context)
                    .load(res)
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .bitmapTransform(new GlideCircleTransform(context))
                    .crossFade()
                    .into(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

}