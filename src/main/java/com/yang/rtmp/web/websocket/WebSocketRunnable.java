package com.yang.rtmp.web.websocket;

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
    private int port;
    private String path;
    private String subprotocols;

    public WebSocketRunnable(int port, String path, String subprotocols) {
        this.port = port;
        this.path = path;
        this.subprotocols = subprotocols;
    }

    public void run() {
        boolean allowExtensions = true;
        EventLoopGroup workGroupLoop = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(workGroupLoop)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            // 长时间不写会断
                            ch.pipeline().addLast("readTimeout", new ReadTimeoutHandler(45));
                            ch.pipeline().addLast("HttpServerCodec", new HttpServerCodec());
                            ch.pipeline().addLast("ChunkedWriter", new ChunkedWriteHandler());
                            ch.pipeline().addLast("HttpAggregator", new HttpObjectAggregator(65535));
                            ch.pipeline().addLast("WsProtocolHandler", new WebSocketServerProtocolHandler(path, subprotocols, allowExtensions));
                            // ws解码成字节
                            ch.pipeline().addLast("WsBinaryDecoder", new WebSocketFrameDecoder());
                            // 字节编码成ws
                            ch.pipeline().addLast("WsEncoder", new WebSocketFramePrepender());
                            ch.pipeline().addLast(new VideoPlayerHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            if (channelFuture.isSuccess()) {
                logger.info("websocket server start at port: " + port + ".");
            }
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        } finally {
            workGroupLoop.shutdownGracefully();
        }
    }

}