package com.ym.learn.test.controller;

import com.ym.learn.core.api.R;
import com.ym.learn.test.model.pojo.MediaFiles;
import com.ym.learn.test.model.pojo.dto.UploadFileParamsDto;
import com.ym.learn.test.model.pojo.vo.UploadFileVo;
import com.ym.learn.test.service.MediaFilesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Author: Yangmiao
 * @Date: 2023/5/29 19:17
 * @Desc:
 */
@Api(tags = "测试Controller")
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    @Autowired
    private MediaFilesService mediaFilesService;

    @ApiOperation(value = "查看所有的文件")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public R listAll(){
        List<MediaFiles> list = mediaFilesService.list();
        return R.ok(list);
    }

    /**
     * 需求：上传文件，并将文件保存在minio，同时将获取的url存取到数据库文件中
     * @param multipartFile
     * @param objectName
     * @return
     */
    @ApiOperation(value = "上传文件")
    @RequestMapping(value = "/upload/normalfile", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<String> uploadFile(@RequestPart("normalfile") MultipartFile multipartFile,
                                @RequestParam(value = "objectname", required = false) String objectName) {
        if (multipartFile.isEmpty()){
            return R.fail("上传的文件为空！请重新操作");
        }
        File normalfile = null;
        try {
            normalfile = File.createTempFile("normalfile", ".temp");
            multipartFile.transferTo(normalfile);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("创建临时文件失败，{}",e.getMessage());
        }
        if (normalfile == null){
            return R.fail("创建临时文件失败！");
        }

        UploadFileParamsDto uploadFileParamsDto = new UploadFileParamsDto();
        uploadFileParamsDto.setFileName(multipartFile.getOriginalFilename());
        uploadFileParamsDto.setFileSize(multipartFile.getSize());
        uploadFileParamsDto.setFileType(multipartFile.getContentType());
        UploadFileVo uploadFileVo = mediaFilesService.uploadFile(uploadFileParamsDto,normalfile.getAbsolutePath(), objectName);
        if (uploadFileVo == null){
            log.error("上传文件失败！fileName: {}", multipartFile.getName());
            return R.fail("上传文件失败！fileName: "+multipartFile.getName());
        }
        return R.ok(uploadFileVo);
    }
}
