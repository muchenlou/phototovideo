package com.muchenlou.phototovideo.exception;

import com.alibaba.fastjson.JSON;
import com.muchenlou.phototovideo.utils.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Baojian Hong
 * @Date 2023/4/7 13:55
 * @Version 1.0
 */

@Slf4j
@RestControllerAdvice(basePackages = "com.muchenlou.phototovideo.controller")
public class MCLException {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public JsonData handleVaildException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();

        Map<String,String> errorMap = new HashMap<>();

        for (FieldError f:bindingResult.getFieldErrors()) {
            errorMap.put(f.getField(),f.getDefaultMessage());
        }
        log.error("数据出现错误{}", JSON.toJSONString(errorMap));
        return JsonData.buildError(400, bindingResult.getFieldErrors().get(0).getDefaultMessage());
    }
}
