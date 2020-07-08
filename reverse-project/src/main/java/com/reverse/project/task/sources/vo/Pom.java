package com.reverse.project.task.sources.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * pom对象
 *
 * @author guoguoqiang
 * @since 2020年07月07日
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pom implements Serializable {

    private static final long serialVersionUID = 729799108892578964L;

    /**
     * maven打包方式 由pom.xml解析
     * {@link com.reverse.project.constants.MavenPackagingEnum}
     * 源码只存在jar和pom两种打包形式
     */
    private String packaging;

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
     * maven relativePath 由pom.xml解析(可能暂时没用)
     * 父pom才有该属性
     */
    private String relativePath;

    /**
     * maven name 由pom.xml解析
     */
    private String name;

    /**
     * maven description 由pom.xml解析
     */
    private String description;

    /**
     * pom下所有module 由pom.xml解析
     */
    private List<String> modules;

    /**
     * 父pom
     */
    private Pom parent;

}
