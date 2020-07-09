package com.reverse.project.task.sources.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * key = groupId$artifactId$version 详见 {@link com.reverse.project.utils.MapKeyUtil}
     * value = SourceVO
     */
    private Map<String, SourceVO> sourceMap;

    /**
     * 所有pom.xml里的<module>模块记录 用于判断某模块是否别人的子模块
     * 值为groupId$artifactId$version 详见 {@link com.reverse.project.utils.MapKeyUtil}
     */
    private Set<String> allModuleHistory;

    /**
     * 将需要逆向的模块列表收集成map形式 方便生成源代码
     * key = groupId$artifactId$version 详见 {@link com.reverse.project.utils.MapKeyUtil}
     * value = ModuleVO
     */
    private Map<String, ModuleVO> moduleMap;
}
