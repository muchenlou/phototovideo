package com.muchenlou.phototovideo.async;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.muchenlou.phototovideo.mapper.VideoDetailsMapper;
import com.muchenlou.phototovideo.model.ModelsPrintsDO;
import com.muchenlou.phototovideo.model.VideoDetailsDO;
import com.muchenlou.phototovideo.response.EventData;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.concurrent.Semaphore;

/**
 * @Author Baojian Hong
 * @Date 2023/4/7 14:19
 * @Version 1.0
 */

@Component
@Log4j2
public class AIModelsAsync {

    @Value("${resultDir}")
    private String resultDir;


    @Value("${localhostApi}")
    private String localhostApi;


    @Value("${modelUrl}")
    private String modelUrl;


    @Value("${generateVideoUrl}")
    private String generateVideoUrl;

    // 创建一个信号量，限制同时处理的请求数量为5
    private static final Semaphore semaphore = new Semaphore(1);

    @Autowired
    private VideoDetailsMapper videoDetailsMapper;

    @Async
    public void submitTaskByUrl(String uuid, String imageUrl, ModelsPrintsDO modelsPrintsDO) {
        try {
            // 获取一个许可，如果无法获取则等待
            semaphore.acquire();
            VideoDetailsDO videoDetailsDO = new VideoDetailsDO();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("driven_audio", modelsPrintsDO.getPath());
            jsonObject.put("source_image", imageUrl);
            jsonObject.put("enhancer", "gfpgan");
            jsonObject.put("result_dir", resultDir);
            jsonObject.put("uuid", uuid);
            String entity = JSONObject.toJSONString(jsonObject);

            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            HttpEntity<String> request = new HttpEntity<>(entity, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(generateVideoUrl, HttpMethod.POST, request, String.class);
            if (response.getStatusCode().value() != 200){
                videoDetailsDO.setStatus(2);
                videoDetailsMapper.update(videoDetailsDO,new UpdateWrapper<VideoDetailsDO>().eq("uuid",uuid));
                //  异步发送回调
                asyncHttpRequest(uuid,null,2);
            }else{
                videoDetailsDO.setStatus(1);
                JSONObject body = JSONObject.parseObject(response.getBody());
                String[] parts  = body.get("video_url").toString().split("/");
                String url = localhostApi+parts[parts.length-2]+"/"+parts[parts.length-1];
                videoDetailsDO.setVideoUrl(url);
                videoDetailsDO.setVideoCreateTime(new Date());
                videoDetailsMapper.update(videoDetailsDO,new UpdateWrapper<VideoDetailsDO>().eq("uuid",uuid));
                //  异步发送回调
                asyncHttpRequest(uuid,url,1);
            }
        } catch (Exception e) {
            VideoDetailsDO videoDetailsDO = new VideoDetailsDO();
            videoDetailsDO.setStatus(2);
            videoDetailsMapper.update(videoDetailsDO,new UpdateWrapper<VideoDetailsDO>().eq("uuid",uuid));
            log.error(e.getMessage());
        } finally {
            // 完成处理后，释放许可
            semaphore.release();
        }
    }

    @Async
    public void asyncHttpRequest(String uuid,String resultUrl, Integer status){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("eventName", "EVENT_FACE_DRIVEN_TASK_FINISHED");
        EventData eventData = new EventData();
        eventData.setStatus(status);
        eventData.setResultUrl(resultUrl);
        eventData.setTaskId(uuid);
        jsonObject.put("eventData", eventData);
        String entity = JSONObject.toJSONString(jsonObject);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> request = new HttpEntity<>(entity, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(modelUrl, HttpMethod.POST, request, String.class);
    }

}
