package com.timeblog.business.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: dong.chao
 * @create: 2019-09-07 15:22
 * @description: 分页入参，用于PageHelper分页
 **/
@Data
@Builder
public class PageDomain implements Serializable {

    private int pageNumber;

    private int pageSize;

    private long total;

    /**
     * 检索内容
     */
    private String retrieve;

    public PageDomain() {
    }

    public PageDomain(int pageNumber, int pageSize, long total, String retrieve) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.total = total;
        this.retrieve = retrieve;
    }
}
