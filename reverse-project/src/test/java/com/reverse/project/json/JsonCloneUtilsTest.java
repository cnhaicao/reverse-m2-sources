package com.reverse.project.json;

import com.reverse.project.task.sources.vo.ModuleVO;
import com.reverse.project.task.sources.vo.Pom;
import com.reverse.project.task.sources.vo.SourceVO;
import com.reverse.project.utils.JsonBinder;
import com.reverse.project.utils.JsonCloneUtils;
import com.reverse.project.utils.PomUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;

/**
 * JsonCloneUtils测试
 *
 * @author guoguoqiang
 * @since 2020年07月14日
 */
@Slf4j
public class JsonCloneUtilsTest {
    @Test
    public void clonePom() throws IOException {
        Pom pom = new Pom();
        PomUtils.analysisPom("/Users/guoguoqiang/me-workspace/reverse-m2-sources/backup/cedarsoft/commons/2.0.6/commons-2.0.6.pom", pom);
        log.info("pom:{}", JsonBinder.buildNormalBinder().toJson(pom));
        Pom pomNew = JsonCloneUtils.cloneFrom(pom, Pom.class);
        log.info("pomNew:{}", JsonBinder.buildNormalBinder().toJson(pomNew));
    }

    @Test
    public void cloneModule() throws IOException {
        String pomPath = "/Users/guoguoqiang/me-workspace/reverse-m2-sources/backup/cedarsoft/commons/2.0.6/commons-2.0.6.pom";
        Pom pom = new Pom();
        PomUtils.analysisPom(pomPath, pom);
        log.info("pom:{}", JsonBinder.buildNormalBinder().toJson(pom));
        SourceVO source = new SourceVO();
        source.setPom(pom);
        source.setPomPath(pomPath);
        source.setGroupId("com.cedarsoft");
        source.setArtifactId("commons");
        source.setVersion("2.0.6");
        ModuleVO module = JsonCloneUtils.cloneFrom(source, ModuleVO.class);
        log.info("module:{}", JsonBinder.buildNormalBinder().toJson(module));
    }
}
