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
    /**开始时间*/
    private Date recordStartTime;
    /**结束*/
    private Date recordEndTime;
    /**状态*/
    private Integer status;


    /**事件类型   0：发布文章  1：撸代码  2：锻炼 运动  -1：其他代办事件  */
    private Integer eventType;



    private enum eventEnum{
        RELEASE_BLOG(0,"发布文章"),WRITE_CODE(1,"撸代码"),MOTION(2,"运动");
        private Integer eventType;

        private String eventTitle;

        eventEnum(Integer eventType, String eventTitle) {
            this.eventType = eventType;
            this.eventTitle = eventTitle;
        }

        public Integer getEventType() {
            return eventType;
        }

        public void setEventType(Integer eventType) {
            this.eventType = eventType;
        }

        public String getEventTitle() {
            return eventTitle;
        }

        public void setEventTitle(String eventTitle) {
            this.eventTitle = eventTitle;
        }
    }


}
