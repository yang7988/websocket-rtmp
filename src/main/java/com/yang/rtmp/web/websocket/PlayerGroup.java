package com.yang.rtmp.web.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.util.concurrent.ImmediateEventExecutor;

public class PlayerGroup {
    private static ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);

    public static void addChannel(Channel channel) {
        channelGroup.add(channel);
    }

    public static void removeChannel(Channel channel) {
        channelGroup.remove(channel);
    }

    public static void broadCast(ByteBuf message) {
        if (channelGroup == null || channelGroup.isEmpty()) {
            return;
        }
        BinaryWebSocketFrame frame = new BinaryWebSocketFrame(message);
        message.retain();
        channelGroup.writeAndFlush(frame);
    }

    public static void destory() {
        if (channelGroup == null || channelGroup.isEmpty()) {
            return;
        }
        channelGroup.close();
    }

}