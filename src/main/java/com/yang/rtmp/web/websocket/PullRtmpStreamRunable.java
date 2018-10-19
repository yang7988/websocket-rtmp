package com.yang.rtmp.web.websocket;

import com.yang.rtmp.web.util.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PullRtmpStreamRunable implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(PullRtmpStreamRunable.class);

    @Override
    public void run() {
        try{
            String catalog = RandomStringUtils.random(6,"abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            String BASH = "ffmpeg -i rtmp://183.47.216.134/live_hd/" + catalog +
                    " -max_muxing_queue_size 1024 -f mpegts -codec:v mpeg1video -s 960x540 -b:v 1500k -r 25 -bf 0 -codec:a mp2 -ar 44100 -ac 1 -b:a 128k udp://183.47.216.134:9094";
            logger.info("rtmp上传目录名: " + catalog);
            logger.info("rtmp协议为: rtmp://183.47.216.134/live_hd/" + catalog);
            logger.info("ffmpeg执行的命令: " + BASH);
            String[] cmd = { "/bin/sh", "-c", BASH };
            Runtime rt = Runtime.getRuntime();
            //执行命令, 最后一个参数，可以使用new File("path")指定运行的命令的位置
            Process proc = rt.exec(cmd);
            InputStream stderr =  proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(stderr,"UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) { // 打印出命令执行的结果
                logger.info(line);
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }

    }
}