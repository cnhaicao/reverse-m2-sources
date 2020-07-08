package com.reverse.project.utils;

/**
 * map key
 *
 * @author guoguoqiang
 * @since 2020年07月08日
 */
public class MapKeyUtil {
    public static final String SPLITTER = "$";

    public static String mapKey(String groupId, String artifactId, String version) {
        return groupId + SPLITTER + artifactId + SPLITTER + version;
    }

}
