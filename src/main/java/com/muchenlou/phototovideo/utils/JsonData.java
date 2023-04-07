package com.muchenlou.phototovideo.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Baojian Hong
 * @Date 2023/4/7 10:43
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonData {
    private static final long serialVersionUID = 1L;

    private Integer code;
    private Object data;
    private String msg;
    private Boolean success;

    /**
     * 成功，不传数据
     * @return
     */
    public static JsonData buildSuccess() {
        return new JsonData(0, null, "请求成功",true);
    }


    /**
     * 成功，传入数据
     * @param data
     * @return
     */
    public static JsonData buildSuccess(Object data) {
        return new JsonData(0, data, "请求成功",true);
    }

    /**
     * 成功，传入数据与返回的提示内容
     * @param data
     * @param msg
     * @return
     */
    public static JsonData buildSuccess(Object data,String msg) {
        return new JsonData(0, data, msg,true);
    }

    /**
     * 失败，传入描述信息
     * @param msg
     * @return
     */
    public static JsonData buildError(String msg) {
        return new JsonData(-1, null, msg,false);
    }

    /**
     * 失败，传入描述信息,状态码
     * @param msg
     * @param code
     * @return
     */
    public static JsonData buildError(Integer code, String msg) {
        return new JsonData(code, null, msg,false);
    }

}
