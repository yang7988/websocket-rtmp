package com.yang.rtmp.web.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class WebSocketRtmplService {

    @Autowired
    private Configuration configuration;

    @PostConstruct
    public void init() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(new WebSocketRunnable(configuration.getWebsocketPort()
                ,configuration.getWebsocketPath(),configuration.getWebsocketSubprotocols()));
        executorService.submit(new UdpPullRunable(configuration.getUdpPort()));
        executorService.submit(new PullRtmpStreamRunable(configuration.getRtmpHost(),configuration.getPullRtmpParams()));
    }

}