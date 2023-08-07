package com.ym.learn.minio.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.ym.learn.minio.config.MinioConfig;
import com.ym.learn.minio.service.MinioService;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Yangmiao
 * @Date: 2023/4/21 23:12
 * @Desc:
 */
@Service
@Slf4j
public class MinioServiceImpl implements MinioService {
    @Autowired
    private MinioConfig minioConfig;
    @Autowired
    private MinioClient minioClient;

    @Override
    public boolean bucketExists(String bucketName) {
        if (StrUtil.isEmpty(bucketName)){
            return false;
        }
        boolean res = false;
        try {
            res = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            log.error("bucketExists error: {}",e.getMessage());
            e.printStackTrace();
            return false;
        }
        return res;
    }

    @Override
    public boolean createBucket(String bucketName) {
        if (StrUtil.isEmpty(bucketName)){
            return false;
        }
        if (this.bucketExists(bucketName)){
            return true;
        }
        try {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }catch (Exception e){
            log.error("createBucket error: {}",e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean removeBucket(String bucketName) {
        if (StrUtil.isEmpty(bucketName)){
            return false;
        }
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        }catch (Exception e){
            log.error("removeBucket error: {}",e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<Bucket> getAllBuckets() {
        try {
            return minioClient.listBuckets();
        }catch (Exception e){
            log.error("getAllBuckets error: {}",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String upload(MultipartFile multipartFile) {
        if (multipartFile == null){
            return null;
        }
        String fileName = multipartFile.getOriginalFilename();
        if (StrUtil.isBlank(fileName)){
            throw new RuntimeException("upload fail!");
        }
        fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+"_"+UUID.randomUUID().toString().replaceAll("-","")+
                fileName.substring(fileName.lastIndexOf("."));
        log.debug("upload: fileName {}",fileName);
        try {
            minioClient.putObject(PutObjectArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .object(fileName)
                            .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1)
                            .contentType(multipartFile.getContentType())
                            .build());
        } catch (Exception e) {
            log.error("upload error: {}",e.getMessage());
            e.printStackTrace();
            return null;
        }
        String url = minioConfig.getEndpoint()+"/"+minioConfig.getBucketName()+"/"+fileName;
        return url;
    }

    @Override
    public String upload(String localPath, String objectName, String mimeType, String bucket) {
        createBucket(bucket);
        try {
           minioClient.uploadObject(UploadObjectArgs.builder()
                    .contentType(mimeType)
                    .object(objectName)
                    .filename(localPath)
                    .bucket(bucket)
                    .build());
           return minioConfig.getEndpoint()+"/"+minioConfig.getBucketName()+"/"+objectName;
        } catch (Exception e) {
            log.error("upload error: {}",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void download(String fileName, HttpServletResponse res) {
        if (StrUtil.isEmpty(fileName)){
            return;
        }
        ByteArrayOutputStream out = null;
        try {
            GetObjectResponse objectResponse = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(fileName)
                    .build());

            out = new ByteArrayOutputStream();
            IoUtil.copy(objectResponse,out);
            res.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            res.setContentLength(out.toByteArray().length);
            res.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            try(ServletOutputStream stream = res.getOutputStream()){
                stream.write(out.toByteArray());
                stream.flush();
            }
        }catch (Exception e){
            log.error("download error: {}",e.getMessage());
            e.printStackTrace();
        }finally {
            if (out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean remove(String fileName) {
        if (StrUtil.isEmpty(fileName)){
            return false;
        }
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(minioConfig.getBucketName()).object(fileName).build());
        }catch (Exception e){
            log.error("remove error: {}",e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String preview(String fileName) {
        if (StrUtil.isEmpty(fileName)){
            return null;
        }
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(fileName)
                    .method(Method.GET)
                    .build());
        }catch (Exception e){
            log.error("preview error: {}",e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Item> getAllFiles() {
        List<Item> itemList = new ArrayList<>();
        try {
            Iterable<Result<Item>> listObjects = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .build());
            try {
                for (Result<Item> item:listObjects){
                    itemList.add(item.get());
                }
            }catch (Exception e){
                log.error("getAllFiles error, get item fail! {}",e.getMessage());
                e.printStackTrace();
                return null;
            }
        }catch (Exception e){
            log.error("getAllFiles error {}",e.getMessage());
            e.printStackTrace();
            return null;
        }
        return itemList;
    }


}
