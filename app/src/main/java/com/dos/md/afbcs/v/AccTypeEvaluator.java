package com.dos.md.afbcs.v;

import android.animation.TypeEvaluator;

/**
 * Created by SongNick on 15/9/7.
 */
public class AccTypeEvaluator implements TypeEvaluator<Float>{

    @Override
    public Float evaluate(float fraction, Float startValue, Float endValue) {
        return fraction * (endValue - startValue);
    }

}