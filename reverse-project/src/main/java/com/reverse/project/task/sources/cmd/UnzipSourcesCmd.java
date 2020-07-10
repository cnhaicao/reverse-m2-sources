package com.reverse.project.task.sources.cmd;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ZipUtil;
import com.reverse.project.base.task.AbstractTaskCommand;
import com.reverse.project.constants.Constants;
import com.reverse.project.constants.FileTypeEnum;
import com.reverse.project.task.sources.context.ReverseSourceContext;
import com.reverse.project.task.sources.vo.SourceVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 解压 sources.jar
 * 对middle.sourceList进行源码解压
 * 输入：middle.sourceList
 * 输出：解压sources.jar到tmpDir目录下
 * @author guoguoqiang
 * @since 2020年07月07日
 */
@Slf4j
@Component
public class UnzipSourcesCmd extends AbstractTaskCommand<ReverseSourceContext> {
    @Override
    public boolean exec(ReverseSourceContext context) throws Exception {
        List<SourceVO> sources = context.getMiddle().getSourceList();
        if (CollectionUtil.isEmpty(sources)) {
            log.error("param error scanDir or m2Dir is blank.");
            return true;
        }
        sources.parallelStream().forEach(s -> {
            if (s.getFileType() == FileTypeEnum.FILE_TYPE_SOURCES.getCode()) {
                File file = new File(s.getSource());
                String targetDir = context.getTmpDir() + File.separator + s.getGroupId() + File.separator
                    + s.getVersion() + File.separator + s.getArtifactId() + File.separator + file.getName();
                File target = new File(targetDir);
                // 这时只判断文件有没有存在 可以考虑增加CRC校验
                if (!target.exists() && file.length() > 0) {
                    try {
                        ZipUtil.unzip(file, target);
                    } catch (Exception e) {
                        log.error("unzip error:" + s.getSource(), e);
                    }
                }
                if (target.exists() && target.isDirectory()) {
                    s.setSourcesPath(target.getAbsolutePath());
                    s.setPomPath(findPom(target));
                }
            }
        });
        return false;
    }

    /**
     * 从解压文件夹找到pom.xml
     * @param target sources.jar解压文件夹
     * @return pom.xml绝对路径
     */
    private String findPom(File target) {
        if (!target.exists() || target.isFile()) {
            return null;
        }
        File[] listFile = target.listFiles();
        if (listFile == null || listFile.length == 0) {
            return null;
        }
        Optional<File> file = Arrays.stream(listFile).filter(f -> f.getName().equals(Constants.POM_XML)).findFirst();
        if (file.isPresent()) {
            return file.get().getAbsolutePath();
        }
        for (File item: listFile) {
            if (item.isDirectory()) {
                String pom = findPom(item);
                if (pom != null) {
                    return pom;
                }
            }
        }
        return null;
    }
}
