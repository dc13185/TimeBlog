package com.timeblog.framework.task.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (TimeTask)实体类
 *
 * @author makejava
 * @since 2020-03-10 12:24:33
 */

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TimeTask implements Serializable {

    private static final long serialVersionUID = -75893408468558154L;

    private Integer taskId;
    /**任务名称*/
    private String taskName;
    /**cron表达式*/
    private String taskCron;
    /**任务状态，1:启动，0:停止*/

    private String taskBeanName;

    private String taskMethod;

    private Integer status;


}
