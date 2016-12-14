package com.yn.tools.netty.rpc.service.impl;

import com.yn.tools.netty.rpc.annotation.RpcService;
import com.yn.tools.netty.rpc.service.HelloService;

/**
 * Created by yangnan on 2016/12/14.
 */
@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService {
    public String hello(String name) {
        String response = "I am HelloServiceImpl Server";
        System.out.println("server:" + name);
        return response + name;
    }
}
