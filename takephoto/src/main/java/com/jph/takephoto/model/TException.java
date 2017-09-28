package com.jph.takephoto.model;

/**
 * Create by Sprout at 2017/7/17
 */
public class TException extends Exception {
    String detailMessage;

    public TException(TExceptionType exceptionType) {
        super(exceptionType.getStringValue());
        this.detailMessage = exceptionType.getStringValue();
    }

    public String getDetailMessage() {
        return detailMessage;
    }
}
