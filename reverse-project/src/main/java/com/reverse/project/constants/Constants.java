package com.reverse.project.constants;

import java.io.File;

/**
 * 常量定义类
 *
 * @author guoguoqiang
 * @since 2020年07月06日
 */
public class Constants {
    public static final String POM_XML = "pom.xml";
    /**
     * pom文件
     */
    public static final String POM_FIX = ".pom";

    /**
     * 源码包后缀
     */
    public static final String SOURCES_FIX = "-sources.jar";

    /**
     * lib包后缀.jar
     */
    public static final String JAR_FIX = ".jar";

    /**
     * java 源码后缀
     */
    public static final String JAVA_FIX = ".java";

    /**
     * maven pom.xml变量符
     */
    public static final String POM_VAR_SYNBOL = "$";

    /**
     * 源码生成的src目录
     */
    public static final String SRC_DIRECTORY = "src" + File.separator + "main" + File.separator + "java";

    /**
     * 源码生成的resources
     */
    public static final String RESOURCES_DIRECTORY = "src" + File.separator + "main" + File.separator + "resources";

}
