package com.reverse.project.base.task;



import java.text.MessageFormat;

/**
 * 任务运行时异常
 *
 * @author guoguoqiang
 * @since 2020年07月03日
 */
public class TaskRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 5468298198387302834L;

    private int code = -1;
    private String msg = "任务运行异常";
    private String[] vars = {};

    public TaskRuntimeException() {
    }


    public TaskRuntimeException(String msg, int code) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public TaskRuntimeException(int code, String msg, String... vars) {
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

    public TaskRuntimeException(String message) {
        super(message);
    }

    public TaskRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskRuntimeException(Throwable cause) {
        super(cause);
    }

}
