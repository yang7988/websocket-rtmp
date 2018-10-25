package com.yang.rtmp.web.util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class HttpUtils {
    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public static List<String> getRtmpClientStream(String url) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.TEXT_XML);
            HttpEntity httpEntity = new HttpEntity(httpHeaders);
            ResponseEntity<String> response = restTemplate.getForEntity("http://183.47.216.134/stat", String.class, httpEntity);
            if (response == null || response.getStatusCode() != HttpStatus.OK) {
                return null;
            }
            List<String> streams = new ArrayList<>();
            String xml = response.getBody();
            JSONObject jsonObject = XML.toJSONObject(xml);
            JSONObject rtmp = (JSONObject) jsonObject.get("rtmp");
            JSONObject server = (JSONObject) rtmp.get("server");
            JSONObject application = (JSONObject) server.get("application");
            JSONObject live = (JSONObject) application.get("live");
            Object object = live.get("stream");
            if (object instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) object;
                for (Object obj : jsonArray) {
                    JSONObject stream = (JSONObject) obj;
                    String name = (String) stream.get("name");
                    streams.add(name);
                }
                return streams;
            }
            JSONObject stream = (JSONObject) object;
            String name = (String) stream.get("name");
            streams.add(name);
            return streams;
        } catch (Exception e) {
            logger.error("获取rtmp客户端推流数据出错 ", e);
        }
        return null;
    }

    public static void main(String[] args) {
        List<String> streams = getRtmpClientStream("");
        streams.forEach(System.out::println);
    }
}