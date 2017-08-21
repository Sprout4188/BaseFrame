package com.sprout.frame.baseframe.http;

import android.content.Context;

import com.sprout.frame.baseframe.widgets.nicetoast.Toasty;
import com.sprout.frame.baseframe.widgets.progressbar.BaseProgressbar;

import rx.functions.Action1;

/**
 * Create by Sprout at 2017/8/15
 */
public class InterceptorUtil {

    public static Action1<HttpResult> buildToastAction(final Context context) {
        return new Action1<HttpResult>() {
            @Override
            public void call(HttpResult result) {
                Toasty.warning(context, result.getResultMessage()).show();
            }
        };
    }

    public static IInterceptor buildCircleProgressbar(final BaseProgressbar progressbar) {
        return new IInterceptor() {
            @Override
            public void runOnStart() {
                if (!BaseProgressbar.isShowing) progressbar.show();
            }

            @Override
            public void runOnComplete() {
                if (BaseProgressbar.isShowing) progressbar.disMiss();
            }
        };
    }
}
