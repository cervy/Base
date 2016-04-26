package com.dos.md;

import android.app.Application;

import com.dos.md.data.bean.User;
import com.dos.md.util.CrashHandler;
import com.dos.md.util.U_ssp;

//import com.anupcowkur.reservoir.Reservoir;
//import com.dos.md.util.objectcache.CacheManager;
//import com.dos.md.util.objectcache.DiskCache;

/**
 * Created by DOS on 2015/12/2.
 * <p/>
 * satic 里引用context.getApplicationContext();
 */
public class MyApp extends Application {
   /* public static final int ONE_KB = 1024;
    public static final int ONE_MB = ONE_KB * 1024;
    public static final int CACHE_DATA_MAX_SIZE = ONE_MB * 10;
*/

    private static MyApp myApp;
    //private static CacheManager cacheManager;//缓存管理

    private User user;
   // public Gson gson;

    public static MyApp getMyApp() {
        return myApp;
    }


    public void setUserModel(User userModel) {
        user = userModel;
        U_ssp.saveUpdate(SF.CACHE_USER_KEY, userModel);

    }

    public User getUserModel() {
        return user;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
        CrashHandler.getInstance().init(this);

        U_ssp.initSP(this.getSharedPreferences("", MODE_PRIVATE));
//        setUserModel((User) U_ssp.get(SF.CACHE_USER_KEY, null));



//获取手机设备号imei        ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();
        //初始化高德定位功能     initAmapLocation();
        //获取本地保存的用户信息
        //获取本地保存的用户信息
//getVersionName(this, getPackageName());
    }

}
