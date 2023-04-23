package com.ym.learn.checkcode.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @Author: Yangmiao
 * @Date: 2023/4/21 16:25
 * @Desc: 验证码配置
 */
@Configuration
public class KaptchaConfig {
    @Bean
    public DefaultKaptcha defaultKaptcha() {
        Properties properties = new Properties();
        properties.put("kaptcha.border", "no");

        // 文本宽度和长度
        properties.put("kaptcha.textproducer.char.space", "10");
        properties.put("kaptcha.textproducer.char.length","4");
        // 宽度和高度
        properties.put("kaptcha.image.height","34");
        properties.put("kaptcha.image.width","130");
        // 字体大小和颜色
        properties.put("kaptcha.textproducer.font.size","25");
        properties.put("kaptcha.textproducer.font.color", "black");
        // 背景
        properties.setProperty("kaptcha.background.clear.from", "white");
        properties.setProperty("kaptcha.background.clear.to", "white");

        properties.put("kaptcha.noise.impl","com.google.code.kaptcha.impl.NoNoise");

        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
