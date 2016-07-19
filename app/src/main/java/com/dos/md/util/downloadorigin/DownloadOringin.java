package com.dos.md.util.downloadorigin;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by DOS on 2016/7/19.
 */
public class DownloadOringin {
    public static void download(String url, String mTempFilePath) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        try {
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = null;
                try {
                    is = connection.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                File file = new File(mTempFilePath);
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();
                // 为了防止文件过大导致OOM，这里直接把数据下载到文件中
                FileOutputStream fos = new FileOutputStream(file);
                // 5MB buffer
                BufferedOutputStream bos = new BufferedOutputStream(fos, 5 * 1024 * 1024);
                byte[] buffer = new byte[10240];// download 10kb every time
                int length;
                while ((length = is.read(buffer)) > -1) {
                    try {
                        bos.write(buffer, 0, length);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    bos.flush();
                    bos.close();
                    fos.close();
                    is.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(mTempFilePath, options);
                options.inSampleSize = calculateScaleSize(options.outWidth, options.outHeight, 480, 320);
// decode bitmap
                options.inJustDecodeBounds = false;
                Bitmap scaleBitmap = BitmapFactory.decodeFile(mTempFilePath, options);

                file.delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // calculate inSampleSize

    }

    private static int calculateScaleSize(int originalWidth, int originalHeight, int destWidth, int destHeight) {//向上取整
        int scaleWidth = (int) Math.ceil((originalWidth * 1.0f) / destWidth);
        int scaleHeight = (int) Math.ceil((originalHeight * 1.0f) / destHeight);
        return Math.max(scaleWidth, scaleHeight);
    }


}
