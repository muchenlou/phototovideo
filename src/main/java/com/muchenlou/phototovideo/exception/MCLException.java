package com.muchenlou.phototovideo.exception;

import com.alibaba.fastjson.JSON;
import com.muchenlou.phototovideo.utils.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * @Author Baojian Hong
 * @Date 2023/4/7 13:55
 * @Version 1.0
 */

@Slf4j
@ControllerAdvice
public class MCLException {
    @ExceptionHandler(value = MCLCodeException.class)
    @ResponseBody
    public JsonData bizExceptionHandler(HttpServletRequest req, MCLCodeException e){
        log.error("发生业务异常！原因是：{}",e.getMessage());
        return JsonData.buildError(e.getCode(),e.getMessage());
    }

    /**
     * 处理空指针的异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =NullPointerException.class)
    @ResponseBody
    public JsonData exceptionHandler(HttpServletRequest req, NullPointerException e){
        log.error("发生空指针异常！原因是:",e);
        return JsonData.buildError(e.getMessage());
    }


    /**
     * 处理其他异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public JsonData exceptionHandler(HttpServletRequest req, Exception e){
        log.error("未知异常！原因是:",e);
        return JsonData.buildError(500,e.getMessage());
    }
}
