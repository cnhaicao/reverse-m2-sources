package com.reverse.project.constants;

/**
 * maven打包方式
 *
 * @author guoguoqiang
 * @since 2020年07月06日
 */
@SuppressWarnings("unused")
public enum MavenPackagingEnum {
    /**
     * pom
     */
    POM("pom", "pom"),
    /**
     * jar
     */
    JAR("jar", "jar");

    private String code;
    private String name;

    MavenPackagingEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
