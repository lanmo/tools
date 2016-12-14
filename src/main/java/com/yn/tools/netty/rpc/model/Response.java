package com.yn.tools.netty.rpc.model;

import java.io.Serializable;

/**
 * Created by yangnan on 2016/12/14.
 * Response 服务功能模块
 *
 */
public class Response implements Serializable {

    private String messageId;
    private String error;
    private Object result;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
