package com.timeblog.business.domain;

import com.google.common.base.Objects;
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
    /**状态    1:已完成  -1：待完成 */
    private Integer status;


    /**事件类型   0：发布文章  1：撸代码  2：锻炼 运动  -1：其他代办事件  */
    private Integer eventType;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeRecord that = (TimeRecord) o;
        return Objects.equal(recordId, that.recordId) &&
                Objects.equal(recordTitle, that.recordTitle) &&
                Objects.equal(recordContext, that.recordContext) &&
                Objects.equal(recordStartTime, that.recordStartTime) &&
                Objects.equal(recordEndTime, that.recordEndTime) &&
                Objects.equal(status, that.status) &&
                Objects.equal(eventType, that.eventType);
    }

    @Override
    public int hashCode() {
        int objectCodes = Objects.hashCode(recordId, recordTitle, recordContext, status, eventType);
        int startHashCode = dateHashCode(this.recordStartTime);
        int recordEndTime = dateHashCode(this.recordEndTime);
        return objectCodes + startHashCode + recordEndTime;
    }

    public enum eventEnum{
        RELEASE_BLOG(0,"发布文章"),WRITE_CODE(1,"撸代码"),MOTION(2,"运动"),OTHER(-1,"其他事项");
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





    private int dateHashCode(Date date){
        int dateTime = (int)recordStartTime.getTime();
        int  dateHashCode =  (int) dateTime ^ (int) (dateTime >> 32);
        return dateHashCode;
    }




}
