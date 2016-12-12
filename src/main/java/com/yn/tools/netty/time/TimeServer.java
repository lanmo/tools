package com.yn.tools.netty.time;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * Created by yangnan on 16/12/1.
 * netty 时间服务器服务端
 */
public class TimeServer {
    public void bind(int port) {
        //配置服务端的NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(
                    ChannelOption.SO_BACKLOG, 1024
            ).childHandler(new ChildChannelHandler<SocketChannel>());

            //绑定端口，同步等待成功
            ChannelFuture future = bootstrap.bind(port).sync();
            //等待服务端监听端口关闭
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //优雅退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler<S extends Channel> extends ChannelInitializer<SocketChannel> {

        protected void initChannel(SocketChannel socketChannel) throws Exception {
            ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
//            socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(1024));
            socketChannel.pipeline().addLast(new StringDecoder());
            socketChannel.pipeline().addLast(new TimeServerHandler());
        }
    }

    public static void main(String[] args) {
        int port = 8080;
        new TimeServer().bind(port);
    }
}
