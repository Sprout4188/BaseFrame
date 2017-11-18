package com.sprout.frame.baseframe.webview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.sprout.frame.baseframe.utils.NetUtil;

import rx.functions.Action1;

/**
 * creat by lucky_code at 2017/11/6
 * 继承object，是为了js调用Android
 */
public class WebViewFunction extends Object {
    private WebView webView;
    private WebSettings webSettings;
    private Context context;
    private LinearLayout layout;
    private LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    private Action1<String> action1;

    /**
     * 初始化webView
     *
     * @param layout WebView的父容器
     */
    public WebViewFunction initWebView(Context context, LinearLayout layout) {
        this.context = context;
        this.layout = layout;
        webView = new WebView(context);
        webView.setLayoutParams(params);
        layout.addView(webView);
        webSettings = webView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //支持插件  这个方法，在18版本后，被弃用，原因是adobe不再开发新的flash移动版了
        webSettings.setPluginState(WebSettings.PluginState.ON);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(false); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他细节操作
        if (NetUtil.getInstance().isNetworkConnected()) {
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//根据cache-control决定是否从网络上取数据。
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
        }
        //缓存模式如下：
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        return this;
    }

    /**
     * 加载网页
     *
     * @param url
     * @param jsName 本类对象 映射到js上的对象名字
     */
    public WebViewFunction load(final String url, String jsName) {
        Log.d("WebViewFunction", "加载");
        //通过addJavascriptInterface()将Java对象映射到JS对象
        //参数1：java这边的对象
        //参数2：映射到js上的对象名字
        if (!TextUtils.isEmpty(jsName)) webView.addJavascriptInterface(this, jsName);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(final WebView view, String url) {
                // 如下方案可在非微信内部WebView的H5页面中调出微信支付
//                if (!(url.startsWith("http") || url.startsWith("https"))) return true;
//                if (url.startsWith("weixin://wap/pay?")) {
//                    Intent intent = new Intent();
//                    intent.setAction(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse(url));
//                    context.startActivity(intent);
//                    return true;
//                } else {
//                    Map<String, String> extraHeaders = new HashMap<String, String>();
//                    extraHeaders.put("Referer", "http://wxpay.wxutil.com");
//                    view.loadUrl(url, extraHeaders);
//                }


                // ------  对alipays:相关的scheme处理 -------
                if (url.startsWith("alipays:") || url.startsWith("alipay")) {
                    try {
                        context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                    } catch (Exception e) {
                        new AlertDialog.Builder(context)
                                .setMessage("未检测到支付宝客户端，请安装后重试。")
                                .setPositiveButton("立即安装", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Uri alipayUrl = Uri.parse("https://d.alipay.com");
                                        context.startActivity(new Intent("android.intent.action.VIEW", alipayUrl));
                                    }
                                }).setNegativeButton("取消", null).show();
                    }
                    return true;
                }
                if (!(url.startsWith("http") || url.startsWith("https"))) return true;
                // ------- 处理结束 -------

                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //开始加载时的操作
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //加载结束时的操作
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                //设定加载资源的操作 在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次。
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                //提示错误
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //进度条
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                //获取网页中的title
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                if (action1 != null) action1.call(message);
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                if (action1 != null) action1.call(message);
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                if (action1 != null) action1.call(consoleMessage.message());
                return true;
            }

        });

        webView.loadUrl(url);
        return this;
    }

    public WebViewFunction AndroidCallJS(final String function) {
        if (webView != null) webView.loadUrl(function);
        return this;
    }

    /**
     * 定义JS需要调用的方法
     * 1.被JS调用的方法必须加入@JavascriptInterface注解
     * 2.方法名必须与JS定义的一样
     *
     * @param msg 最好带个参数, 如果不带, 某些情况下会回调不了 (如果不需要该参数, JS调用时随便传个或不传即可)
     */
    @JavascriptInterface
    public void rechargeBack(String msg) {
        if (action1 != null) action1.call(msg);
    }

    @JavascriptInterface
    public void backHome(String msg) {
        //如当JS调用本方法后, 这里做跳转至首页处理
    }

    public void onResume() {
        if (webView != null) webView.onResume();
    }

    public void onPause() {
        if (webView != null) webView.onPause();
    }

    public void onDestory() {
        if (layout != null && webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            layout.removeView(webView);
            webView.destroy();
            webView = null;
        }
    }

    /**
     * 判断网页是否可以回退
     */
    public boolean canGoBack() {
        return webView != null && webView.canGoBack();
    }

    /**
     * 回退网页
     */
    public WebViewFunction goBack() {
        if (webView != null) webView.goBack();
        return this;
    }

    public WebViewFunction setCallback(Action1<String> action1) {
        this.action1 = action1;
        return this;
    }
}
