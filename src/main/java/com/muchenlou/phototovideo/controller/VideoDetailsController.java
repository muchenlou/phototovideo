package com.muchenlou.phototovideo.controller;


import com.muchenlou.phototovideo.request.ModelHandlingRequest;
import com.muchenlou.phototovideo.request.TestRequest;
import com.muchenlou.phototovideo.response.EventData;
import com.muchenlou.phototovideo.service.VideoDetailsService;
import com.muchenlou.phototovideo.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author muchenlou
 * @since 2023-04-07
 */
@RestController
@RequestMapping("/videoDetails")
public class VideoDetailsController {

    @Autowired
    private VideoDetailsService videoDetailsService;

    /**
     * 提交静态图生成表情视频任务
     * @param request
     * @param imageUrl
     * @param modelsId
     * @return
     */
    @GetMapping("/v1/submitTaskByUrl")
    public JsonData submitTaskByUrl(@RequestParam("imageUrl") String imageUrl,@RequestParam("modelsId") Integer modelsId, HttpServletRequest request){
        String apiKey = request.getHeader("APIKEY");
        return JsonData.buildSuccess(videoDetailsService.submitTaskByUrl(apiKey,imageUrl,modelsId));
    }

    /**
     * 异步通知
     * @param eventData
     */
    @PostMapping("/v1/modelHandling")
    public void modelHandling(@RequestBody TestRequest eventData){
        videoDetailsService.modelHandling(eventData.getEventData());
    }

    /**
     * 主动查询的接口
     * @param request
     * @param taskId
     * @return
     */
    @GetMapping("/v1/getTaskInfo")
    public JsonData getTaskInfo(@RequestParam("taskId") String taskId, HttpServletRequest request){
        String apiKey = request.getHeader("APIKEY");
        return JsonData.buildSuccess(videoDetailsService.getTaskInfo(apiKey,taskId));
    }
}

