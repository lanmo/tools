package com.yn.tools.netty.rpc.annotation;

import java.lang.annotation.*;

/**
 * Created by yangnan on 2016/12/14.
 * 定义远程服务接口
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcService {
    Class<?> value();
}
