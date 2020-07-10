package com.reverse.project.task.sources.cmd;

import com.reverse.project.base.task.AbstractTaskCommand;
import com.reverse.project.base.task.TaskException;
import com.reverse.project.task.sources.context.ReverseSourceContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 初始化
 * 检查输入参数并初始化
 * 强制删除及建立临时目录
 * 输入：所有输入参数
 * 输出：将相关目录设置成绝对路径格式
 * @author guoguoqiang
 * @since 2020年07月06日
 */
@Slf4j
@Component
public class InitCmd extends AbstractTaskCommand<ReverseSourceContext> {

    @Override
    public boolean exec(ReverseSourceContext context) throws Exception {
        if (context == null) {
            throw new TaskException("上下文对象不能为空");
        }

        if (StringUtils.isBlank(context.getTmpDir()) || StringUtils.isBlank(context.getScanDir())
            || StringUtils.isBlank(context.getM2Dir()) || StringUtils.isBlank(context.getOutputDir())) {
            log.error("参数异常 tmpDir,scanDir,m2Dir,outputDir均不允许为空");
            throw new TaskException("参数异常 tmpDir,scanDir,m2Dir,outputDir均不允许为空");
        }
        File scanFile = new File(context.getScanDir());
        File m2File = new File(context.getM2Dir());
        if (!scanFile.exists() || scanFile.isFile()) {
            throw new TaskException("scanDir error:" + context.getScanDir() + " must exists.");
        }
        if (!m2File.exists() || m2File.isFile()) {
            throw new TaskException("m2Dir error:" + context.getM2Dir());
        }
        File tmpFile = new File(context.getTmpDir());
        boolean mustDelete = tmpFile.exists() && (context.isForceDeleteTmpDir() || tmpFile.isFile());
        if (mustDelete) {
            FileUtils.forceDelete(tmpFile);
        }
        if (!tmpFile.exists()) {
            FileUtils.forceMkdir(tmpFile);
        }
        File outputFile = new File(context.getOutputDir());
        if (!outputFile.exists()) {
            FileUtils.forceMkdir(outputFile);
        }
        context.setTmpDir(tmpFile.getAbsolutePath());
        context.setScanDir(scanFile.getAbsolutePath());
        context.setM2Dir(m2File.getAbsolutePath());
        context.setOutputDir(outputFile.getAbsolutePath());
        return false;
    }

}
