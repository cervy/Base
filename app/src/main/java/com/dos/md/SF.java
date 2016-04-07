package com.dos.md;

import android.os.Environment;

/**
 * Created by DOS on 2015/12/9.
 */
public interface SF {
    int ZERO = 0;
    String MSG = "msg";
    String BASE_URL = "";

    String SUCCESS = "success";
    String FAIL = "fail";

    String HOST = "";
    String FIRST_IN_FLAG = "first";

    String U_MENGAppkey = "5699aaabe0f55a54dc0013b8";
    int SUCESSCONNECTED = 200;
    String GET = "GET";
    String POST = "POST";
    int fiveThousand = 5000;
    String DATA_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    int tenThousand = 10000;





    //应用缓存根目录
    String ROOT_CACHE_PATH = Environment.getExternalStorageDirectory() + "/md/";
    //缓存用户信息key
    String CACHE_USER_KEY = "user_key";

    String ROOT_DATA_CACHE_PATH = ROOT_CACHE_PATH + "data/";


}
