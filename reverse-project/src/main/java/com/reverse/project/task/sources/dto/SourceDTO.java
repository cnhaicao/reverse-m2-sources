package com.reverse.project.task.sources.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 需要逆向的文件源 DTO
 *
 * @author guoguoqiang
 * @since 2020年07月06日
 */
@Data
public class SourceDTO implements Serializable {

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
     * maven版本号 由文件夹分析出来
     */
    private String version;

}
