package com.muchenlou.phototovideo.request;

import com.muchenlou.phototovideo.response.EventData;
import lombok.Data;

/**
 * @author Xiaofei Mu
 * @version 1.0
 * @date 2023/4/9 20:25
 */

@Data
public class TestRequest {
    private String eventName;
    private EventData eventData;
}
