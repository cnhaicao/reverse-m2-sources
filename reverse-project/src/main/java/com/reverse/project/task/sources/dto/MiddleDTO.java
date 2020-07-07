package com.reverse.project.task.sources.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 中间结果 DTO
 *
 * @author guoguoqiang
 * @since 2020年07月07日
 */
@Data
public class MiddleDTO implements Serializable {
    private static final long serialVersionUID = -7004368417400992006L;
    /**
     * 需要逆向的文件列表
     */
    private List<SourceDTO> sourceList;
}
