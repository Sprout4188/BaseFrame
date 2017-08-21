package com.sprout.frame.baseframe.http;


/**
 * Create by Sprout at 2017/8/15
 */
public class HttpResult<E> {

    private E entity;
    private int resultCode;
    private String resultMessage;

    public HttpResult() {
    }

    public HttpResult(int resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public void setEntity(E entity) {
        this.entity = entity;
    }

    public E getEntity() {
        return entity;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    /**
     * 系统异常
     */
    public static final HttpResult defaultErrorResult = new HttpResult(1, "系统异常");
    /**
     * 账号或密码为空
     */
    public static final HttpResult accountErrorResult = new HttpResult(2, "账号或密码为空");
    /**
     * 没有连接上服务器
     */
    public static final HttpResult timeOutErrorResult = new HttpResult(-2, "没有连接上服务器");
    /**
     * JSON解析异常
     */
    public static final HttpResult paseErrorResult = new HttpResult(-3, "JSON解析异常");

    public boolean isSucc() {
        return resultCode == 0;
    }
}
