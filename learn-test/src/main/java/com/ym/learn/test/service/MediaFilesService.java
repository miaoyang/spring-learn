package com.ym.learn.test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ym.learn.test.model.pojo.MediaFiles;
import com.ym.learn.test.model.pojo.dto.UploadFileParamsDto;
import com.ym.learn.test.model.pojo.vo.UploadFileVo;

/**
 * @Author: Yangmiao
 * @Date: 2023/5/29 22:30
 * @Desc:
 */
public interface MediaFilesService extends IService<MediaFiles> {
    /**
     * 上传文件
     * @param uploadFileParamsDto
     * @param localPath
     * @param objectName
     * @return
     */
    UploadFileVo uploadFile(UploadFileParamsDto uploadFileParamsDto, String localPath, String objectName);

    /**
     * 写入文件到数据库
     * @param uploadFileParamsDto
     * @param fileMd5
     * @param bucket
     * @param objectName
     * @return
     */
    MediaFiles insertFileToDb(UploadFileParamsDto uploadFileParamsDto, String fileMd5, String bucket, String objectName, String url);
}
