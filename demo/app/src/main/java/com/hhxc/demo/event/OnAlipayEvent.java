package com.hhxc.demo.event;

public class OnAlipayEvent {
    private int errorCode;
    private String errorMsg;

    public OnAlipayEvent(int errorCode, String errorMsg) {
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
