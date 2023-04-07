package com.muchenlou.phototovideo.service;

import com.muchenlou.phototovideo.model.UserDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.muchenlou.phototovideo.model.VideoDetailsDO;
import com.muchenlou.phototovideo.request.CreateOrGetKeyRequest;
import com.muchenlou.phototovideo.response.CreateOrGetApiKey;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author muchenlou
 * @since 2023-04-07
 */
public interface UserService{

    CreateOrGetApiKey createOrGetKey(CreateOrGetKeyRequest createOrGetKeyRequest);

    boolean checkApiKey(String apiKey);

    UserDO selectByApiKey(String apiKey);
}
