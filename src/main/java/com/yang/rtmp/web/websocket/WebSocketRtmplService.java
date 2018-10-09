package com.yang.rtmp.web.websocket;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class WebSocketRtmplService {

    @PostConstruct
    public void init() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new WebSocketRunnable());
        executorService.submit(new UdpPullRunable());
    }

}