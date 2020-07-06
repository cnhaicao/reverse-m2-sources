package com.reverse.project.constants;

/**
 * 请填写类注释
 *
 * @author guoguoqiang
 * @since 2020年07月06日
 */
@SuppressWarnings("unused")
public enum FileTypeEnum {
    /**
     * pom.xml
     */
    FILE_TYPE_POM(1, "POM"),
    /**
     * -sources.jar
     */
    FILE_TYPE_SOURCES(2, "SOURCES");

    private int code;
    private String name;

    FileTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
