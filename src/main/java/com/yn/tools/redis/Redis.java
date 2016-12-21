package com.yn.tools.redis;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

import java.util.List;

/**
 * Created by yangnan on 2016/12/9.
 */
public class Redis {

    @ElementList(entry = "host", required = true, inline = true)
    private List<Host> hosts;

    @Attribute(name = "auth", required = true)
    private String auth;

    @Attribute(name = "timeout", required = true)
    private int timeout;

    public List<Host> getHosts() {
        return hosts;
    }

    public void setHosts(List<Host> hosts) {
        this.hosts = hosts;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
