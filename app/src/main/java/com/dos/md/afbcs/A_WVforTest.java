package webview.webview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.blueware.agent.android.PerformanceConfiguration;
import com.oneapm.agent.android.OneApmAgent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
/*todo:optimization： 独立进程， js调Java（Java运行在非其初始化线程（注意对UI的操作））*/
public class MainActivity extends AppCompatActivity {
    private WebView mWebView;
    private WebSettings mWebSettings;
    private ViewGroup viewGroup;
    private long start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OneApmAgent.init(this.getApplicationContext()).setToken("B055E90AC06097FAF253920AFCDDC8D905").start();

      /*  NewRelic.withApplicationToken(
                "e49dc813a351bdcfc6cb1f8ee182cf661f3ec300"
        ).start(this.getApplication());
*/
        PerformanceConfiguration.getInstance().setEnableFps(true);//帧率监控
        viewGroup = (ViewGroup) findViewById(R.id.con);
        mWebView = new WebView(this);
        mWebView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        viewGroup.addView(mWebView);

        //mWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        //mWebSettings.setAllowContentAccess(true);
        mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        mWebSettings.setLoadsImagesAutomatically(true);

        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setLoadWithOverviewMode(true);
        start = System.currentTimeMillis();

        mWebView.loadUrl("file:///android_asset/18/index.html");
       /* mWebSettings.setAllowFileAccess(true);
        mWebSettings.setAppCacheEnabled(true);*/
      /*  mWebSettings.setAppCachePath("/data/data/webview.webview/cache");
        mWebSettings.setAppCacheMaxSize(10 * 1024 * 1024); //
        mWebSettings.setDatabaseEnabled(true);*/
        //   mWebSettings.setDomStorageEnabled(true);
        /*String databasePath = getDir("databases", Context.MODE_PRIVATE).getPath();
        mWebSettings.setDatabasePath(databasePath);*/
        //mWebSettings.setBuiltInZoomControls(false);
        //  mWebView.   loadDataWithBaseURL("", getFromAssets("a.htm"), "text/html", "utf-8", null);

        /*
        mWebSettings.setUseWideViewPort(true);*/
        //mWebSettings.setDomStorageEnabled(true);
        //mWebSettings.setGeolocationEnabled(false);real time permission
        mWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {

            /*@Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // if (Uri.parse(url).getHost().equals("http://yujiangshui.com/webview-core-test/")) {
                mWebView.loadUrl(url);
                return false;
            }*/

            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
               /* Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }*/
          /*  @Override
            public void onPageFinished(WebView view, String url) {
                if (!mWebView.getSettings().getLoadsImagesAutomatically()) {
                    mWebSettings.setLoadsImagesAutomatically(true);
                }
            }
*/
           /* @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                mWebView.reload();
            }*/

        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            long baiping, baipingstart;

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                baipingstart = System.currentTimeMillis();
                baiping = baipingstart - start;
                Log.e("whiteScreen", String.valueOf(baiping));//
//view.loadUrl("javascript:alertMessage('domc:' + new Date().getTime())");
                view.loadUrl("javascript:" +
                        "window.addEventListener('DOMContentLoaded', function() {" +
                        "prompt('domc:' + new Date().getTime());})");
                view.loadUrl("javascript:" +
                        "window.addEventListener('load', function() {" +
                        "prompt('load:' + new Date().getTime());})");                // 'load:'整页时间




   /* view.loadUrl("javascript:"+" window.addEventListener('load', function() {
                            prompt('firstscreen:' + firstscreen_time());
    					});"   +
      "window.addEventListener('DOMContentLoaded', function() {" +
          "first_screen();
       });" */             //首屏时间
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult r) {
                //  Log.e("mainactivity", "**** Blocking Javascript Prompt :" + message);
                if (message != null) {
                    //if (!preCacheRun) {
                    String[] strs = message.split(":");
                    if (2 == strs.length) {
                        if ("domc".equals(strs[0]))
                            //result.getCurrentRun().setDocComplete(Long.valueOf(strs[1].trim()));
                            Log.e("time of DOMtree", String.valueOf(Long.valueOf(strs[1].trim()) - baipingstart));
                        if ("load".equals(strs[0]))
                            Log.e("time of wholePage", String.valueOf(Long.valueOf(strs[1].trim()) - start));

                    }
                    // }
                }
                r.confirm(defaultValue);
                return true;
            }


/*@Override
            public void onExceededDatabaseQuota(String url, String databaseIdentifier, long currentQuota, long estimatedSize, long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
                quotaUpdater.updateQuota(estimatedSize * 2);
            }*/
        });
        findViewById(R.id.c).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start = System.currentTimeMillis();

                mWebView.loadUrl("file:///android_asset/html20/index.html");//http://gold.xitu.io/explore/android


            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.pauseTimers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewGroup.removeAllViews();
        mWebView.removeAllViews();
        mWebView.destroy();
    }

    public class WebAppInterface {
        Context mContext;


        WebAppInterface(Context c) {
            mContext = c;
        }


        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public String getFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(
                    getResources().getAssets().open(fileName));

            BufferedReader bufReader = new BufferedReader(inputReader);

            String line = "";
            String Result = "";

            while ((line = bufReader.readLine()) != null)
                Result += line;
            if (bufReader != null)
                bufReader.close();
            if (inputReader != null)
                inputReader.close();
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
