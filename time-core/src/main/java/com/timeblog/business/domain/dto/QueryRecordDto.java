package com.timeblog.business.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Dong.Chao
 * @Classname QueryRecordDto
 * @Description 记录时间传输
 * @Date 2020/3/18 11:26
 * @Version V1.0
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class QueryRecordDto {

    private Date startDate;

    private Date endDate;
}
