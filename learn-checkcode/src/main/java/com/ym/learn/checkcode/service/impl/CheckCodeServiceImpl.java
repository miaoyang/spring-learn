package com.ym.learn.checkcode.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import com.ym.learn.checkcode.domain.CodeVo;
import com.ym.learn.checkcode.service.CheckCodeService;
import com.ym.learn.checkcode.util.CodeUtil;
import com.ym.learn.core.utils.SignUtil;
import com.ym.learn.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @Author: Yangmiao
 * @Date: 2023/4/21 17:33
 * @Desc:
 */
@Service
@Slf4j
public class CheckCodeServiceImpl implements CheckCodeService {
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private RedisService redisService;

    @Override
    public boolean verifyCode(String key, String code) {
        if (StrUtil.isEmpty(key) || StrUtil.isEmpty(code)){
            return false;
        }
        String localCode = (String)redisService.get(key);
        if (StrUtil.isEmpty(localCode)){
            return false;
        }
        if (!code.equalsIgnoreCase(localCode)){
            return false;
        }
        // 删除缓存
        redisService.del(key);
        return true;
    }

    @Override
    public CodeVo generateCode(Integer length, String prefixKey, Integer expireTime) {
        String code = CodeUtil.generateCode(length);
        String key = CodeUtil.generateKey(prefixKey);
        // 缓存redis
        redisService.set(key,code,expireTime);
        // 获取base64编码后的img
        String codeImg = generateCodeImg(code);
        return CodeVo.builder()
                .key(key)
                .aliasing(codeImg)
                .build();
    }

    /**
     * 生成codeImage
     * @param code
     * @return
     */
    private String generateCodeImg(String code){
        BufferedImage bufferedImage = defaultKaptcha.createImage(code);
        ByteOutputStream byteOutputStream = null;
        String codeImg = "";
        try {
            byteOutputStream = new ByteOutputStream();
            ImageIO.write(bufferedImage,"png",byteOutputStream);
            codeImg = "data:image/png;base64,"+SignUtil.encodeBase64(byteOutputStream.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (byteOutputStream != null) {
                byteOutputStream.close();
            }
        }
        return codeImg;
    }
}
