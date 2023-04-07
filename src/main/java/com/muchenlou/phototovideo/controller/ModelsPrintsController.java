package com.muchenlou.phototovideo.controller;


import com.muchenlou.phototovideo.model.ModelsPrintsDO;
import com.muchenlou.phototovideo.service.ModelsPrintsService;
import com.muchenlou.phototovideo.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author muchenlou
 * @since 2023-04-07
 */
@RestController
@RequestMapping("/modelsPrints")
public class ModelsPrintsController {

    @Autowired
    private ModelsPrintsService modelsPrintsService;

    @PostMapping("/v1/add")
    public JsonData add(@RequestBody ModelsPrintsDO modelsPrintsDO){
        modelsPrintsService.add(modelsPrintsDO);
        return JsonData.buildSuccess();
    }

}

