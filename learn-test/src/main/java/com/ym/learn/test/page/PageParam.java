package com.ym.learn.test.page;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: Yangmiao
 * @Date: 2023/5/29 17:29
 * @Desc: 分页参数
 */
@Data
@ToString
public class PageParam {
    /**
     * 默认分页数
     */
    public static final Long DEFAULT_PAGE_NUM = 1L;
    /**
     * 默认的每页数目
     */
    public static final Long DEFAULT_PAGE_SIZE = 10L;

    private long pageNum;
    private long pageSize;
    private long offset;
    private long limit;

    /**
     * 默认的PageParam
     * @return
     */
    public static PageParam newInstance(){
        return newInstance(DEFAULT_PAGE_NUM,DEFAULT_PAGE_SIZE);
    }

    public static PageParam newInstance(Integer pageNum, Integer pageSize){
        if (pageNum == null || pageSize == null){
            return null;
        }
        return newInstance(pageNum.longValue(),pageSize.longValue());
    }

    /**
     * 创建PageParam
     * @param pageNum
     * @param pageSize
     * @return
     */
    public static PageParam newInstance(Long pageNum, Long pageSize){
        if (pageNum == null || pageSize == null){
            return null;
        }
        final PageParam p = new PageParam();
        p.setPageNum(pageNum);
        p.setPageSize(pageSize);
        p.setOffset((pageNum-1)*pageSize);
        p.setLimit(pageSize);
        return p;
    }

}
