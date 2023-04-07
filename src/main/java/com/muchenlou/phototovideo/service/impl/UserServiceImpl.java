package com.muchenlou.phototovideo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.muchenlou.phototovideo.model.UserDO;
import com.muchenlou.phototovideo.mapper.UserMapper;
import com.muchenlou.phototovideo.request.CreateOrGetKeyRequest;
import com.muchenlou.phototovideo.response.CreateOrGetApiKey;
import com.muchenlou.phototovideo.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.muchenlou.phototovideo.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
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
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    private final String APIKEY = "photoToVideoKey";

    @Override
    public CreateOrGetApiKey createOrGetKey(CreateOrGetKeyRequest createOrGetKeyRequest) {
        UserDO user = userMapper.selectOne(new QueryWrapper<UserDO>().eq("external_id", createOrGetKeyRequest.getExternalId()));
        if (user == null){
            user = new UserDO();
            user.setCreateTime(new Date());
            user.setExternalId(createOrGetKeyRequest.getExternalId());
            user.setUuid(UUID.randomUUID().toString());
            user.setApiKey(MD5Util.encryptMD5(createOrGetKeyRequest.getExternalId(),APIKEY));
            userMapper.insert(user);
        }
        CreateOrGetApiKey createOrGetApiKey = new CreateOrGetApiKey();
        createOrGetApiKey.setApiKey(user.getApiKey());
        return createOrGetApiKey;
    }

    @Override
    public boolean checkApiKey(String apiKey) {
        Integer count = userMapper.selectCount(new QueryWrapper<UserDO>().eq("api_key", apiKey));
        return count != 0;
    }

    @Override
    public UserDO selectByApiKey(String apiKey) {
        return userMapper.selectOne(new QueryWrapper<UserDO>().eq("api_key", apiKey));
    }

}
