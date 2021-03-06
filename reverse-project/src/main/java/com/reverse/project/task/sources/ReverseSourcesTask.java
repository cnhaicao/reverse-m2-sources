package com.reverse.project.task.sources;

import com.reverse.project.base.task.BaseTask;
import com.reverse.project.base.task.annotation.TaskChain;
import com.reverse.project.task.sources.cmd.AnalysisPomCmd;
import com.reverse.project.task.sources.cmd.CollectSourcesCmd;
import com.reverse.project.task.sources.cmd.ExportReportCmd;
import com.reverse.project.task.sources.cmd.GenerateAllModulesHistoryCmd;
import com.reverse.project.task.sources.cmd.GenerateProjectSourceCmd;
import com.reverse.project.task.sources.cmd.InitCmd;
import com.reverse.project.task.sources.cmd.ProjectAnalysisCmd;
import com.reverse.project.task.sources.cmd.RemoveInCompleteSourcesCmd;
import com.reverse.project.task.sources.cmd.RemoveUnsupportPackagingPomCmd;
import com.reverse.project.task.sources.cmd.ScanSourceCmd;
import com.reverse.project.task.sources.cmd.UnzipSourcesCmd;
import org.springframework.stereotype.Component;

/**
 * ReverseSourceTask
 *
 * @author guoguoqiang
 * @since 2020年07月06日
 */
@Component
@TaskChain(types = {
    InitCmd.class,
    ScanSourceCmd.class,
    UnzipSourcesCmd.class,
    CollectSourcesCmd.class,
    AnalysisPomCmd.class,
    RemoveUnsupportPackagingPomCmd.class,
    GenerateAllModulesHistoryCmd.class,
    ProjectAnalysisCmd.class,
    RemoveInCompleteSourcesCmd.class,
    GenerateProjectSourceCmd.class,
    ExportReportCmd.class
})
public class ReverseSourcesTask<ReverseSourceContext> extends BaseTask {

}
