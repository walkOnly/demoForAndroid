package com.excs.event;

public class ReloadUrlEvent {

    private String url;

    public ReloadUrlEvent(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

}
