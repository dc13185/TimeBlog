package com.timeblog.business.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Dong.Chao
 * @Classname Sentence
 * @Description 句子实例
 * @Date 2020/3/9 15:50
 * @Version V1.0
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Sentence {



    /**
     * 句子内容
     * */
    private Integer sentenceId;

    /**
     * 句子内容
     * */
    private String content;


    /**
     * 作者
     * */
    private String author;


    /**
     * 对应作品
     */
    private String works;

    private Date createDate;

}
