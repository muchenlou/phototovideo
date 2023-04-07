package com.muchenlou.phototovideo.response;

import lombok.Data;

/**
 * @Author Baojian Hong
 * @Date 2023/4/7 14:53
 * @Version 1.0
 */

@Data
public class EventData {

    private String taskId;
    private String resultUrl;
    private Integer status;
}
