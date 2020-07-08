package com.reverse.project.task.sources.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 中间结果 DTO
 *
 * @author guoguoqiang
 * @since 2020年07月07日
 */
@Data
public class MiddleVO implements Serializable {
    private static final long serialVersionUID = -7004368417400992006L;

    /**
     * 需要逆向的文件列表(包括pom.xml及source.jar)
     */
    private List<SourceVO> sourceList;

    /**
     * 将需要逆向的文件列表收集成map形式 方便处理
     */
    private Map<String, SourceVO> sourceMap;
}
