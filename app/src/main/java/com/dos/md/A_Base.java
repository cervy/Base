package com.dos.md;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

/*DOS   封装3方库再用， 常量化键 compile 'com.github.thepacific:adapter:1.0.3'
日志利器：StackTraceElement.getMethodName()....
WeakHashMap       ViewDragHelper处理触摸事件          View.getContext()
* 含toolbar和container
* PowerManager.WakeLock和JobScheduler
*手机号正则 "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
邮箱正则 "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

*
*针对使用DVM的Android版本
1. 使用WebView的时候，需要注意确保调用destroy()
2. 考虑是否使用applicationContext()来构建WebView实例
3. 调用Dialog设置OnShowListener、OnDismissListener、OnCancelListener时，注意内部类是否泄漏Activity对象
4. 尽量不要自己持有Message对象
todo:startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));拨打电话 AccountManager TextUtils UrlUtils 签名信息：keytool -list -v -keystore release.jks
只更新listview可见itemprivate void updateItem(int index) {
    int visiblePosition = listView.getFirstVisiblePosition();
    if (index - visiblePosition >= 0) {
        //得到要更新的item的view
        View view = listView.getChildAt(index - visiblePosition);

        // 更新界面（示例参考）
        // TextView nameView = ViewLess.$(view, R.id.name);
        // nameView.setText("update " + index);
        // 更新列表数据（示例参考）
        // list.get(index).setName("Update " + index);
    }
}

setCompoundDrawablesWithIntrinsicBounds()
ListView的ClipPadding设为false，就能为ListView设置各种padding而不会出现丑陋的滑动“禁区”
startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber)));去拨号界面，带上号码可省权限
判断（在onCreate（和onResume里））全局user为空否决定是否已登录*/
public class A_Base extends AppCompatActivity {
    private FrameLayout container;
    public Toolbar toolbar;
    //private MyHandler handler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);

        setContentView(R.layout.activity_a_base);
        getWindow().setBackgroundDrawable(null);

        /*getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);设置全屏*/
       /* getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//屏幕常亮
       //透明状态栏        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏   getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //去掉默认添加的背景成了黑背景 getWindow().setBackgroundDrawable(null);*/
        container = (FrameLayout) findViewById(R.id.container);
        initToolbar();
        //handler = new MyHandler(this);
        // 设置分享的内容setShareContent();

    }
    protected <T extends View> T f(int rid) {
        return (T) findViewById(rid);
    }//免按强转键。。。。

    protected void initListeners(View.OnClickListener listener, View... views) {
        for (View view : views) {
            view.setOnClickListener(listener);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //界面成熟 获取view参数等
    }

    @Override
    public void onTrimMemory(int level) {//释放那些仅被你的UI使用的资源。
        super.onTrimMemory(level);
        /*if (level >= ComponentCallbacks2.TRIM_MEMORY_BACKGROUND) {
            clearCache();
        }*/
    }


    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        ActionBar actionBar = getSupportActionBar();
//actionBar.hide()/.show()在全屏和带Actionbar之间转换
        //if (canBack())
        actionBar.setDisplayHomeAsUpEnabled(true);//同时设置了默认的返回图标

        //toolbar.setNavigationIcon(R.mipmap.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //if (U_Validate.isAndroid5())           mAppBarLayout.setElevation(10.6f);

        //toolbar.setTitleTextColor(Color.WHITE);     //设定左上角突变可点击actionBar.setHomeButtonEnabled(true);

    }


    //右菜单+(左侧返回)
    public final void rightMenu(String title, Toolbar.OnMenuItemClickListener listener) {
        getSupportActionBar().setTitle(title);

        toolbar.setOnMenuItemClickListener(listener);
    }


    protected final void addView(int layoutId) {
        View child = LayoutInflater.from(this).inflate(layoutId, null);
        container.addView(child);
    }

    protected final void showToast(int text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    public final void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }




    /*protected final void showSnackbar(View v, int text, int actionName, View.OnClickListener listener) {
        Snackbar.make(v, text, Snackbar.LENGTH_LONG).setAction(actionName, listener).show();
    }*/

    /*private static class MyHandler extends Handler {

        WeakReference<A_Base> mReference = null;

        MyHandler(A_Base activity) {
            this.mReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            A_Base outer = mReference.get();
            if (!(outer == null && outer.isFinishing())) {
                outer.handleMessage(msg);

            }

        }
    }*/

    @Override
    protected void onDestroy() {
        //if (handler != null) handler.removeCallbacksAndMessages(null);
        super.onDestroy();
        //杀死后台线程android.os.Debug.stopMethodTracing();
    }


    protected void toA(Class<? extends Activity> clazz) {
        startActivity(new Intent(this, clazz));
    }

    protected void toAWithFinish(Class<? extends Activity> clazz) {
        toA(clazz);
        finish();
    }

    public void toAWithBundleFinish(Class<? extends Activity> clazz, boolean finish, Bundle bundle) {

        Intent intent = new Intent(this, clazz);
        if (bundle != null) intent.putExtras(bundle);
        startActivity(intent);
        if (finish) {
            finish();
        }
    }

    public void toAWithBundle$falg(Class<? extends Activity> clazz, int flags, boolean finish, Bundle bundle) {

        Intent intent = new Intent(this, clazz);
        if (bundle != null) intent.putExtras(bundle);
        intent.addFlags(flags);
        startActivity(intent);
        if (finish) {
            finish();
        }
    }


    /*public void handleMessage(Message msg) {
    }*/

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        /*if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else         */

        super.onBackPressed();
    }


    /*@Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/
    /*public void sendNotification(Class<? extends Activity> c) {

        new NotificationCompat.Builder(this).setContentIntent(PendingIntent.getActivity(A_Base.this, 0, new Intent(this, c), 0)).setDefaults(Notification.DEFAULT_ALL).build();//.getNotification();
        getSystemService(NOTIFICATION_SERVICE).notify();
    }*/

    /**
     * 注册广播
     */
   /* protected void registeBR(String action, BroadcastReceiver br) {
        IntentFilter filter = new IntentFilter();
        //filter.addAction(TA_ANDROID_NET_CHANGE_ACTION);
        filter.addAction(action);
        getApplicationContext().registerReceiver(br, filter);
    }*/

    /*去相册取   startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 0);
*/
    /*发短信*/
    /*public static void sendSms(Context context, String phoneNumber,
                               String content) {
        Uri uri = Uri.parse("smsto:"
                + (TextUtils.isEmpty(phoneNumber) ? "" : phoneNumber));
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", TextUtils.isEmpty(content) ? "" : content);
        context.startActivity(intent);
    }*/

    /*安装apk*/
    /*public static void installApk(Context context, File file) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("application/vnd.android.package-archive");
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }*/

    /*获取apk版本号  */
    /*public static String getAppVersion(Context context) {
        String version = "0";
        try {
            version = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    *//*是否有sd卡*//*
    public static boolean haveSDCard() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }*/

    /*回到home*/
    /*public static void toHome(Context context) {
        Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
        mHomeIntent.addCategory(Intent.CATEGORY_HOME);
        mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        context.startActivity(mHomeIntent);
    }*/
}

