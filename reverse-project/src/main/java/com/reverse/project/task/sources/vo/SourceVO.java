package com.reverse.project.task.sources.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 需要逆向的文件源 DTO
 *
 * @author guoguoqiang
 * @since 2020年07月06日
 * 样例路径
 * /.m2/repository/org/springframework/boot/spring-boot-actuator-autoconfigure/2.1.3.RELEASE/spring-boot-actuator-autoconfigure-2.1.3.RELEASE-sources.jar
 * groupId: org.springframework.boot
 * artifactId: spring-boot-actuator-autoconfigure
 * version: 2.1.3.RELEASE
 */
@Data
public class SourceVO implements Serializable {

    private static final long serialVersionUID = 5576780709198641376L;

    /**
     * 文件类型 {@link com.reverse.project.constants.FileTypeEnum}
     */
    private Integer fileType;

    /**
     * 源文件路径(绝对路径)
     */
    private String source;

    /**
     * maven groupId 由文件夹分析出来
     */
    private String groupId;

    /**
     * maven artifactId 由文件夹分析出来
     */
    private String artifactId;

    /**
     * maven版本号 由文件夹分析出来
     */
    private String version;

    /**
     * pom.xml路径(绝对路径)
     */
    private String pomPath;

    /**
     * 源码解压路径
     */
    private String sourcesPath;

    /**
     * pom解析结果对象
     */
    private Pom pom;
}
