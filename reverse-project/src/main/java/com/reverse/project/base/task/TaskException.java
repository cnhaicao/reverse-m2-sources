package com.reverse.project.base.task;



import java.text.MessageFormat;

/**
 * 任务异常
 *
 * @author guoguoqiang
 * @since 2020年07月03日
 */
public class TaskException extends Exception {

    private static final long serialVersionUID = -5435138162631642600L;

    private int code = -1;
    private String msg = "任务异常";
    private String[] vars = {};

    public TaskException() {
    }


    public TaskException(String msg, int code) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public TaskException(int code, String msg, String... vars) {
        this.code = code;
        this.msg = msg;
        this.vars = vars;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        if (msg != null && !"".equals(msg.trim())&& vars.length > 0) {
            return MessageFormat.format(msg, vars);
        } else {
            return msg;
        }
    }

    public TaskException(String message) {
        super(message);
    }

    public TaskException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskException(Throwable cause) {
        super(cause);
    }

}
