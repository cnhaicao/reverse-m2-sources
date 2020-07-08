package com.reverse.project.exception;

/**
 * pom.xml不支持的packaging
 *
 * @author guoguoqiang
 * @since 2020年07月08日
 */
public class UnsupportPomPackagingException extends Exception {
    private static final long serialVersionUID = 1529216463990235767L;
    public UnsupportPomPackagingException(){
        super();
    }

    public UnsupportPomPackagingException(String message){
        super(message);
    }

    public UnsupportPomPackagingException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportPomPackagingException(Throwable cause) {
        super(cause);
    }
}
