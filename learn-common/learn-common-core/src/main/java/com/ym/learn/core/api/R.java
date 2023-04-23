package com.ym.learn.core.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: Yangmiao
 * @Date: 2023/3/11 12:57
 * @Desc: 基础信息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class R<T> implements Serializable {
    public static final long serialVersionUID = 1L;

    private T data;

    private int code;

    private String msg;

    public static final String MSG_SUCCESS = "success";
    public static final String MSG_FAIL = "服务开了一会儿小差，请稍后重试";
    public static final int SUCCESS = 200;
    public static final int FAIL = 500;

    public static R ok(){
        return R(SUCCESS,null,MSG_SUCCESS);
    }

    public static <T> R ok(T data){
        return R(SUCCESS,data,MSG_SUCCESS);
    }

    public static <T> R fail(){
        return R(FAIL,null,MSG_FAIL);
    }

    public static <T> R fail(String msg){
        return R(FAIL,null,msg);
    }

    public static <T> R fail(int code,String msg){
        return R(code,null,msg);
    }

    /**
     * 参数校验失败返回结果
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> R validateFailed(String msg){
        return R(ResultCode.VALIDATE_FAILED.getCode(), null,msg);
    }

    /**
     * 未登录返回结果
     * @param data
     * @param <T>
     * @return
     */
    public static <T> R unauthorized(T data){
        return R(ResultCode.UNAUTHORIZED.getCode(), data,ResultCode.UNAUTHORIZED.getMessage());
    }

    /**
     * 未授权返回结果
     * @param data
     * @param <T>
     * @return
     */
    public static <T> R forbidden(T data){
        return R(ResultCode.FORBIDDEN.getCode(),data,ResultCode.FORBIDDEN.getMessage());
    }

    public static <T> R R(int code, T data, String msg){
        return R.builder()
                .code(code)
                .data(data)
                .msg(msg)
                .build();
    }
}
