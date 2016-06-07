package com.hhxc.demo.event;

public class OnWxpayEvent {
    private int errorCode;
    private String errorMsg;

    public OnWxpayEvent(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

}
