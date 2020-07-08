package com.reverse.project.task.sources.vo;

import com.reverse.project.constants.ReverseFailEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 异常源码文件源
 *
 * @author guoguoqiang
 * @since 2020年07月08日
 */
@Data
public class ErrorSourceVO implements Serializable {
    private static final long serialVersionUID = 2300808942013695066L;

    /**
     * 文件类型 {@link com.reverse.project.constants.FileTypeEnum} 由文件夹分析出来
     */
    private Integer fileType;

    /**
     * 源文件路径(绝对路径) 由文件夹分析出来
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
     * pom.xml路径(绝对路径) 由文件夹分析出来
     */
    private String pomPath;

    /**
     * 异常原因
     */
    private ReverseFailEnum failEnum;
}
