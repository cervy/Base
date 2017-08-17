package com.dos.md.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * todo: webView中的js和Android交互支持 APILevel 17及以上，以下不支持，这里限制17+才支持?
 */
public class U_Validate {
    /**
     * 描述：判断当前版本是否更新
     */
    public static boolean isUpdateApp(int curCode, Context context) {
        boolean isUpdate = false;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int code = info.versionCode;
            if (curCode > code) {
                isUpdate = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            isUpdate = false;
        }
        return isUpdate;
    }

    public static boolean validateMobile(String tel) {
        String telRegex = "[1][3758]\\d{9}";//;

        return !TextUtils.isEmpty(tel) && tel.matches(telRegex);
    }

    public static String getSafeMobileNum(String mobiles) {
        if (!validateMobile(mobiles)) {
            return null;
        }
        return mobiles.substring(0, 3) + "*****" + mobiles.substring(8, mobiles.length());
    }

    /**
     * 方法名：isUserNameorPassword
     * <p/>
     * 描述：判断是否是合法用户名（6~20数字字母及下划线组成）
     *
     * @参数： 参数名  参数类型   参数描述
     * @返回值类型： boolean
     * @创建时间： 2014年6月23日
     * @创建者：韩创
     * @变更记录：2014年6月23日下午5:29:01 by
     */
    public static boolean isUserNameOrPasswd(String usermane) {

        for (int i = 0; i < usermane.length(); i++) {
            int chr1 = usermane.charAt(i);
            if (chr1 > 19968) {
                return false;
            }
        }

        Pattern p = Pattern.compile("^\\w{6,20}$");
        Matcher m = p.matcher(usermane);
        return m.matches();
    }

    /**
     * 方法名：isAllNumber
     * <p/>
     * 描述：检测是否为纯数字
     *
     * @参数： 参数名  参数类型   参数描述
     * @返回值类型： boolean
     * @创建时间： 2014年11月26日
     * @创建者：韩创
     * @变更记录：2014年11月26日下午1:44:07 by
     */
    public static boolean isAllNumber(String str) {
        if (isEmpty(str)) {
            return false;
        }
        String reg = "^\\d+$";
        return str.matches(reg);
    }

    /**
     * 判断邮箱地址是否合法
     *
     * @param email
     * @return
     */
    public static boolean isEmailAdress(String email) {

        if (TextUtils.isEmpty(email)) {
            return false;
        }

        if (email.startsWith("_") || email.contains("_@")) {
            return false;
        }

        Pattern p = Pattern
                .compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                        ")+");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 去除字符串外围的<p>标签
     */
    public static String clearHtmlTab(String str) {
        if (!TextUtils.isEmpty(str)) {

            if (str.contains("<p") && str.contains("</p>")) {
                str = str.substring(str.indexOf(">") + 1);
                str = str.substring(0, str.lastIndexOf("</"));
            }

        }

        return str;
    }


    /**
     * 判断给定字符串是否空白串。
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input) || "null".equals(input.toLowerCase().trim()))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }


    

    public static boolean hasNetwork(Context context) {//被集成到网库
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }


    public static Bitmap decodeBMFromRes(Resources res, int resId, int reqWidth, int reqHeight) {//被集成到图库
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInsampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);

    }

    public static int calculateInsampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }

        }
        return inSampleSize;
    }


    public static boolean isEmpty(CharSequence str) {
        return isNull(str) || str.length() == 0;
    }

    public static boolean isEmpty(Object[] os) {
        return isNull(os) || os.length == 0;
    }

    public static boolean isEmpty(Collection<?> l) {
        return isNull(l) || l.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> m) {
        return isNull(m) || m.isEmpty();
    }

    public static boolean isNull(Object o) {
        return o == null;
    }


    public static boolean isAndroid5() {//区分系统版本
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }


    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - mLastClickTime;
        if (0 < timeD && timeD < 1000) return true;
        mLastClickTime = time;
        return false;

    }

    private static long mLastClickTime;


    public static int[] getScreenSize(Context context) {

        int[] size = new int[2];

        WindowManager w = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display d = w.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);
// since SDK_INT = 1;
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
// includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17)
            try {
                widthPixels = (Integer) Display.class.getMethod("getRawWidth").invoke(d);
                heightPixels = (Integer) Display.class.getMethod("getRawHeight").invoke(d);
            } catch (Exception ignored) {
            }
// includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 17)
            try {
                Point realSize = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(d, realSize);
                widthPixels = realSize.x;
                heightPixels = realSize.y;
            } catch (Exception ignored) {
            }
        size[0] = widthPixels;
        size[1] = heightPixels;
        return size;
    }


    //把字节数组转换成16进制字符串1
    public static String bytesToHexString(byte[] bArray) {
        StringBuilder sb = new StringBuilder(bArray.length);
        String sTemp;
        for (byte aBArray : bArray) {
            sTemp = Integer.toHexString(0xFF & aBArray);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }


    public static void saveToFile(File file, Bitmap bmp) throws IOException {
        FileOutputStream out = new FileOutputStream(file);
        bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
        out.flush();
        out.close();
    }

    /**
     * 描述：将Unicode编码转换为汉字
     */
    public static String convert(String ori) {
        char aChar;
        int len = ori.length();
        StringBuilder outBuffer = new StringBuilder(len);
        for (int x = 0; x < len; ) {
            aChar = ori.charAt(x++);
            if (aChar == '\\') {
                aChar = ori.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx


                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = ori.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);

        }
        return outBuffer.toString();
    }

/**
     * SD卡判断
     *
     * @return boolean
     */
    public static boolean isSDCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
/**
     * 创建App文件夹
     *
     * @param appName     appName
     * @param application application
     * @return String
     */
    public static String createAPPFolder(String appName, Application application) {
        return createAPPFolder(appName, application, null);
    }

    /**
     * 创建App文件夹
     *
     * @param appName     appName
     * @param application application
     * @param folderName  folderName
     * @return String
     */
    public static String createAPPFolder(String appName, Application application, String folderName) {
        File root = Environment.getExternalStorageDirectory();
        File folder;
        /**
         * 如果存在SD卡
         */
        if (root != null) {//DeviceUtils.isSDCardAvailable() && todo:DeviceUtils
            folder = new File(root, appName);
            if (!folder.exists()) {
                folder.mkdirs();
            }
        } else {
            /**
             * 不存在SD卡，就放到缓存文件夹内
             */
            root = application.getCacheDir();
            folder = new File(root, appName);
            if (!folder.exists()) {
                folder.mkdirs();
            }
        }
        if (null != null) {// TODO: 2016/4/25 0025  
            folder = new File(folder, null);
            if (!folder.exists()) {
                folder.mkdirs();
            }
        }
        return folder.getAbsolutePath();
    }

    /**
     * 通过Uri找到File
     *
     * @param context context
     * @param uri     uri
     * @return File
     */
    public static File uri2File(Activity context, Uri uri) {
        File file;
        String[] project = {MediaStore.Images.Media.DATA};
        Cursor actualImageCursor = context.getContentResolver().query(uri, project, null, null, null);
        if (actualImageCursor != null) {
            int actual_image_column_index = actualImageCursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualImageCursor.moveToFirst();
            String img_path = actualImageCursor
                    .getString(actual_image_column_index);
            file = new File(img_path);
        } else {
            file = new File(uri.getPath());
        }
        if (actualImageCursor != null) actualImageCursor.close();
        return file;
    }



// /根据系统版本，调用版本API中的设置View背景
public static void setViewBackgroundDrawable(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

/*public static Drawable getColorDrawable(Resources res, int drawableResId, int colorResId){
    Drawable drawable=res.getDrawable(drawableResId,null);
            drawable.setColorFilter(res.getColor(colorResId, PorterDuff.Mode.SRC_IN));
    return drawable;


}*/




 /* 利用只读属性动画+WindowManager设置窗口透明度
  *@param from\>=0&&from\<=1.0f
  * @param to\>=0&&to\<=1.0f
  * 
  * */
 private void dimBackground(final float from, final float to) {
 final Window window = getWindow();
 ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
 valueAnimator.setDuration(500);
 valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
 @Override
 public void onAnimationUpdate(ValueAnimator animation) {
 WindowManager.LayoutParams params = window.getAttributes();
 params.alpha = (Float) animation.getAnimatedValue();
 window.setAttributes(params);
 }
 });

 valueAnimator.start();
 }
}
