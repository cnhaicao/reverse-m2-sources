package com.reverse.project.files;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

/**
 * 文件列举测试
 *
 * @author guoguoqiang
 * @since 2020年07月06日
 */
@Slf4j
public class ListFileTest {

    @Test
    public void listFile() {
        File file = new File("/Users/guoguoqiang/gitlab/");
        File[] fileList = file.listFiles();
        if (fileList == null) {
            return;
        }
        Optional<File> source = Arrays.stream(fileList).filter(f -> f.getName().endsWith("-sources.jar")).max(Comparator.naturalOrder());
        if (source.isPresent()) {
            log.info("catch sources.jar:{}", source.get().getAbsolutePath());
        } else {
            Optional<File> pom = Arrays.stream(fileList).filter(f -> f.getName().endsWith(".pomPath")).max(Comparator.naturalOrder());
            if (pom.isPresent()) {
                log.info("cat pomPath.xml:{}", pom.get().getAbsolutePath());
            }
        }
    }

}
