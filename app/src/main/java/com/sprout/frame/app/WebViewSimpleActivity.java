package com.sprout.frame.app;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.LinearLayout;

import com.sprout.frame.baseframe.base.BaseActivity;
import com.sprout.frame.baseframe.webview.WebViewFunction;

import butterknife.OnClick;
import rx.functions.Action1;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * 网络请求示例
 */
public class WebViewSimpleActivity extends BaseActivity {

    private WebViewFunction webViewFunction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_webview_simple);
        setTitle("WebView示例");

        //注意, 这个父容器只能通过findViewById获取, 不能通过butterknife获取
        LinearLayout container = (LinearLayout) findViewById(R.id.llContainer);
        webViewFunction = new WebViewFunction()
                .initWebView(getApplicationContext(), container)
//                .load("http://192.168.6.160:8889/javascript.html", "jsName")
                .load("http://www.baidu.com", "jsName")
                .setCallback(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        //JS调Android后的回调
                    }
                });
    }

    //标题栏后退键处理
    @OnClick(R.id.btBack)
    public void goBack() {
        if (webViewFunction.canGoBack()) webViewFunction.goBack();
        else finish();
    }

    //Android调JS
    @OnClick(R.id.btCallJS)
    public void click() {
        webViewFunction
                .AndroidCallJS("javascript:callJS()")
                .setCallback(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        //Android调JS后的回调
                    }
                });
    }

    //物理后退键处理
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && webViewFunction.canGoBack()) {
            webViewFunction.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        webViewFunction.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webViewFunction.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webViewFunction.onDestory();
    }
}
