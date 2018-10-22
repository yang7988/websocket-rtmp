package com.yang.rtmp.web.websocket;

import org.springframework.beans.factory.annotation.Value;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Value("${udp.port}")
    private Integer udpPort;
    @Value("${websocket.port}")
    private Integer websocketPort;
    @Value("${websocket.path}")
    private String websocketPath;
    @Value("${websocket.subprotocols}")
    private String websocketSubprotocols;
    @Value("${rtmp.server.host}")
    private String rtmpHost;
    @Value("${rtmp.pull.stream.params}")
    private String pullRtmpParams;

    public Integer getUdpPort() {
        return udpPort;
    }

    public void setUdpPort(Integer udpPort) {
        this.udpPort = udpPort;
    }

    public Integer getWebsocketPort() {
        return websocketPort;
    }

    public void setWebsocketPort(Integer websocketPort) {
        this.websocketPort = websocketPort;
    }

    public String getWebsocketPath() {
        return websocketPath;
    }

    public void setWebsocketPath(String websocketPath) {
        this.websocketPath = websocketPath;
    }

    public String getWebsocketSubprotocols() {
        return websocketSubprotocols;
    }

    public void setWebsocketSubprotocols(String websocketSubprotocols) {
        this.websocketSubprotocols = websocketSubprotocols;
    }

    public String getRtmpHost() {
        return rtmpHost;
    }

    public void setRtmpHost(String rtmpHost) {
        this.rtmpHost = rtmpHost;
    }

    public String getPullRtmpParams() {
        return pullRtmpParams;
    }

    public void setPullRtmpParams(String pullRtmpParams) {
        this.pullRtmpParams = pullRtmpParams;
    }
}