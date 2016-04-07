package com.dos.md.afbcs.v;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * 适配一切RecyclerView.ViewHolder
 *
 * @author kymjs (http://www.kymjs.com/) on 8/27/15.
 */
public class RecyclerHolder extends RecyclerView.ViewHolder {
    private final SparseArray<View> mViews;
    private final int EIGHT = 8;

    public RecyclerHolder(View itemView) {
        super(itemView);
        //一般不会超过8个(type or so)吧
        this.mViews = new SparseArray<View>(EIGHT);
    }

    public SparseArray<View> getAllView() {
        return mViews;
    }

    /**
     * 通过控件的Id获取对应的控件，如果没有则加入views
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     *
     * @return RecyclerHolder
     */
    public RecyclerHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    public RecyclerHolder setText(int viewId, int text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为ImageView设置imageResource
     *
     * @return RecyclerHolder
     */
    public RecyclerHolder setImageResource(int viewId, int resDrawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(resDrawableId);

        return this;
    }

    /**
     * 为ImageView设置bitmap
     *
     * @return RecyclerHolder
     */
    public RecyclerHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    /**
     * 为ImageView设置图片:图片库直接加载

     */
     /*public RecyclerHolder setImageByUrl(int viewId, String url) {

        return this;
    }*/
}