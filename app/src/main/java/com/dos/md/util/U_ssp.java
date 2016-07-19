package com.dos.md.util;

import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class U_ssp {
//Application.onCcreate();

    private static SharedPreferences mSp;

    public static void initSP(SharedPreferences sp) {
        mSp = sp;
    }

   /* public static void saveOrUpdate(String key, String json) {
        mSp.edit().putString(key, json).apply();
    }
    public static String find(String key) {
        return mSp.getString(key, null);
    }*/


    public static void delete(String key) {
        mSp.edit().remove(key).apply();
    }

    public static void clearAll() {
        mSp.edit().clear().apply();
    }


    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     */
    public static void saveUpdate(String key, Object object) {
        SharedPreferences.Editor editor = mSp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     */
    public static Object get(String key, Object defaultObject) {
if (defaultObject==null) return null;
        if (defaultObject instanceof String) {
            return mSp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return mSp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return mSp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return mSp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return mSp.getLong(key, (Long) defaultObject);
        } else {
            return null;
        }
    }

    private static class SharedPreferencesCompat {

        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException ignored) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException ignored) {
            }
            editor.commit();
        }
    }

}