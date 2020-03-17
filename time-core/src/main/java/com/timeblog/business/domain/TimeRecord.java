package com.timeblog.business.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * (TimeRecord)实体类
 *
 * @author makejava
 * @since 2020-03-17 16:50:25
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TimeRecord implements Serializable {

    private Integer recordId;
    /**记录标题*/
    private String recordTitle;
    /**记录内容*/
    private String recordContext;
    /**时间*/
    private Date recordTime;
    /**状态*/
    private Integer status;

}
