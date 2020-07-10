package com.reverse.project.task.sources.cmd;

import com.reverse.project.base.task.AbstractTaskCommand;
import com.reverse.project.constants.Constants;
import com.reverse.project.constants.FileTypeEnum;
import com.reverse.project.task.sources.context.ReverseSourceContext;
import com.reverse.project.task.sources.vo.SourceVO;
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
 * 输入：scanDir
 * 输出：middle.sourceList
 * @author guoguoqiang
 * @since 2020年07月06日
 */
@Slf4j
@Component
public class ScanSourceCmd extends AbstractTaskCommand<ReverseSourceContext> {

    @Override
    public boolean exec(ReverseSourceContext context) throws Exception {
        log.info("context:" + context.toString());
        List<SourceVO> sources = new ArrayList<>();
        File scanFile = new File(context.getScanDir());
        scan(scanFile, context.getM2Dir(), sources);
        context.getMiddle().setSourceList(sources);
        return false;
    }

    private void scan(File file, String m2Dir, List<SourceVO> sources) {
        if (!file.exists() || file.isFile()) {
            return;
        }
        File[] listFile = file.listFiles();
        if (listFile == null || listFile.length == 0) {
            return;
        }
        // 源码目录的判断条件为 存在后缀为-sources.jar的文件或（存在.pom且不存在.jar)
        Optional<File> s = Arrays.stream(listFile).filter(f -> f.getName().endsWith(Constants.SOURCES_FIX)).max(Comparator.naturalOrder());
        if (s.isPresent()) {
            log.debug("catch sources.jar:{}", s.get().getAbsolutePath());
            File sourceFile = s.get();
            SourceVO source = buildSource(sourceFile, m2Dir, FileTypeEnum.FILE_TYPE_SOURCES.getCode());
            sources.add(source);
            return;
        }
        Optional<File> pom = Arrays.stream(listFile).filter(f -> f.getName().endsWith(Constants.POM_FIX))
            .filter(f -> !f.getName().endsWith(Constants.JAR_FIX))
            .max(Comparator.naturalOrder());

        if (pom.isPresent()) {
            log.debug("catch pom.xml:{}", pom.get().getAbsolutePath());
            File pomFile = pom.get();
            SourceVO source = buildSource(pomFile, m2Dir, FileTypeEnum.FILE_TYPE_POM.getCode());
            source.setPomPath(source.getSource());
            sources.add(source);
            return;
        }

        Arrays.stream(listFile).forEach(f -> {
            if (f.isDirectory()) {
                scan(f, m2Dir, sources);
            }
        });
    }

    private SourceVO buildSource(File file, String m2Dir, int fileType) {
        SourceVO result = new SourceVO();
        result.setFileType(fileType);
        result.setSource(file.getAbsolutePath());
        result.setVersion(file.getParentFile().getName());
        result.setArtifactId(file.getParentFile().getParentFile().getName());
        result.setGroupId(getGroupId(file, m2Dir));
        return result;
    }

    private String getGroupId(File file, String m2Dir) {
        if (file.getParentFile().getParentFile().getParentFile() == null) {
            throw new IllegalArgumentException("目录结构有误:" + file.getAbsolutePath());
        }
        File groupFileDir = file.getParentFile().getParentFile().getParentFile();
        if (!m2Dir.endsWith(File.separator)) {
            m2Dir = m2Dir + File.separator;
        }
        String path = groupFileDir.getAbsolutePath();
        path = path.substring(path.indexOf(m2Dir) + m2Dir.length());
        return path.replaceAll(File.separator, ".");
    }

}
