package com.muchenlou.phototovideo.async;

import com.alibaba.fastjson.JSONObject;
import com.muchenlou.phototovideo.model.ModelsPrintsDO;
import com.muchenlou.phototovideo.request.ModelHandlingRequest;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @Author Baojian Hong
 * @Date 2023/4/7 14:19
 * @Version 1.0
 */

@Component
public class AIModelsAsync {

    @Async
    public void submitTaskByUrl(String jobId, String imageUrl, Integer modelsId, ModelsPrintsDO modelsPrintsDO) {
        ModelHandlingRequest modelHandlingRequest = new ModelHandlingRequest();
        modelHandlingRequest.setVideoUrl("111");
        modelHandlingRequest.setStatus(1);
        modelHandlingRequest.setVideoName("112");
        modelHandlingRequest.setJobId(modelsId.toString());
        String entity = JSONObject.toJSONString(modelHandlingRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> request = new HttpEntity<>(entity,headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange("http://127.0.0.1:9005/videoDetails/v1/modelHandling", HttpMethod.POST, request, String.class);

        System.out.println("进入远程调用了");
        // TODO 处理远程调用
    }
}
