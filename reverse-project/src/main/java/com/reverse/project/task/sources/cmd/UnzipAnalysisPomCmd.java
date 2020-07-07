package com.reverse.project.task.sources.cmd;

import com.reverse.project.base.task.AbstractTaskCommand;
import com.reverse.project.task.sources.context.ReverseSourceContext;
import org.springframework.stereotype.Component;

/**
 * 解压jar 并分析pom.xml
 *
 * @author guoguoqiang
 * @since 2020年07月07日
 */
@Component
public class UnzipAnalysisPomCmd extends AbstractTaskCommand<ReverseSourceContext> {
    @Override
    public boolean exec(ReverseSourceContext context) throws Exception {
        return false;
    }
}
