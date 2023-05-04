package com.ym.learn.checkcode.controller;

import com.ym.learn.checkcode.config.CheckCodeProperties;
import com.ym.learn.checkcode.domain.CodeVo;
import com.ym.learn.checkcode.service.CheckCodeService;
import com.ym.learn.core.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Yangmiao
 * @Date: 2023/4/21 17:29
 * @Desc: 验证码接口
 */
@Api(tags = "验证码接口")
@RestController
@RequestMapping("/checkcode")
public class CheckCodeController {

    @Autowired
    private CheckCodeService checkCodeService;

    @Autowired
    private CheckCodeProperties codeProperties;

    @ApiOperation(value = "获取验证码")
    @GetMapping("/getCheckCode")
    public R getCheckCode(){
        CodeVo codeVo = checkCodeService.generateCode(codeProperties.getLength(), codeProperties.getPrefixKey(), codeProperties.getExpireTime());
        return R.ok(codeVo);
    }

    @ApiOperation(value = "校验验证码")
    @PostMapping("/verifyCheckCode")
    public R verifyCheckCode(@ApiParam(name = "key")@RequestParam("key")String key,
                             @ApiParam(name = "code")@RequestParam("code")String code){
        boolean ret = checkCodeService.verifyCode(key, code);
        return R.ok(ret);
    }

}
