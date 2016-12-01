package com.yn.tools.disruptor;

/**
 * Created by yangnan on 2016/11/22.
 */
public class Element {

    private long value;
    private long currentTime;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }
}
