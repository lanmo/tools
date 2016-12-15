package com.yn.tools.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import sun.applet.Main;

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
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChildChannelHandler(url));

            ChannelFuture future = b.bind("127.0.0.1", port).sync();
            System.out.println("HTTP 文件目录服务器启动, 网址是 : http://127.0.0.1:" + port + "url");
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;

        String url = DEFAULT_URL;
        new HttpFileServer().run(port, url);
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        private String url;
        public ChildChannelHandler(String url) {
            this.url = url;
        }

        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
            ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
            ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
            ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
            ch.pipeline().addLast("fileServerHandler", new HttpFileServerHandler(url));
        }
    }
}
