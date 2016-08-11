package com.dos.md.afbcs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.HttpAuthHandler;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dos.md.A_Base;

/**
 * todo:webView默认是不处理https请求     利用 WebView 加载本地 html: 跨进程在manifest.xml里设置
 * myWebView.loadData(htmlText,"text/html", "utf-8");
 * 此处的htmltext 是以字符串的方式读取 assets下 html中的内容.
 * WebView是拦截不到页面内的fragment跳转的
 * <p>
 * 在WebView加入 flash支持:
 * String temp = "<html><body bgcolor=\"" + "black"
 * + "\"> <br/><embed src=\"" + url + "\" width=\"" + "100%"
 * + "\" height=\"" + "90%" + "\" scale=\"" + "noscale"
 * + "\" type=\"" + "application/x-shockwave-flash"
 * + "\"> </embed></body></html>";
 * String mimeType = "text/html";
 * String encoding = "utf-8";
 * wv.loadDataWithBaseURL("null", temp, mimeType, encoding, "");
 */
public class A_WebView extends A_Base {
    private WebView wv;
    private String url = "";//"file:///android_asset/XX.html"); 本地文件存放在：assets中
    private WebSettings setting;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wv = new WebView(getApplicationContext());
        wv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
         setting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        setting.setLoadsImagesAutomatically(true);        

        setting = wv.getSettings();

        setting.setJavaScriptEnabled(true);
        setting.setAppCacheEnabled(true);

        setting.setBuiltInZoomControls(true);
        setting.setDisplayZoomControls(true);//显示2缩放按钮

        wv.requestFocus();        //触摸焦点起作用 若有输入框
        wv.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);       // 取消滚动条

        container.addView(wv);

        wv.addJavascriptInterface(new JSUseJ(), "second");
        wv.setDownloadListener(new DownloadListener() {//下载

            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition,
                                        String mimetype, long contentLength) {

                if (null != url) {
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            }

        });
        //屏蔽掉长按事件 webview默认长按时调用剪切板
        wv.setOnLongClickListener(new View.OnLongClickListener()

                                  {
                                      @Override
                                      public boolean onLongClick(View v) {
                                          return true;
                                      }
                                  }

        );


        if (null != savedInstanceState)
            wv.restoreState(savedInstanceState);
        else
            wv.loadUrl(url);


        wv.setWebViewClient(new WebViewClient() {
                                @Override
                                public boolean shouldOverrideUrlLoading(WebView view, String url) {//点击链接事件/重定向 true本地处理
                                    if (url.startsWith("mailto:") || url.startsWith("geo:") || url.startsWith("tel:")) {
                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                        startActivity(intent);
                                        return true;// TODO: 016/16/6/2016 false
                                    }
                                    wv.loadUrl(url);
                                    Log.d(url, "shouldOverrideUrlLoading:");
                                    return false;
                                }

                                @Override
                                public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String
                                        host, String realm) {//接收到Http请求的事件
                                    super.onReceivedHttpAuthRequest(view, handler, host, realm);
                                }


                                @Override
                                public void onPageStarted(WebView view, String url, Bitmap favicon) {//不稳定
                                    super.onPageStarted(view, url, favicon);
                                    Log.d(url, "pageStarted:");
                                }

                                @Override
                                public void onPageFinished(WebView view, String url) {//不稳定
                                    super.onPageFinished(view, url);
                                    Log.d(url, "pageFinished:");
                            
          
                if (!wv.getSettings().getLoadsImagesAutomatically()) {
                    setting.setLoadsImagesAutomatically(true);
                }
          
                                }

                                @Override
                                public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                /*首先需要在url中加特殊标记/协议, 如在onWebViewResource方法中拦截对应的请求，然后将要添加的请求头，以get形式拼接到url末尾在shouldInterceptRequest()方法中,可以拦截到所有的网页中资源请求，比如加载JS，图片以及Ajax请求等，    非超链接(如Ajax)请求无法直接添加请求头，考虑拼接到url末尾,这里拼接一个imei作为示例*/

                                    String ajaxUrl = url;
                                    // 如标识:req=ajax
                                    if (url.contains("req=ajax")) {
                                        ajaxUrl += "&imei=" + "imei";
                                    }
                                    Log.e(url, "shouldInterceptRequest:");

                                    return super.shouldInterceptRequest(view, ajaxUrl);

                                }

                            }

        );


        wv.setWebChromeClient(new MyWebChromeClient());

    }


    @Override

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        wv.saveState(outState);

    }


    @Override
    public void onBackPressed() {
        if (wv.canGoBack())
            wv.goBack();
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // TODO: 016/16/6/2016 rerunjs if needed         setting.setJavaScriptEnabled(true);
        wv.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        // TODO: 016/16/6/2016 停止runningjs if runnnig      setting.setJavaScriptEnabled(false); or
        wv.onPause();
    }

    @Override

    protected void onDestroy() {
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().startSync();
        CookieManager.getInstance().removeSessionCookie();//webview cookie clear

        wv.clearCache(true);
        wv.clearHistory();

        container.removeAllViews();//and othres need to if wrap the webview
        wv.stopLoading();
        wv.removeAllViews();
        wv.destroy();
        wv = null;
        container = null;
        System.exit(0);// Process.killProcess(Process.myPid());

        super.onDestroy();
    }

    protected void toTopFromButom() {
        /*if (wv.getContentHeight * wv.getScale() == (wv.getHeight() + wv.getScrollY())) { //已经处于底端 }
            wv.scrollTo(0, 0);
        }*/
    }


    class JSUseJ {
        @JavascriptInterface

        public void click(final String order) {//js回调来非主线程  onClick="window.second.click()"    不能运行在构造他的线程中
            mHandler.post(new Runnable() {
                @Override
                public void run() {//转UI线程
                    wv.loadUrl("javascript:wave(" + order + "orderfromjs" + ")");/*调js 对于字符串作为参数值需要进行转义双引号 String call = "javascript:alertMessage(\"" + "content" + "\")"         Android在4.4之前并没有提供直接调用js函数并获取值的方法，so得回调再回调。*/
                }
            });
        }

        @JavascriptInterface
        public void clickk(final String order) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    wv.loadUrl("javascript:wave(" + order + "orderfromjs" + ")");
                }
            });
        }
    }

    private ValueCallback<Uri> mUploadMessage;

    private class MyWebChromeClient extends WebChromeClient {

        // js上传文件的<input type="file">事件捕获
        // Android > 4.1.1 调用这个方法
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            mUploadMessage = uploadMsg;

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType(TextUtils.isEmpty(acceptType) ? "image/*" : acceptType);

            A_WebView.this.startActivityForResult(
                    Intent.createChooser(intent, "请选择"),
                    A_WebView.FILECHOOSER_RESULTCODE);

        }

        // 3.0 + 调用这个方法
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            openFileChooser(uploadMsg, acceptType, null);
        }

        // Android < 3.0 调用这个方法
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            openFileChooser(uploadMsg, null);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {/*与链接点击事件发生频率一致  加载出错做出相应提示通过获取Web页中的title用来设置自己界面中的title及相关问题:*/
            super.onReceivedTitle(view, title);

            setTitle(title);
            Log.e(url, "onReceivedTitle");

        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            Log.d(url, "onProgressChanged:");

            super.onProgressChanged(view, newProgress);//比onpagefinished稳定
        }
    }

    public static final int FILECHOOSER_RESULTCODE = 0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && FILECHOOSER_RESULTCODE == requestCode) {
            if (null == mUploadMessage) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
            return;
        }
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
