package com.sprout.frame.baseframe.utils;


import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by Sprout on 2017/7/22
 */
public class RxUtil {

    public static <T> void finish(Subscriber<T> subscriber, T obj) {
        if (null != subscriber && !subscriber.isUnsubscribed()) {
            subscriber.onNext(obj);
            subscriber.onCompleted();
        }
    }

    public static <T> void finish(Subscriber<T> subscriber) {
        if (null != subscriber && !subscriber.isUnsubscribed()) {
            subscriber.onCompleted();
        }
    }

    public static void errFinish(Subscriber subscriber, Throwable err) {
        subscriber.onError(err);
        subscriber.onCompleted();
    }

    public static Action1<Throwable> buildErrorAction(final Subscriber subscriber) {
        return new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onError(throwable);
                    subscriber.onCompleted();
                }
            }
        };
    }

    public static final Action1<Throwable> emptyErrAction = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {

        }
    };

    public static final Action1 emptyResultAction = new Action1() {
        @Override
        public void call(Object throwable) {

        }
    };

    public static <T> Subscriber<T> sameTo(final Subscriber<T> org) {
        return new Subscriber<T>() {
            @Override
            public void onCompleted() {
                org.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                org.onError(e);
            }

            @Override
            public void onNext(T t) {
                org.onNext(t);
            }
        };
    }
}
