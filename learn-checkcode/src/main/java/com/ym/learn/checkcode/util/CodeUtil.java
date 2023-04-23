package com.ym.learn.checkcode.util;

import java.util.Random;
import java.util.UUID;

/**
 * @Author: Yangmiao
 * @Date: 2023/4/21 17:05
 * @Desc: 验证码工具类
 */
public class CodeUtil {
    /**
     * Code字符的取值范围
     */
    public static final String CODE_NUM = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    /**
     * 生成UUID，包括前缀
     * @param prefix
     * @return
     */
    public static String generateKey(String prefix){
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return prefix+uuid;
    }

    /**
     * 生成指定长度的code
     * @param len 不指定时，默认为4
     * @return
     */
    public static String generateCode(Integer len){
        if (len <= 0){
            len = 4;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            Random random = new Random();
            int nextInt = random.nextInt(CODE_NUM.length());
            sb.append(CODE_NUM.charAt(nextInt));
        }
        return sb.toString();
    }

}
