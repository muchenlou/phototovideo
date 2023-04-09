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
        aiModelsAsync.submitTaskByUrl(videoDetailsDO.getUuid(),imageUrl,modelsPrintsDO);
        return videoDetailsDO.getUuid();
    }

    @Override
    public void modelHandling(EventData eventData) {
        log.error(JSONObject.toJSON(eventData));
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
