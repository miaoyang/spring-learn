package com.ym.learn.core.api;

/**
 * @Author: Yangmiao
 * @Date: 2023/4/9 15:33
 * @Desc:
 */
public interface IErrorCode {
    /**
     * 返回错误码
     * @return
     */
    int getCode();

    /**
     * 返回错误信息
     * @return
     */
    String getMessage();
}
