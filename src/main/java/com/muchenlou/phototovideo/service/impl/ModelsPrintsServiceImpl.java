package com.muchenlou.phototovideo.service.impl;

import com.muchenlou.phototovideo.model.ModelsPrintsDO;
import com.muchenlou.phototovideo.mapper.ModelsPrintsMapper;
import com.muchenlou.phototovideo.service.ModelsPrintsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author muchenlou
 * @since 2023-04-07
 */
@Service
public class ModelsPrintsServiceImpl implements ModelsPrintsService {

    @Autowired
    private ModelsPrintsMapper modelsPrintsMapper;

    @Override
    public int add(ModelsPrintsDO modelsPrintsDO) {
        return modelsPrintsMapper.insert(modelsPrintsDO);
    }

    @Override
    public ModelsPrintsDO selectOne(Integer modelsId) {
        return modelsPrintsMapper.selectById(modelsId);
    }
}
