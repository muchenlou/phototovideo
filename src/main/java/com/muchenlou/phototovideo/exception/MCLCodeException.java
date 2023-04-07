package com.muchenlou.phototovideo.exception;


import lombok.Data;

/**
 * @Author Baojian Hong
 * @Date 2023/4/7 13:55
 * @Version 1.0
 */
@Data
public class MCLCodeException extends RuntimeException{
    private Integer code;
    private String message;

    public MCLCodeException(Integer code, String message){
        super(message);
        this.code = code;
        this.message = message;
    }
}
