package com.yn.tools.redis;

import org.simpleframework.xml.Attribute;

/**
 * Created by yangnan on 2016/12/21.
 */
public class Host {

    @Attribute(name = "ip", required = true)
    private String ip;

    @Attribute(name = "port", required = true)
    private int port;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
