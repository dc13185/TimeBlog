package com.timeblog.business.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * (TimeLabel)实体类
 *
 * @author makejava
 * @since 2020-02-23 10:00:49
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Label extends BaseDomain {

    private Integer labelId;
    
    private String labelName;
}