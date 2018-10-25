package com.yang.rtmp.web.util;

import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class HttpUtils {
    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
    public static JSONObject getRtmpClientStream(String url) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.TEXT_XML);
            HttpEntity httpEntity = new HttpEntity(httpHeaders);
            ResponseEntity<String> response = restTemplate.getForEntity("http://183.47.216.134/stat", String.class, httpEntity);
            if(response == null || response.getStatusCode() != HttpStatus.OK) {
                return null;
            }
            String xml = response.getBody();
            return   XML.toJSONObject(xml);
        }catch (Exception e) {
           logger.error("获取rtmp客户端推流数据出错 ",e);
        }
        return null;
    }
}