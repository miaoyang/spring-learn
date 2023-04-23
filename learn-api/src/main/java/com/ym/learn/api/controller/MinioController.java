package com.ym.learn.api.controller;

import com.ym.learn.core.api.R;
import com.ym.learn.minio.config.MinioConfig;
import com.ym.learn.minio.service.MinioService;
import io.minio.messages.Bucket;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: Yangmiao
 * @Date: 2023/4/22 23:00
 * @Desc:
 */
@Api(tags = "minio接口")
@RestController
@RequestMapping("/file")
@Slf4j
public class MinioController {
    @Autowired
    private MinioService minioService;
    @Autowired
    private MinioConfig minioConfig;

    @ApiOperation(value = "判断bucket是否存在")
    @GetMapping("/bucketExist")
    public R bucketExist(@RequestParam("bucketName")String bucketName){
        boolean exists = minioService.bucketExists(bucketName);
        return R.ok(exists);
    }

    @ApiOperation(value = "创建bucket")
    @GetMapping("/createBucket")
    public R createBucket(String bucketName){
        boolean ret = minioService.createBucket(bucketName);
        return R.ok(ret);
    }

    @ApiOperation(value = "删除bucket")
    @GetMapping("/removeBucket")
    public R removeBucket(String bucketName){
        boolean removeBucket = minioService.removeBucket(bucketName);
        return R.ok(removeBucket);
    }

    @ApiOperation(value = "获取所有的bucket")
    @GetMapping("/getAllBucket")
    public R getAllBucket(){
        List<Bucket> allBuckets = minioService.getAllBuckets();
        return R.ok(allBuckets);
    }

    @ApiOperation(value = "上传文件")
    @PostMapping("/uploadFile")
    public R uploadFile(@RequestParam("file") MultipartFile file){
        String upload = minioService.upload(file);
        return R.ok(upload);
    }

    @ApiOperation(value = "查看Image")
    @GetMapping("/preview")
    public R preview(String fileName){
        String preview = minioService.preview(fileName);
        return R.ok(preview);
    }

    @ApiOperation(value = "下载文件")
    @GetMapping("/downloadFile")
    public R downloadFile(String fileName, HttpServletResponse response){
        minioService.download(fileName,response);
        return R.ok();
    }

    @ApiOperation(value = "删除文件")
    @PostMapping("/removeFile")
    public R removeFile(String url){
        String objName = url.substring(url.lastIndexOf(minioConfig.getBucketName()+"/") + minioConfig.getBucketName().length()+1);
        boolean remove = minioService.remove(objName);
        return R.ok(remove);
    }

}
