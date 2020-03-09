package com.timeblog.framework.task;

import java.util.concurrent.ScheduledFuture;

/**
 * @author Dong.Chao
 * @Classname ScheduledTask
 * @Description 定时任务控制类
 * @Date 2020/3/10 0:39
 * @Version V1.0
 */
public final class ScheduledTask {

    public volatile ScheduledFuture<?> future;
    /**
     * 取消定时任务
     */
    public void cancel() {
        ScheduledFuture<?> future = this.future;
        if (future != null) {
            future.cancel(true);
        }
    }
}
