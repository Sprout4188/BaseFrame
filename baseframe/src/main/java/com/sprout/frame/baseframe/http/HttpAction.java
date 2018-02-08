package com.sprout.frame.baseframe.http;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sprout.frame.baseframe.datamodel.Customer;
import com.sprout.frame.baseframe.http.typeAdapterFactory.NullStringTypeAdapterFactory;
import com.sprout.frame.baseframe.utils.AndroidUtil;
import com.sprout.frame.baseframe.utils.LogUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;


/**
 * Create by Sprout at 2017/8/15
 */
public abstract class HttpAction<E> extends LongAction<E, String> {
    //每个接口都需要传递的公共参数
    private static final String OS_VERSION = "osVersion";
    private static final String APP_VERSION = "appVersion";
    private static final String PHONE_TYPE = "phoneType";
    private static final String PHONE_ID = "phoneID";
    private static final String OS_TYPE_ANDROID = "2";
    private static final String APP_KEY = "appName";
    private static final String APP_NAME = "sr";
    private static final String INPUT1 = "input1";
    private static final String INPUT2 = "input2";

    private Map<String, String> map = new HashMap<>();
    private Map<String, File> fileMap = new HashMap<>();
    private Gson gson;
    private String tag;
    private int connTimeout = 0;
    private int readTimeout = 0;
    private int writeTimeout = 0;

    private static String host;
    private String rPath;

    public HttpAction(String rPath) {
        this(host, rPath);
    }

    public HttpAction(String host, String rPath) {
        HttpAction.host = host;
        this.rPath = rPath;
        initial();
    }

    private void initial() {
        //返回手机系统SDK版本(如16)
        add(OS_VERSION, AndroidUtil.SDK_VERSION_CODE + "");
        //返回手机类型      ("1"为iOS, "2"为Android)
        add(PHONE_TYPE, OS_TYPE_ANDROID);
        //返回手机ID       (即IMEI,UUID)
        add(PHONE_ID, AndroidUtil.getUUID());
        //返回APP版本名    (如1.5.8)
        add(APP_VERSION, AndroidUtil.APP_VERSION_NAME);
        //返回APP名称      (如"思锐")
        add(APP_KEY, APP_NAME);
        //数据模型中，有这两个参数，才传, 用于session保活, 原理如下:
        //本来第一次请求网络时, 服务器创建一个session会话, 返回一个cookie
        //手机收到响应后持久化存储该cookie, 以后每次请求都上传该cookie, 即可保证session不会被销毁

        //第一次请求网络时, 上传input1和input2, 服务器创建一个session会话, 并与input1和input2关联, 即相当于token
        //当在指定时间内手机端没有新的请求时, 则服务器将session挂起, 当下次请求时, 服务器先从挂起的session列表查询是否
        //有与input1和input2关联的session, 若有则激活, 若无才重新创建session
        String input1 = Customer.instance.input1;
        String input2 = Customer.instance.input2;
        if (!TextUtils.isEmpty(input1) && !TextUtils.isEmpty(input2)) {
            add(INPUT1, input1);
            add(INPUT2, input2);
        }
    }

    public static void setHost(String host) {
        HttpAction.host = host;
    }

    /**
     * 为本请求设置TAG, 可在BaseActivity的onDestroy中取消TAG标签的请求, 防止网络较差的情况下, 回调返回时, 用户已经退出界面了, 这时再去更新界面, 会出现空指针异常崩溃<Br/>
     * 若不需要更新界面, 且响应结果返回前界面已退出的情况下, 仍要处理响应结果, 则不要设置TAG
     */
    public HttpAction setTag(String tag) {
        this.tag = tag;
        return this;
    }

    /**
     * 为单次请求配置连接超时时间
     *
     * @param timeout 单位s
     */
    public HttpAction connTimeOut(int timeout) {
        this.connTimeout = timeout;
        return this;
    }

    /**
     * 为单次请求配置读取超时时间
     *
     * @param timeout 单位s
     */
    public HttpAction readTimeOut(int timeout) {
        this.readTimeout = timeout;
        return this;
    }

    /**
     * 为单次请求配置写入超时时间
     *
     * @param timeout 单位s
     */
    public HttpAction writeTimeOut(int timeout) {
        this.writeTimeout = timeout;
        return this;
    }

    /**
     * 添加普通参数
     */
    public HttpAction add(String key, String value) {
        map.put(key, value);
        return this;
    }

    /**
     * 添加文件参数
     */
    protected HttpAction addFile(String key, File file) {
        fileMap.put(key, file);
        return this;
    }

    public void execute() {
        if (TextUtils.isEmpty(host)) return;

        runOnStart();   //请求开始前的回调
        final String url = host.concat(rPath);
        PostFormBuilder builder = OkHttpUtils.post().url(url).params(map);

        HashMap<String, Object> tempMap = new HashMap<>();
        tempMap.putAll(map);
        if (fileMap.size() > 0) {
            tempMap.putAll(fileMap);
            for (Map.Entry<String, File> entry : fileMap.entrySet()) {
                builder.addFile(entry.getKey(), entry.getValue().getName(), entry.getValue());
            }
        }
        LogUtil.debug("请求", url.concat("\n").concat(new Gson().toJson(tempMap)));

        if (!TextUtils.isEmpty(tag)) builder.tag(tag);
        RequestCall requestCall = builder.build();
        if (connTimeout > 0) requestCall.connTimeOut(connTimeout * 1000);
        if (readTimeout > 0) requestCall.connTimeOut(readTimeout * 1000);
        if (writeTimeout > 0) requestCall.connTimeOut(writeTimeout * 1000);
        requestCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                //主动取消请求
                if (e != null && !TextUtils.isEmpty(e.getMessage()) && "Socket closed".equals(e.getMessage())) return;

                if (!TextUtils.isEmpty(url) && e != null && !TextUtils.isEmpty(e.getMessage()))
                    LogUtil.debug("响应", url.concat("\t").concat("onError").concat("\t").concat(e.getMessage()));
                //请求结束后的回调(Gson解析前)
                runOnComplet();
                //请求结束后的回调(Gson解析后)
                runOnResult(HttpResult.defaultErrorResult);
            }

            @Override
            public void onResponse(String response, int id) {
                LogUtil.debug("响应", url.concat("\t").concat("onResponse").concat("\n").concat(response));
                //请求结束后的回调(Gson解析前)
                runOnComplet();
                try {
                    //Gson解析响应
                    HttpResult<E> httpResult = new HttpResult<>();
                    httpResult.setEntity(decodeAndProcessModel(response, httpResult));
                    runOnResult(httpResult);
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnResult(HttpResult.paseErrorResult);
                }
            }
        });
    }

    /**
     * 解析公共响应
     */
    private E decodeAndProcessModel(String response, HttpResult<E> result) throws JSONException {
        //默认解析公共的数据
        JSONObject resultObject = new JSONObject(response).getJSONObject("result");
        result.setResultCode(resultObject.getInt("resultCode"));
        result.setResultMessage(resultObject.getString("resultMessage"));
        //解析非公共的数据
        if (gson == null) gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringTypeAdapterFactory()).create();
        E e = decodeModel(response, result, gson);
        result.setEntity(e);
        return e;
    }

    /**
     * 解析特有响应
     */
    public E decodeModel(String response, HttpResult<E> result, Gson gson) throws JSONException {
        return null;
    }
}
