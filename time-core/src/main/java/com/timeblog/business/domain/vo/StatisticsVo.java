package com.timeblog.business.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Dong.Chao
 * @Classname StatisticsVo
 * @Description 统计表现层实体
 * @Date 2020/3/21 16:17
 * @Version V1.0
 */

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsVo {

    private String eventTypeId;

    private String eventTypeName;

    /** 最大连续值 如连续写博客最大天数  */
    private int maxConsecutiveNumber;

    /** 总数  */
    private int totalNumber;

    /** 最大值对应的结束天数  */
    private Date maxEndDate;


    /** 最大值对应的开始天数  */
    private Date maxStartDate;



}
