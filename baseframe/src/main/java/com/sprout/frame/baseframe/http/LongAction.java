package com.sprout.frame.baseframe.http;

import android.content.Context;
import android.os.Handler;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;
import rx.functions.Func0;

/**
 * Create by Sprout at 2017/8/15
 */
public abstract class LongAction<T, S> {

    protected static Handler mHandler = new Handler();

    private List<Runnable> startAction = null;              //请求网络开始前调用本任务列表
    private List<Runnable> completAction = null;            //请求网络结束后调用本任务列表(Gson解析前)
    private List<Action1<HttpResult>> resultAction = null;  //请求网络结束后调用本任务列表(Gson解析后)

    /**
     * 针对 请求开始前 和 请求结束后 添加拦截器 (如菊花圈)
     */
    public LongAction<T, S> addInterceptor(final IInterceptor action) {
        addAction(new Func0<Pair<Runnable, Runnable>>() {
            @Override
            public Pair<Runnable, Runnable> call() {
                return new Pair<Runnable, Runnable>(new Runnable() {
                    @Override
                    public void run() {
                        action.runOnStart();
                    }
                }, new Runnable() {
                    @Override
                    public void run() {
                        action.runOnComplete();
                    }
                });
            }
        });
        return this;
    }

    /**
     * 添加一个返回值为 "任务对" 的任务, 并将任务对的第一个任务添加到 开始任务列表中, 将任务对的第二个任务添加到 结束任务列表中
     */
    private LongAction<T, S> addAction(Func0<Pair<Runnable, Runnable>> actionBuilder) {
        Pair<Runnable, Runnable> actions = actionBuilder.call();
        return addAction(actions.first, actions.second);
    }

    /**
     * 将指定的两个任务分别添加到 开始任务列表 和 结束任务列表 中
     */
    private LongAction<T, S> addAction(Runnable start, Runnable complet) {
        return addActionOnStart(start).addActionOnComplet(complet);
    }

    /**
     * 将指定的任务添加到 开始任务列表 中
     */
    private LongAction<T, S> addActionOnStart(Runnable action) {
        if (action == null) return this;
        if (startAction == null) startAction = new ArrayList<>();
        startAction.add(action);
        return this;
    }

    /**
     * 将指定的任务添加到 结束任务列表 中
     */
    private LongAction<T, S> addActionOnComplet(Runnable action) {
        if (action == null) return this;
        if (completAction == null) completAction = new ArrayList<>();
        completAction.add(action);
        return this;
    }

    /**
     * 将指定任务添加到 结果处理任务列表中
     */
    private LongAction<T, S> addActionOnResult(Action1<HttpResult> action) {
        if (action == null) return this;
        if (resultAction == null) resultAction = new ArrayList<>();
        resultAction.add(action);
        return this;
    }

    /**
     * 执行 开始任务列表 中的所有任务
     */
    protected void runOnStart() {
        if (startAction != null) {
            for (Runnable action : startAction) {
                mHandler.post(action);
            }
        }
    }

    /**
     * 执行 结束任务列表 中的所有任务
     */
    protected void runOnComplet() {
        if (completAction != null) {
            for (Runnable action : completAction) {
                mHandler.post(action);
            }
        }
    }

    /**
     * 执行 结果处理任务列表 中的所有任务(即对同一个请求结果, 不同的任务有不同的处理方法)
     */
    protected void runOnResult(final HttpResult result) {
        if (resultAction != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    for (Action1<HttpResult> action : resultAction) {
                        action.call(result);
                    }
                }
            });
        }
    }

    /**
     * 设置请求成功时的回调任务, 将之添加到结果处理任务列表中
     */
    public LongAction<T, S> onSuccess(final Action1<HttpResult> action) {
        addActionOnResult(new Action1<HttpResult>() {
            @Override
            public void call(HttpResult result) {
                if (result.getResultCode() == 0) action.call(result);
            }
        });
        return this;
    }

    /**
     * 设置请求失败时的回调任务, 将之添加到结果处理任务列表中
     */
    public LongAction<T, S> onFail(final Action1<HttpResult> action) {
        addActionOnResult(new Action1<HttpResult>() {
            @Override
            public void call(HttpResult result) {
                if (result.getResultCode() != 0) action.call(result);
            }
        });
        return this;
    }

    /**
     * 设置请求失败时不同结果码的回调任务, 将之添加到结果处理任务列表中
     */
    public LongAction<T, S> onFail(final int code, final Action1<HttpResult> action) {
        this.addActionOnResult(new Action1<HttpResult>() {
            @Override
            public void call(HttpResult result) {
                if (result.getResultCode() == code) action.call(result);
            }
        });
        return this;
    }

    /**
     * 创建请求失败时, 弹Toast的任务
     */
    public LongAction<T, S> onFailToast(Context context) {
        return onFail(InterceptorUtil.buildToastAction(context));
    }

    /**
     * 执行请求
     */
    public abstract void execute();
}
