package com.muchenlou.phototovideo.request;

import lombok.Data;

/**
 * @Author Baojian Hong
 * @Date 2023/4/7 14:37
 * @Version 1.0
 */

@Data
public class ModelHandlingRequest {
    private String jobId;
    private String videoUrl;
    private String videoName;
    private Integer status;
}
