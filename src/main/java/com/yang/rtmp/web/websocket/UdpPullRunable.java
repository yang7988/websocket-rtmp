package com.yang.rtmp.web.websocket;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UdpPullRunable implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(UdpPullRunable.class);
    private int port;

    public UdpPullRunable(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        EventLoopGroup bossLoop = null;
        try {
            bossLoop = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioDatagramChannel.class);
            bootstrap.group(bossLoop)
                     .option(ChannelOption.SO_BROADCAST, true)
                     .option(ChannelOption.SO_SNDBUF, 1024 * 256)
                     .option(ChannelOption.SO_RCVBUF, 1024 * 256)
                     .handler(new UdpMpegTsHandler());
            ChannelFuture future = bootstrap.bind(port).sync();
            if (future.isSuccess()) {
                logger.info("UDP stream server start at port: " + port + ".");
            }
            future.channel().closeFuture().await();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        } finally {
            if (bossLoop != null) {
                bossLoop.shutdownGracefully();
            }
        }
    }
}