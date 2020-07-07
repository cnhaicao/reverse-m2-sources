package com.reverse.project.jar;

import cn.hutool.core.util.ZipUtil;
import org.junit.Test;

import java.io.File;

/**
 * jar unzip test
 * base hutool util
 *
 * @author guoguoqiang
 * @since 2020年07月07日
 */
public class JarUnzipTest {
    @Test
    public void unzipJar() {
        String source = "/Users/guoguoqiang/.m2/repository/org/springframework/boot/spring-boot-actuator-autoconfigure/2.1.3.RELEASE/spring-boot-actuator-autoconfigure-2.1.3.RELEASE-sources.jar";
        String target = "/Users/guoguoqiang/.m2/repository/org/springframework/boot/spring-boot-actuator-autoconfigure/2.1.3.RELEASE/spring-boot-actuator-autoconfigure-2.1.3.RELEASE-sources.jar-dir";
        ZipUtil.unzip(new File(source), new File(target));
    }
}
