package com.yang.rtmp.web.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.yang.rtmp.web.websocket","com.yang.rtmp.web.controller"})
public class WebsocketRtmpApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {

        SpringApplication.run(WebsocketRtmpApplication.class, args);

    }
}
