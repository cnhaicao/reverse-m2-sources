package com.reverse.project.task.sources.cmd;

import com.reverse.project.base.task.AbstractTaskCommand;
import com.reverse.project.constants.Constants;
import com.reverse.project.constants.FileTypeEnum;
import com.reverse.project.task.sources.context.ReverseSourceContext;
import com.reverse.project.task.sources.dto.SourceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * 扫描需要逆向的pom.xml 及源码包(-sources.jar)
 *
 * @author guoguoqiang
 * @since 2020年07月06日
 */
@Slf4j
@Component
public class ScanSourceCmd extends AbstractTaskCommand<ReverseSourceContext> {

    @Override
    public boolean exec(ReverseSourceContext context) throws Exception {
        log.info("context:" + context.toString());
        List<SourceDTO> sources = new ArrayList<>();
        File scanFile = new File(context.getScanDir());
        scan(scanFile, context.getM2Dir(), sources);
        context.getMiddle().setSourceList(sources);
        return false;
    }

    private void scan(File file, String m2Dir, List<SourceDTO> sources) {
        if (!file.exists() || file.isFile()) {
            return;
        }
        File[] listFile = file.listFiles();
        if (listFile == null || listFile.length == 0) {
            return;
        }
        // 源码目录的判断条件为 存在后缀为-sources.jar的文件或（存在.pom且不存在.jar)
        boolean matchSources = Arrays.stream(listFile).anyMatch(f -> f.isFile() && f.getName().endsWith(Constants.SOURCES_FIX));
        boolean noneMatchJar = Arrays.stream(listFile).noneMatch(f -> f.getName().endsWith(Constants.JAR_FIX));
        boolean matchPom = Arrays.stream(listFile).anyMatch(f -> f.isFile() && f.getName().endsWith(Constants.POM_FIX));

        // 当前在源码目录
        boolean inSourceDir = matchSources || (matchPom && noneMatchJar);
        if (inSourceDir) {
            SourceDTO source = analysisSource(listFile, m2Dir);
            sources.add(source);
            return;
        }

        Arrays.stream(listFile).forEach(f -> {
            if (f.isDirectory()) {
                scan(f, m2Dir, sources);
            }
        });
    }

    private SourceDTO analysisSource(File[] listFile, String m2Dir) {
        SourceDTO result = new SourceDTO();
        Optional<File> s = Arrays.stream(listFile).filter(f -> f.getName().endsWith(Constants.SOURCES_FIX)).max(Comparator.naturalOrder());
        if (s.isPresent()) {
            File sourceFile = s.get();
            result.setFileType(FileTypeEnum.FILE_TYPE_SOURCES.getCode());
            result.setSource(sourceFile.getAbsolutePath());
            result.setVersion(sourceFile.getParentFile().getName());
            result.setGroupId(getGroupId(sourceFile, m2Dir));
            log.info("catch sources.jar:{}", s.get().getAbsolutePath());
        } else {
            Optional<File> pom = Arrays.stream(listFile).filter(f -> f.getName().endsWith(Constants.POM_FIX)).max(Comparator.naturalOrder());
            if (pom.isPresent()) {
                File pomFile = pom.get();
                result.setFileType(FileTypeEnum.FILE_TYPE_POM.getCode());
                result.setSource(pomFile.getAbsolutePath());
                result.setVersion(pomFile.getParentFile().getName());
                result.setGroupId(getGroupId(pomFile, m2Dir));
                log.info("catch pom.xml:{}", pom.get().getAbsolutePath());
            }
        }
        return result;
    }

    private String getGroupId(File file, String m2Dir) {
        if (file.getParentFile().getParentFile() == null) {
            throw new IllegalArgumentException("目录结构有误:" + file.getAbsolutePath());
        }
        if (!m2Dir.endsWith(File.separator)) {
            m2Dir = m2Dir + File.separator;
        }
        String path = file.getParentFile().getParentFile().getAbsolutePath();
        path = path.substring(path.indexOf(m2Dir) + m2Dir.length());
        return path.replaceAll(File.separator, ".");
    }

}
