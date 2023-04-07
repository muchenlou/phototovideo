package com.muchenlou.phototovideo.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author Baojian Hong
 * @Date 2023/4/7 11:12
 * @Version 1.0
 */
@Data
public class CreateOrGetKeyRequest {

    @NotEmpty(message = "外部用户id不能为空")
    private String externalId;
}
