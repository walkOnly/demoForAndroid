package com.hhxc.demo.event;

public class DownloadProgressEvent {

    private int percent;

    public DownloadProgressEvent(int percent) {
        this.percent = percent;
    }

    public int getPercent() {
        return percent;
    }

}
