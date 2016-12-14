package com.yn.tools.netty.rpc.model;

import java.io.Serializable;

/**
 * Created by yangnan on 2016/12/14.
 * rpc服务请求结构 MessageRequest功能模块
 *
 */
public class Request implements Serializable {

    private String      messageId;
    private String      className;
    private String      methodName;
    private Class<?>    parameterTypes;
    private Object[]    parameters;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?> getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?> parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
