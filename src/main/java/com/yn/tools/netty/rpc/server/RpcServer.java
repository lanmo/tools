package com.yn.tools.netty.rpc.server;

import com.yn.tools.netty.rpc.annotation.RpcService;
import com.yn.tools.netty.rpc.exception.RpcException;
import com.yn.tools.netty.rpc.model.Request;
import com.yn.tools.netty.rpc.model.Response;
import com.yn.tools.netty.rpc.serialize.RpcDecoder;
import com.yn.tools.netty.rpc.serialize.RpcEncoder;
import com.yn.tools.netty.rpc.server.handler.RpcHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangnan on 2016/12/14.
 */
public class RpcServer {

    private String serverAddress;

    private String className = "classpath*:**/*.class";

    //存放接口名与服务对象的映射关系
    private Map<String, Object> handlerMap = new HashMap<String, Object>();

    public RpcServer(String serverAddress) {
        this.serverAddress = serverAddress;

        initHandlerMap();
    }

    private void initHandlerMap() {
        try {
            ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
            Resource[] resources = resourcePatternResolver.getResources(className);
            for (Resource resource : resources) {
                MetadataReader reader = metadataReaderFactory.getMetadataReader(resource);
                AnnotationMetadata metadata = reader.getAnnotationMetadata();
                if (metadata.hasAnnotation(RpcService.class.getName())) {
                    ClassMetadata m = reader.getClassMetadata();
                    Class<?> cz = Class.forName(m.getClassName());
                    RpcService rpcService = cz.getAnnotation(RpcService.class);
                    String interfaceName = rpcService.value().getSimpleName();
                    interfaceName = Character.toLowerCase(interfaceName.charAt(0)) + interfaceName.substring(1);
                    handlerMap.put(interfaceName, cz);
                }
            }
        } catch (Exception e) {
            throw new RpcException(e);
        }
    }

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
                     .childHandler(new ChildChannelHandler<SocketChannel>()).option(ChannelOption.SO_BACKLOG, 1024)
                        .childOption(ChannelOption.SO_KEEPALIVE, true);

            String[] host  = serverAddress.split(":");
            int port = Integer.parseInt(host[1]);
            ChannelFuture future = bootstrap.bind(host[0], port);
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            throw new RpcException(e);
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler<S extends SocketChannel> extends ChannelInitializer<SocketChannel> {

        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new RpcDecoder(Request.class));//将 RPC 请求进行解码（为了处理请求）
            socketChannel.pipeline().addLast(new RpcEncoder(Response.class));//将 RPC 响应进行编码（为了处理响应）
            socketChannel.pipeline().addLast(new RpcHandler());//处理RPC请求
        }
    }

}
