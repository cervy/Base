package com.dos.md.util;


import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

/**
 * 打印调试
 */
public class U_Debug {
    public static final String TAG = "Debug";

    public class Debug {
        //不需打印信息时，为false
        public static final boolean DEBUG_MODE = true;
    }

    private U_Debug() {

    }

    public final static void println(String printInfo) {
        if (Debug.DEBUG_MODE && null != printInfo) {
            System.out.println(printInfo);
        }
    }

    public final static void print(String printInfo) {
        if (Debug.DEBUG_MODE && null != printInfo) {
            System.out.print(printInfo);
        }
    }

    public final static void printLogI(String logInfo) {
        printLogI(TAG, logInfo);
    }

    public final static void printLogI(String tag, String logInfo) {
        if (Debug.DEBUG_MODE && null != tag && null != logInfo) {
            Log.i(tag, logInfo);
        }
    }

    public final static void printLogE(String logInfo) {
        printLogE(TAG, logInfo);
    }

    public final static void printLogE(String tag, String logInfo) {
        if (Debug.DEBUG_MODE && null != tag && null != logInfo) {
            Log.e(tag, logInfo);
        }
    }

    public final static void printLogW(String logInfo) {
        printLogW(TAG, logInfo);
    }

    public final static void printLogW(String tag, String logInfo) {
        if (Debug.DEBUG_MODE && null != tag && null != logInfo) {
            Log.w(tag, logInfo);
        }
    }

    public final static void printLogD(String logInfo) {
        printLogD(TAG, logInfo);
    }

    public final static void printLogD(String tag, String logInfo) {
        if (Debug.DEBUG_MODE && null != tag && null != logInfo) {
            Log.d(tag, logInfo);
        }
    }

    public final static void printLogV(String logInfo) {
        printLogV(TAG, logInfo);
    }

    public final static void printLogV(String tag, String logInfo) {
        if (Debug.DEBUG_MODE && null != tag || null != logInfo) {
            Log.v(tag, logInfo);
        }
    }

    public final static void printLogWtf(String logInfo) {
        printLogWtf(TAG, logInfo);
    }

    public final static void printLogWtf(String tag, String logInfo) {
        if (Debug.DEBUG_MODE && null != tag && null != logInfo) {
            Log.wtf(tag, logInfo);
        }
    }

    public final static void showToast(Context context, String toastInfo) {
        if (null != context && null != toastInfo) {
            Toast.makeText(context, toastInfo, Toast.LENGTH_LONG).show();
        }
    }

    public final static void showToast(Context context, String toastInfo, int timeLen) {
        if (null != context && null != toastInfo && (timeLen > 0)) {
            Toast.makeText(context, toastInfo, timeLen).show();
        }
    }

    public final static void printBaseInfo() {
        if (Debug.DEBUG_MODE) {
            StringBuffer strBuffer = new StringBuffer();
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();

            strBuffer.append("; class:").append(stackTrace[1].getClassName())
                    .append("; method:").append(stackTrace[1].getMethodName())
                    .append("; number:").append(stackTrace[1].getLineNumber())
                    .append("; fileName:").append(stackTrace[1].getFileName());

            println(strBuffer.toString());
        }
    }

    public final static void printFileNameAndLinerNumber() {
        if (Debug.DEBUG_MODE) {
            StringBuffer strBuffer = new StringBuffer();
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();

            strBuffer.append("; fileName:").append(stackTrace[1].getFileName())
                    .append("; number:").append(stackTrace[1].getLineNumber());

            println(strBuffer.toString());
        }
    }

    public final static int printLineNumber() {
        if (Debug.DEBUG_MODE) {
            StringBuffer strBuffer = new StringBuffer();
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();

            strBuffer.append("; number:").append(stackTrace[1].getLineNumber());

            println(strBuffer.toString());
            return stackTrace[1].getLineNumber();
        } else {
            return 0;
        }
    }

    public final static void printMethod() {
        if (Debug.DEBUG_MODE) {
            StringBuffer strBuffer = new StringBuffer();
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();

            strBuffer.append("; number:").append(stackTrace[1].getMethodName());

            println(strBuffer.toString());
        }
    }

    public final static void printFileNameAndLinerNumber(String printInfo) {
        if (null == printInfo || !Debug.DEBUG_MODE) {
            return;
        }
        StringBuffer strBuffer = new StringBuffer();
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();

        strBuffer.append("; fileName:").append(stackTrace[1].getFileName())
                .append("; number:").append(stackTrace[1].getLineNumber()).append("/n")
                .append((null != printInfo) ? printInfo : "");

        println(strBuffer.toString());
    }

    public final static void showStrictMode() {
        if (Debug.DEBUG_MODE) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        }
    }

    public final static void d(String tag, String msg) {
        if (Debug.DEBUG_MODE) {
            Log.d(tag, msg);
        }
    }


}