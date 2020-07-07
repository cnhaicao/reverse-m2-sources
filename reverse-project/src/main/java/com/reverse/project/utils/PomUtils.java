package com.reverse.project.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.reverse.project.constants.Constants;
import com.reverse.project.task.sources.dto.Pom;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * pom analysis util
 * base on jsoup
 * @author guoguoqiang
 * @since 2020年07月07日
 */
public class PomUtils {

    /**
     * 解析pom.xml并返回解析结果对象
     * @param source pom.xml绝对路径
     * @param pom pom对象解析后将内容填入pom对象中
     * @return Pom
     * @throws IOException 文件解析异常
     */
    public static Pom analysisPom(String source, Pom pom) throws IOException {
        if (pom == null) {
            throw new IllegalArgumentException("pom不能为null");
        }
        Document document = Jsoup.parse(new File(source), "utf-8");
        Elements parent = document.select("project>parent");
        if (parent.size() > 0) {
            String groupId = getValueFromElements(parent.select("groupId"));
            String artifactId = getValueFromElements(parent.select("artifactId"));
            String version = getValueFromElements(parent.select("version"));
            if (StringUtils.isNotBlank(version)
                && version.contains(Constants.POM_VAR_SYNBOL)) {
                version = pom.getVersion();
            }
            String relativePath = getValueFromElements(parent.select("relativePath"));
            Pom parentPom = Pom.builder().groupId(groupId).artifactId(artifactId).version(version)
                .relativePath(relativePath).build();
            pom.setParent(parentPom);
        }
        Elements packaging = document.select("project>packaging");
        pom.setPackaging(getValueFromElements(packaging));
        Elements name = document.select("project>name");
        pom.setName(getValueFromElements(name));
        Elements description = document.select("project>description");
        pom.setDescription(getValueFromElements(description));
        pom.setModules(analysisModules(document));
        return pom;
    }

    private static String getValueFromElements(Elements el) {
        if (el == null || el.size() == 0) {
            return null;
        }
        return el.text() != null ? el.text() : el.val();
    }

    private static List<String> analysisModules(Document document) {
        Set<String> sets = Sets.newHashSet();
        Elements modules = document.select("module");
        // module去重
        if (modules.size() > 0) {
            for (Element el: modules) {
                sets.add(el.text() != null ? el.text() : el.val());
            }
        }
        return Lists.newArrayList(sets);
    }
}
