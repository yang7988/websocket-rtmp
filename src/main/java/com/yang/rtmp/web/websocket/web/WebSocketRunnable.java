package com.yang.rtmp.web.websocket.web;

import com.yang.rtmp.web.websocket.rtmp.VideoPlayerHandler;
import com.yang.rtmp.web.websocket.rtmp.WebSocketFrameDecoder;
import com.yang.rtmp.web.websocket.rtmp.WebSocketFramePrepender;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WebSocketRunnable implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketRunnable.class);

    public void run() {
        int webSocketPort = 9092;
        EventLoopGroup workGroupLoop = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(workGroupLoop)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast("readTimeout", new ReadTimeoutHandler(45)); // 长时间不写会断
                            ch.pipeline().addLast("HttpServerCodec", new HttpServerCodec());
                            ch.pipeline().addLast("ChunkedWriter", new ChunkedWriteHandler());
                            ch.pipeline().addLast("HttpAggregator", new HttpObjectAggregator(65535));
                            ch.pipeline().addLast("WsProtocolHandler", new WebSocketServerProtocolHandler("/wsEntry", "haofei", true));
                            ch.pipeline().addLast("WsBinaryDecoder", new WebSocketFrameDecoder()); // ws解码成字节
                            ch.pipeline().addLast("WsEncoder", new WebSocketFramePrepender()); // 字节编码成ws
                            ch.pipeline().addLast(new VideoPlayerHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind(webSocketPort).sync();
            if (channelFuture.isSuccess()) {
                logger.info("websocket server start at port: " + webSocketPort + ".");
            }
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workGroupLoop.shutdownGracefully();
        }
    }

}