package com.reverse.project.exception;

/**
 * 父pom解析异常(不存在或解析异常)
 *
 * @author guoguoqiang
 * @since 2020年07月08日
 */
public class ParentPomException extends Exception {
    private static final long serialVersionUID = 1529216463990235767L;
    public ParentPomException(){
        super();
    }

    public ParentPomException(String message){
        super(message);
    }

    public ParentPomException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParentPomException(Throwable cause) {
        super(cause);
    }
}
