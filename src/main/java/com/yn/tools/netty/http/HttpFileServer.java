package com.yn.tools.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by yangnan on 16/12/13.
 * netty 实现的http服务器
 */
public class HttpFileServer {

    private static final String DEFAULT_URL = "/src/main/java/com/yn/tools/";

    public void run(final int port, final String url) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class);
        } finally {

        }
    }
}
