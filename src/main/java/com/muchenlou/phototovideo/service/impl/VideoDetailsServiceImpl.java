package com.muchenlou.phototovideo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.muchenlou.phototovideo.async.AIModelsAsync;
import com.muchenlou.phototovideo.exception.MCLCodeException;
import com.muchenlou.phototovideo.mapper.ModelsPrintsMapper;
import com.muchenlou.phototovideo.model.ModelsPrintsDO;
import com.muchenlou.phototovideo.model.UserDO;
import com.muchenlou.phototovideo.model.VideoDetailsDO;
import com.muchenlou.phototovideo.mapper.VideoDetailsMapper;
import com.muchenlou.phototovideo.request.ModelHandlingRequest;
import com.muchenlou.phototovideo.response.EventData;
import com.muchenlou.phototovideo.service.ModelsPrintsService;
import com.muchenlou.phototovideo.service.UserService;
import com.muchenlou.phototovideo.service.VideoDetailsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author muchenlou
 * @since 2023-04-07
 */
@Service
@Log4j2
public class VideoDetailsServiceImpl implements VideoDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private VideoDetailsMapper videoDetailsMapper;

    @Autowired
    private ModelsPrintsService modelsPrintsService;

    @Autowired
    private AIModelsAsync aiModelsAsync;

    @Value("${modelUrl}")
    private String modelUrl;



    @Override
    public String submitTaskByUrl(String apiKey, String imageUrl, Integer modelsId) {
        // 校验apiKey
        if (!checkApiKey(apiKey)){
            throw new MCLCodeException(401,"用户无权限调用接口");
        }

        // 校验模型编号
        ModelsPrintsDO modelsPrintsDO = modelsPrintsService.selectOne(modelsId);

        UserDO userDO = userService.selectByApiKey(apiKey);
        // 创建提交记录
        VideoDetailsDO videoDetailsDO = new VideoDetailsDO();
        videoDetailsDO.setUuid(UUID.randomUUID().toString());
        videoDetailsDO.setUserId(userDO.getId());
        videoDetailsDO.setImageUrl(imageUrl);
        videoDetailsDO.setModelsId(modelsId);
        videoDetailsDO.setStatus(0);
        videoDetailsDO.setCreateTime(new Date());
        videoDetailsMapper.insert(videoDetailsDO);
        // 启动异步加载
        aiModelsAsync.submitTaskByUrl(videoDetailsDO.getUuid(),imageUrl,modelsId,modelsPrintsDO);
        System.out.println("看看谁先");
        return videoDetailsDO.getUuid();
    }

    @Override
    public void modelHandling(ModelHandlingRequest modelHandlingRequest) {
        VideoDetailsDO videoDetailsDO = new VideoDetailsDO();
        if (modelHandlingRequest.getStatus() == 2){
            videoDetailsDO.setStatus(2);
        }else{
            videoDetailsDO.setStatus(1);
            videoDetailsDO.setVideoUrl(modelHandlingRequest.getVideoUrl());
            videoDetailsDO.setVideoName(modelHandlingRequest.getVideoName());
        }
        videoDetailsMapper.update(videoDetailsDO,new UpdateWrapper<VideoDetailsDO>().eq("uuid",modelHandlingRequest.getJobId()));
        EventData eventData = new EventData();
        eventData.setStatus(modelHandlingRequest.getStatus());
        eventData.setResultUrl(modelHandlingRequest.getVideoUrl());
        eventData.setTaskId(modelHandlingRequest.getJobId());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("eventName","EVENT_FACE_DRIVEN_TASK_FINISHED");
        jsonObject.put("eventData",eventData);
        String entity = JSONObject.toJSONString(jsonObject);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> request = new HttpEntity<>(entity,headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(modelUrl, HttpMethod.POST, request, String.class);
        log.error("请求成功");
    }

    @Override
    public EventData getTaskInfo(String apiKey, String taskId) {
        if (!checkApiKey(apiKey)){
            throw new MCLCodeException(401,"用户无权限调用接口");
        }
        EventData eventData = new EventData();
        VideoDetailsDO videoDetailsDO = videoDetailsMapper.selectOne(new QueryWrapper<VideoDetailsDO>().eq("uuid", taskId));
        if (videoDetailsDO == null){
            throw new MCLCodeException(404,"未找到相关的数据,请重新确认");
        }
        eventData.setTaskId(taskId);
        eventData.setResultUrl(videoDetailsDO.getVideoUrl());
        eventData.setStatus(videoDetailsDO.getStatus());
        return eventData;
    }

    private boolean checkApiKey(String apiKey) {
        return userService.checkApiKey(apiKey);
    }
}
