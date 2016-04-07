package com.dos.md.util;

import android.content.SharedPreferences;


public class U_ssp {
//Application.onCcreate();

    private static SharedPreferences mSp;

    public static void initSP(SharedPreferences sp) {
        mSp = sp;
    }

    public static void saveOrUpdate(String key, String json) {
        mSp.edit().putString(key, json).apply();
    }

    public static String find(String key) {
        return mSp.getString(key, null);
    }

    public static void delete(String key) {
        mSp.edit().remove(key).apply();
    }

    public static void clearAll() {
        mSp.edit().clear().apply();
    }
}