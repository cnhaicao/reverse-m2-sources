package com.reverse.project.constants;

/**
 * 逆向失败原因
 *
 * @author guoguoqiang
 * @since 2020年07月06日
 */
@SuppressWarnings("unused")
public enum ReverseFailEnum {
    /**
     * 不支持的packaging方式
     */
    FAIL_NO_SUPPORT(0, "不支持的packaging方式"),
    /**
     * pom.xml缺失或解析异常
     */
    FAIL_POM(1, "pom.xml缺失或解析异常"),
    FAIL_NOT_EXISTS_PARENT_POM(2, "父pom解析异常"),
    FAIL_MODULE_MISS(3, "module缺失"),
    FAIL_UN_MATCH_IN_MODULE(4, "未被其它模块包含"),
    FAIL_REVERSE_SOURCE(5, "源码生成阶段异常"),
    FAIL_ANALYSIS_SOURCE(6, "源码结构分析阶段异常");



    private int code;
    private String name;

    ReverseFailEnum(int code, String name) {
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
    
    public static String getNameByIndex(int index) {
        for (ReverseFailEnum fileTypeEnum : ReverseFailEnum.values()) {
            if (fileTypeEnum.getCode() == index) {
                return fileTypeEnum.getName();
            }
        }
        return null;
    }
}
