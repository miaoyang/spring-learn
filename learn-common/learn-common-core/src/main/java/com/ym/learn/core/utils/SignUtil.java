package com.ym.learn.core.utils;

import cn.hutool.core.codec.Base64Decoder;

import java.util.Base64;

/**
 * @Author: Yangmiao
 * @Date: 2023/4/21 19:03
 * @Desc: 加解密工具类
 */
public class SignUtil {
    /**
     * Base64 encoder
     * @param bytes
     * @return
     */
    public static String encodeBase64(byte [] bytes){
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Base64 decoder
     * @param str
     * @return
     */
    public static byte[] decoderBase64(String str){
        return Base64.getDecoder().decode(str);
    }
}
