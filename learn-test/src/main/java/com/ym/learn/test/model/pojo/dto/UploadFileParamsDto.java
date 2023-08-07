package com.ym.learn.test.model.pojo.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: Yangmiao
 * @Date: 2023/5/31 22:53
 * @Desc:
 */
@Data
@ToString
public class UploadFileParamsDto {
    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件类型（文档，音频，视频）
     */
    private String fileType;
    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 标签
     */
    private String tags;
    /**
     * 上传人
     */
    private String username;
    /**
     * 备注
     */
    private String remark;
}
