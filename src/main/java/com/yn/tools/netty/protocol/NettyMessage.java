package com.yn.tools.netty.protocol;

/**
 * Created by yangnan on 16/12/19.
 */
public class NettyMessage {

    private Header header;      //消息头
    private Object body;        //消息体

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "NettyMessage{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
