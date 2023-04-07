package com.muchenlou.phototovideo.service;

import com.muchenlou.phototovideo.model.ModelsPrintsDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.muchenlou.phototovideo.model.VideoDetailsDO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author muchenlou
 * @since 2023-04-07
 */
public interface ModelsPrintsService {

    /**
     * 新建模型积分
     * @param modelsPrintsDO
     * @return
     */
    int add(ModelsPrintsDO modelsPrintsDO);

    ModelsPrintsDO selectOne(Integer modelsId);
}
