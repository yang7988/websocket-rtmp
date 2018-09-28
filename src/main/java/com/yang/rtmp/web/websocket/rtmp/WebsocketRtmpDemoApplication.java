package com.yang.rtmp.web.websocket.rtmp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.yang.rtmp.web.websocket")
public class WebsocketRtmpDemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(WebsocketRtmpDemoApplication.class, args);

    }
}
