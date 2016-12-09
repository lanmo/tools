package com.yn.tools.redis;

/**
 * Created by yangnan on 2016/12/9.
 */
public class Redis {

    private String host;
    private int port;

    public Redis(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
