package com.yang.rtmp.web.websocket.rtmp;

import com.yang.rtmp.web.websocket.web.WebSocketRunnable;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UdpPullService {

    private static final Logger logger = LoggerFactory.getLogger(UdpPullService.class);

    @PostConstruct
    public void init() {
        Thread thread = new Thread(new WebSocketRunnable());
        thread.start();
        //udp pull
        int port = 9094;
        EventLoopGroup bossLoop = null;
        try {
            bossLoop = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioDatagramChannel.class);
            bootstrap.group(bossLoop).option(ChannelOption.SO_BROADCAST, true)
                    .option(ChannelOption.SO_SNDBUF, 1024 * 256).option(ChannelOption.SO_RCVBUF, 1024 * 256);
            bootstrap.handler(new UdpMpegTsHandler());
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