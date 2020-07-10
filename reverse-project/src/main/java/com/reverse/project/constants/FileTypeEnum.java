package com.reverse.project.constants;

/**
 * 源码类型
 *
 * @author guoguoqiang
 * @since 2020年07月06日
 */
@SuppressWarnings("unused")
public enum FileTypeEnum {
    /**
     * pom.xml
     */
    FILE_TYPE_POM(1, "pom"),
    /**
     * -sources.jar
     */
    FILE_TYPE_SOURCES(2, "source.jar源码包");

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

    public static String getNameByIndex(int index) {
        for (FileTypeEnum fileTypeEnum : FileTypeEnum.values()) {
            if (fileTypeEnum.getCode() == index) {
                return fileTypeEnum.getName();
            }
        }
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }
}
