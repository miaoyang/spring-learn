package com.ym.learn.test.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ym.learn.minio.service.MinioService;
import com.ym.learn.test.mapper.MediaFilesMapper;
import com.ym.learn.test.model.pojo.MediaFiles;
import com.ym.learn.test.model.pojo.dto.UploadFileParamsDto;
import com.ym.learn.test.model.pojo.vo.UploadFileVo;
import com.ym.learn.test.service.MediaFilesService;
import com.ym.learn.test.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDateTime;

/**
 * @Author: Yangmiao
 * @Date: 2023/5/29 22:30
 * @Desc:
 */
@Service
@Slf4j
public class MediaFilesServiceImpl extends ServiceImpl<MediaFilesMapper, MediaFiles> implements MediaFilesService {

    @Autowired
    private MediaFilesService mediaFilesServiceProxy;

    @Autowired
    private MinioService minioService;

    @Override
    public UploadFileVo uploadFile(UploadFileParamsDto uploadFileParamsDto, String localPath, String objectName) {
        String fileName = uploadFileParamsDto.getFileName();
        if (StrUtil.isEmpty(fileName)){
            return null;
        }
        // 获取扩展名
        String extention = fileName.substring(fileName.lastIndexOf("."));
        String mimeType = FileUtil.getMimeType(extention);
        // 生成文件路径名称
        String fileMD5 = FileUtil.getFileMD5(new File(localPath));
        if (StrUtil.isEmpty(objectName)){
            objectName = FileUtil.getDateMD5Name(fileMD5, extention);
        }
        // TODO: bucket media
        String bucket = "media";
        String url = minioService.upload(localPath, objectName, mimeType, bucket);
        log.debug("uploadFile: url=" + url);
        if (StrUtil.isEmpty(url)){
            log.error("uploadFile: url is empty");
            return null;
        }
        MediaFiles mediaFiles = mediaFilesServiceProxy.insertFileToDb(uploadFileParamsDto, fileMD5, bucket, objectName, url);
        UploadFileVo uploadFileVo = new UploadFileVo();
        if (mediaFiles == null){
            log.error("uploadFile: mediaFiles is null");
            return null;
        }
        BeanUtils.copyProperties(mediaFiles, uploadFileVo);
        return uploadFileVo;
    }

    /**
     * 防止事务失效的方式：
     * 1. 单独抽取出方法，放在一个service中，这样会走动态代理
     * 2. 自注入方式
     * 3. 事务编程
     * @param uploadFileParamsDto
     * @param fileMd5
     * @param bucket
     * @param objectName
     * @param url
     * @return
     */
    @Transactional
    @Override
    public MediaFiles insertFileToDb(UploadFileParamsDto uploadFileParamsDto, String fileMd5, String bucket, String objectName, String url) {
        // 查询数据库
        MediaFiles mediaFiles = baseMapper.selectById(fileMd5);
        if (mediaFiles == null){
            mediaFiles  = new MediaFiles();
            mediaFiles.setId(fileMd5);
            mediaFiles.setFileId(fileMd5);
            mediaFiles.setFilename(uploadFileParamsDto.getFileName());
            mediaFiles.setFileSize(uploadFileParamsDto.getFileSize());
            mediaFiles.setFilePath(objectName);
            mediaFiles.setBucket(bucket);
            mediaFiles.setCreateDate(LocalDateTime.now());
            mediaFiles.setUrl(url);
            int insert = baseMapper.insert(mediaFiles);
            if (insert<=0){
                log.error("insertFileToDb failed!, objectName:{} ",objectName);
                return null;
            }
        }
        return mediaFiles;
    }
}
