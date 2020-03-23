package com.timeblog.business.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Dong.Chao
 * @Classname StatisticsDo
 * @Description 统计数量
 * @Date 2020/3/21 16:01
 * @Version V1.0
 */

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsDto {

    /** 事件类型 */
    private String eventType;

    /** 连续天数*/
    private int days;

    /** 开始时间 */
    private Date startDate;

    /** 结束时间 */
    private Date endDate;

}
