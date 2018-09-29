package com.yang.rtmp.web.websocket.rtmp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.yang.rtmp.web.websocket")
public class WebsocketRtmpDemoApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {

        SpringApplication.run(WebsocketRtmpDemoApplication.class, args);

    }
}
