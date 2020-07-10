package com.reverse.project.task.sources.cmd;

import cn.hutool.poi.excel.ExcelWriter;
import com.google.common.collect.Lists;
import com.reverse.project.base.task.AbstractTaskCommand;
import com.reverse.project.constants.Constants;
import com.reverse.project.constants.FileTypeEnum;
import com.reverse.project.constants.ReverseFailEnum;
import com.reverse.project.task.sources.context.ReverseSourceContext;
import com.reverse.project.task.sources.vo.ErrorSourceVO;
import com.reverse.project.task.sources.vo.ModuleVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 输出逆向结果报告
 * 输入：output.errorSources、output.successSources
 * 输出：逆向结果报告
 * @author guoguoqiang
 * @since 2020年07月10日
 */
@Slf4j
@Component
public class ExportReportCmd extends AbstractTaskCommand<ReverseSourceContext> {

    @Override
    public boolean exec(ReverseSourceContext context) throws Exception {
        List<ErrorSourceVO> errorSources = context.getOutput().getErrorSources();
        List<ModuleVO> successSources = context.getOutput().getSuccessSources();

        exportExcel(context.getOutputDir(), successSources, errorSources);
        return false;
    }

    private void exportExcel(String outputDir, List<ModuleVO> successSources, List<ErrorSourceVO> errorSources) throws IOException {
        String excel = outputDir + File.separator + DateFormatUtils.format(new Date(), "yyyyMMdd-HHmmss-SSS") + Constants.EXPORT_EXCEL_FIX;
        File file = new File(excel);
        if (file.exists()) {
            FileUtils.forceDelete(file);
        }

        // 汇总信息
        List<String> rowTitle = Lists.newArrayList("逆向成功的项目数", "逆向失败的源码包数");
        List<String> rowValue = Lists.newArrayList("" + successSources.size(), "" + errorSources.size());
        List<List<String>> total = Lists.newArrayList(rowTitle, rowValue);
        try (ExcelWriter writer = new ExcelWriter(excel, "逆向报告")) {
            //跳过当前行
            writer.passCurrentRow();
            //合并单元格后的标题行，使用默认标题样式
            writer.merge(rowTitle.size() - 1, "逆向报告汇总");
            //一次性写出内容，强制输出标题
            writer.write(total, true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        rowTitle = Lists.newArrayList("项目名称", "版本号", "groupId", "项目路径");
        List<List<String>> successList = Lists.newArrayList();
        successList.add(rowTitle);
        successSources.forEach(m -> {
            StringBuilder sb = new StringBuilder(outputDir);
            sb.append(File.separator).append(m.getArtifactId()).append(File.separator).append(m.getVersion());
            File f = new File(sb.toString());
            List<String> rValue = Lists.newArrayList(m.getArtifactId(), m.getVersion(),
                m.getGroupId(), f.getAbsolutePath());
            successList.add(rValue);
        });
        // 逆向成功项目列表
        try (ExcelWriter writer = new ExcelWriter(excel, "逆向成功项目列表")) {
            //跳过当前行
            writer.passCurrentRow();
            //合并单元格后的标题行，使用默认标题样式
            writer.merge(rowTitle.size() - 1, "逆向成功项目列表");
            //一次性写出内容，强制输出标题
            writer.write(successList, true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        // 逆向失败源码包列表
        // 逆向成功项目列表
        try (ExcelWriter writer = new ExcelWriter(excel, "逆向失败源码包列表")) {
            //跳过当前行
            writer.passCurrentRow();
            //一次性写出内容，强制输出标题
            writer.write(generateErrorReport(errorSources), true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        log.error("逆向报告生成成功:" + new File(excel).getAbsolutePath());
    }

    private List<ErrorReport> generateErrorReport(List<ErrorSourceVO> errorSources) {
        List<ErrorReport> result = Lists.newArrayList();
        if (CollectionUtils.isEmpty(errorSources)) {
            return result;
        }
        errorSources.forEach(s -> {
            ErrorReport errorReport = ErrorReport.builder()
                .fileType(FileTypeEnum.getNameByIndex(s.getFileType()))
                .artifactId(s.getArtifactId()).version(s.getVersion())
                .groupId(s.getGroupId())
                .reason(s.getFailEnum().getName() + (s.getReverseFailDescription() != null ? " " + s.getReverseFailDescription() : ""))
                .build();
            result.add(errorReport);
        });
        return result;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ErrorReport {
        /**
         * 源码包类型 source.jar/pom
         */
        private String fileType;

        /**
         * maven artifactId
         */
        private String artifactId;

        /**
         * maven版本号
         */
        private String version;

        /**
         * maven groupId
         */
        private String groupId;

        /**
         * 源文件路径(绝对路径)
         */
        private String source;

        /**
         * 逆向失败原因
         */
        private String reason;
    }

}
