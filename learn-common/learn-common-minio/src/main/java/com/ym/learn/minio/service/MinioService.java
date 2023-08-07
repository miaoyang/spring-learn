package com.ym.learn.minio.service;

import com.ym.learn.minio.config.MinioConfig;
import io.minio.MinioClient;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: Yangmiao
 * @Date: 2023/4/21 23:09
 * @Desc: minio服务
 */
public interface MinioService {
    /**
     * 判断bucket是否存在
     * @param bucketName
     * @return
     */
    boolean bucketExists(String bucketName);

    /**
     * 创建bucket
     * @param bucketName
     * @return
     */
    boolean createBucket(String bucketName);

    /**
     * 删除bucket
     * @param bucketName
     * @return
     */
    boolean removeBucket(String bucketName);

    /**
     * 获取所有的bucket
     * @return
     */
    List<Bucket> getAllBuckets();

    /**
     * 上传文件
     * @param multipartFile
     * @return
     */
    String upload(MultipartFile multipartFile);

    /**
     * 上传文件
     * @param localPath 本地路径
     * @param objectName 对象名称
     * @param mimeType 媒体类型
     * @param bucket 桶名称
     * @return
     */
    String upload(String localPath, String objectName, String mimeType, String bucket);

    /**
     * 下载文件
     * @param fileName
     * @param res
     */
    void download(String fileName, HttpServletResponse res);

    /**
     * 删除文件
     * @param fileName
     * @return
     */
    boolean remove(String fileName);

    /**
     * 预览文件
     * @param fileName
     * @return
     */
    String preview(String fileName);

    /**
     * 查看所有的文件对象
     * @return
     */
    List<Item> getAllFiles();

}
