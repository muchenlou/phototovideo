package com.muchenlou.phototovideo.service;

import com.muchenlou.phototovideo.model.VideoDetailsDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.muchenlou.phototovideo.request.ModelHandlingRequest;
import com.muchenlou.phototovideo.response.EventData;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author muchenlou
 * @since 2023-04-07
 */
public interface VideoDetailsService {

    String submitTaskByUrl(String apiKey, String imageUrl, Integer modelsId);

    void modelHandling(ModelHandlingRequest modelHandlingRequest);

    EventData getTaskInfo(String apiKey, String taskId);
}
