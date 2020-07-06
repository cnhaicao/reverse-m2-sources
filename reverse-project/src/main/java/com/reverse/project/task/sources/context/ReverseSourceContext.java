package com.reverse.project.task.sources.context;

import com.reverse.project.base.task.TaskContext;
import com.reverse.project.task.sources.dto.ReverseSourceDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请填写类注释
 *
 * @author guoguoqiang
 * @since 2020年07月06日
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReverseSourceContext extends TaskContext {
    private static final long serialVersionUID = 485463239399262025L;

    // -----------------------输入参数-----------------------
    /**
     * m2根目录
     */
    private String m2Dir;

    /**
     * 待逆向的目录
     */
    private String scanDir;

    /**
     * 临时目录 用于存放-sources.jar解压文件
     */
    private String tmpDir;

    // -----------------------输出参数-----------------------
    /**
     * 逆向结果 DTO
     */
    ReverseSourceDTO reverseSource = new ReverseSourceDTO();

}
