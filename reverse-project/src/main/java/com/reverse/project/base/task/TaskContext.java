package com.reverse.project.base.task;

import java.util.Date;

/**
 * 任务上下文
 *
 * @author guoguoqiang
 * @since 2020年07月03日
 */
public class TaskContext implements Context {
    private static final long serialVersionUID = 7079507268626458316L;

    private boolean result = true;
    private Date beginTime;
    private Date endTime;

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
