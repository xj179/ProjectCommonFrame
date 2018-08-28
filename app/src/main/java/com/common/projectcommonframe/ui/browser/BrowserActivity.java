package com.common.projectcommonframe.ui.browser;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.ProgressBar;

import com.common.projectcommonframe.R;
import com.common.projectcommonframe.base.BaseActivity;
import com.common.projectcommonframe.base.BasePresenter;
import com.common.projectcommonframe.base.BaseView;
import com.common.projectcommonframe.view.Title;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 通用浏览器Activity
 */
public class BrowserActivity extends BaseActivity {

    /**
     * javaScrip传给服务器Tag名称，跟JS交互服务器需要使用的Tag
     */
    public static final  String JAVASCRIPT_TAG = "android" ;

    @BindView(R.id.title)
    Title title;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    /*通过Intent传过来的值*/
    String url = "http://www.baidu.com" ;
    String titleText = "浏览器Title";
    /*是否使用URL的Title*/
    boolean isUseUrlTitle=true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_browser;
    }

    @Override
    public BaseView createView() {
        return null;
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void init() {
        title.setTitle(titleText);
        title.setLeftButton(R.mipmap.icon_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initWebView();
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                if (i != 100){
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(i);
                }else{
                    progressBar.setVisibility(View.GONE);
                }
            }

            /**
             * Title是用URL的title
             * @param webView
             * @param s
             */
            @Override
            public void onReceivedTitle(WebView webView, String s) {
                super.onReceivedTitle(webView, s);
                if (isUseUrlTitle) {
                    title.setTitle(s);
                }
            }

            /*不设此属性会在有的手机上面打不开广告*/
            @Override
            public boolean onCreateWindow(final WebView webView, boolean b, boolean b1, final Message message) {
                final WebView newWebView = new WebView(webView.getContext());
                newWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView iview, String url) {
                        webView.loadUrl(url);
                        return true;
                    }
                });
                WebView.WebViewTransport transport = (WebView.WebViewTransport) message.obj;
                transport.setWebView(newWebView);
                message.sendToTarget();
                return true;
            }
        });
    }
    private void initWebView() {
        WebSettings settings = webView.getSettings();
        settings.setPluginState(WebSettings.PluginState.ON);
        if (Build.VERSION.SDK_INT > 16) {//设置视频自动播放
            settings.setMediaPlaybackRequiresUserGesture(false);
        }
        settings.setJavaScriptCanOpenWindowsAutomatically(true);//支持js自动弹框
        settings.setSupportZoom(true); //支持缩放
        settings.setBuiltInZoomControls(true); //支持手势缩放
        settings.setDisplayZoomControls(false); //是否显示缩放按钮
        // >= 19(SDK4.4)启动硬件加速，否则启动软件加速
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            settings.setLoadsImagesAutomatically(false);
        }
        settings.setUseWideViewPort(true); //设置内容自适应屏幕大小
        settings.setLoadWithOverviewMode(true);
        settings.setDatabaseEnabled(true);//设置允许使用数据库
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);//允许使用缓存
        settings.setDomStorageEnabled(true);
        settings.setSaveFormData(true);
        settings.setSupportMultipleWindows(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT); //优先使用缓存
        webView.setHorizontalScrollBarEnabled(false);
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER); // 取消WebView中滚动或拖动到顶部、底部时的阴影
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); // 取消滚动条白边效果
        webView.requestFocus();

        //注意：以下两项是必须设置，其他设置选项根据自身项目需求进行配置
        settings.setJavaScriptEnabled(true); //必须设置
        /*跟H5 JS交互接口*/
        webView.addJavascriptInterface(new MyJavascriptInterface(), JAVASCRIPT_TAG);

        //移除Android系统内部的默认内置接口,存在远程代码执行漏洞
        webView.removeJavascriptInterface("searchBoxJavaBridge_");
        webView.removeJavascriptInterface("accessibility");
        webView.removeJavascriptInterface("accessibilityTraversal");

        //webview中的下载调用系统下载
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String s, String s1, String s2, String s3, long l) {
                Uri uri = Uri.parse(s);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }

    /**
     * javaScript交互接口
     */
    class MyJavascriptInterface {

        /*
         *closePage JS端javaScript 方法名
         */
        @JavascriptInterface
        public void closePage() {
            finish();
        }

        @JavascriptInterface
        public void download(){
            Uri uri = Uri.parse("http://www.baidu.com");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

    }
}
