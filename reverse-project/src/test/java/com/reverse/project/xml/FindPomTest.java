package com.reverse.project.xml;

import com.reverse.project.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;

/**
 * find pom.xml
 *
 * @author guoguoqiang
 * @since 2020年07月07日
 */
@Slf4j
public class FindPomTest {
    @Test
    public void test() {
        String source = "/Users/guoguoqiang/.m2/";
        File pomFile = new File(source);
        String pomXml = findPom(pomFile);
        log.info("pom xml:{}", pomXml);
    }

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
