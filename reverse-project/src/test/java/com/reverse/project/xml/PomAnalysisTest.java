package com.reverse.project.xml;

import com.reverse.project.task.sources.vo.Pom;
import com.reverse.project.utils.PomUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * pomPath.xml解析测试
 *
 * @author guoguoqiang
 * @since 2020年07月07日
 */
@Slf4j
public class PomAnalysisTest {

    @Test
    public void analysisPom() throws IOException {
        String pom = "/Users/guoguoqiang/.m2/repository/com/cloud/cloud-service/1.0.1-SNAPSHOT/cloud-service-1.0.1-SNAPSHOT.pomPath";
        Document document = Jsoup.parse(new File(pom), "utf-8");
        Elements el = document.select("project>packaging");
        if (el.size() != 0) {
            log.info("打包方式:" + el.get(0).text());
        }
        Elements parent = document.select("project>parent");
        if (parent.size() > 0) {
            String groupId = parent.select("groupId").text();
            String artifactId = parent.select("artifactId").text();
            String version = parent.select("version").text();
            String relativePath = parent.select("relativePath").val();
            log.info("存在parent, group Id:{}, artifactId:{}, version:{}, relativePath:{}", groupId, artifactId,
                version, relativePath);
        }
    }

    @Test
    public void analysisPomBuild() throws IOException {
        String pom = "/Users/guoguoqiang/.m2/repository/com/cloud/cloud-build/1.0.1-SNAPSHOT/cloud-build-1.0.1-SNAPSHOT.pomPath";
        Document document = Jsoup.parse(new File(pom), "utf-8");
        Elements modules = document.select("module");
        if (modules.size() > 0) {
            for (Element el: modules) {
                log.info("module:{}", el.text());
            }
        }
    }

    @Test
    public void pomUtilsTest() throws IOException {
        Pom pom = new Pom();
        String source = "/Users/guoguoqiang/.m2/repository/com/cloud/cloud-build/1.0.1-SNAPSHOT/cloud-build-1.0.1-SNAPSHOT.pomPath";
        pom = PomUtils.analysisPom(source, pom);
        log.info("pomPath analysis result:{}", pom);
    }

    @Test
    public void pomUtilsTest2() throws IOException {
        Pom pom = new Pom();
        String source = "/Users/guoguoqiang/.m2/repository/com/cloud/cloud-service/1.0.1-SNAPSHOT/cloud-service-1.0.1-SNAPSHOT.pomPath";
        pom = PomUtils.analysisPom(source, pom);
        log.info("pomPath analysis result:{}", pom);
    }

}
