package com.yn.tools.netty.rpc.serialize;

/**
 * Created by yangnan on 2016/12/14.
 * 序列化  接口
 */
public interface Serialize {

    byte[] encode(Object data);

    Object decode(byte[] bytes);
}
