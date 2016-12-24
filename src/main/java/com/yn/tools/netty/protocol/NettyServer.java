package com.yn.tools.netty.protocol;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * Created by yangnan on 16/12/24.
 */
public class NettyServer {

    //配置服务端的IO现场组
    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workGroup = new NioEventLoopGroup();

    public void bind() throws Exception {

        try {

            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChildChannelInitializer());

            //绑定端口,同步等待成功
            ChannelFuture future = b.bind(NettyConstant.REMOTEIP, NettyConstant.PORT).sync();
            System.out.println("Netty server start ok : " + (NettyConstant.REMOTEIP + ":" + NettyConstant.PORT));
            //等待服务端监听端口关闭
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new NettyServer().bind();
    }


    class ChildChannelInitializer extends ChannelInitializer<SocketChannel> {

        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(new NettyMessageDecoder(1024 * 1024 * 1024, 4, 4));
            pipeline.addLast(new NettyMessageEncoder());
            pipeline.addLast("readTimeoutHandler", new ReadTimeoutHandler(50));
//            pipeline.addLast(new LoginAuthRespHandler());
            pipeline.addLast("HeartBeatHandler", new HeartBeatRespHandler());
        }
    }
}
