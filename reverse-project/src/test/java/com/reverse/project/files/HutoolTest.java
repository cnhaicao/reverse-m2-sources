package com.reverse.project.files;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;

/**
 * hutool test
 *
 * @author guoguoqiang
 * @since 2020年07月13日
 */
@Slf4j
public class HutoolTest {
    @Test
    public void file() {
        File f = new File("/Users/guoguoqiang/me-workspace/reverse-m2-sources/reverse-project/target/../../docs/m2-source-example-generate/20200713-145553-438.xlsx");
        log.info("f path:{}", f.getAbsolutePath());
        log.info("f abs path:{}", FileUtil.getAbsolutePath(f));
    }
}
