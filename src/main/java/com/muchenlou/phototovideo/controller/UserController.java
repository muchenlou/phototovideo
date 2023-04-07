package com.muchenlou.phototovideo.controller;


import com.muchenlou.phototovideo.request.CreateOrGetKeyRequest;
import com.muchenlou.phototovideo.service.UserService;
import com.muchenlou.phototovideo.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author muchenlou
 * @since 2023-04-07
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 根据外部id获取key
     * @param createOrGetKeyRequest
     * @return
     */
    @PostMapping("/v1/createOrGetKey")
    public JsonData createOrGetKey(@Valid @RequestBody CreateOrGetKeyRequest createOrGetKeyRequest){
        return JsonData.buildSuccess(userService.createOrGetKey(createOrGetKeyRequest));
    }
}

