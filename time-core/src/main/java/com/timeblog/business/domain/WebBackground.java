package com.timeblog.business.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (TimeWebBackground)实体类
 *
 * @author makejava
 * @since 2020-02-27 15:01:20
 */

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class WebBackground implements Serializable {

    private static final long serialVersionUID = -31152260057003801L;

    private Integer backgroundId;
    /**背景名称*/
    private String backgroundName;
    /**背景图片路径*/
    private String backgroundUrl;



}
