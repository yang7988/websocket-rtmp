package com.yang.rtmp.web.websocket.rtmp;

import com.yang.rtmp.web.websocket.web.UdpPullRunable;
import com.yang.rtmp.web.websocket.web.WebSocketRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class WebSocketRtmplService {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketRtmplService.class);

    @PostConstruct
    public void init() {
        Thread webSocketThread = new Thread(new WebSocketRunnable());
        webSocketThread.start();
        Thread udpPullThread = new Thread(new UdpPullRunable());
        udpPullThread.start();

    }

}