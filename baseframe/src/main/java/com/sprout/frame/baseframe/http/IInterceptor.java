package com.sprout.frame.baseframe.http;

/**
 * Create by Sprout at 2017/8/15
 */
interface IInterceptor {
    void runOnStart();

    void runOnComplete();
}
