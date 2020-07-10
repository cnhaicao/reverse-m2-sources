package com.reverse.project.task.sources.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * reverse source dto
 *
 * @author guoguoqiang
 * @since 2020年07月06日
 */
@Data
public class OutputVO implements Serializable {
    private static final long serialVersionUID = -4285145518854752278L;

    /**
     * 逆向失败的源码
     */
    private List<ErrorSourceVO> errorSources;

    /**
     * 逆向成功的源码
     */
    private List<ModuleVO> successSources;

}
