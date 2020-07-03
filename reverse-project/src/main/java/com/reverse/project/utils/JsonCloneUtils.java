package com.reverse.project.utils;

import com.fasterxml.jackson.databind.JavaType;

import java.util.List;
import java.util.Map;

/**
 * 通过json实现对像深克隆
 * 要求被克隆的源属性对像没有循环对像结构
 * @author guoguoqiang
 * @since 2020/06/24
 */
public class JsonCloneUtils {

    /**
     * 要求被克隆的类不能有循环对像结构
     * 支持深克隆
     * @param source 原对像
     * @param clazz  转换目标类
     * @param <T>    返回类型
     * @return 返回目标类的实例
     */
    public static <T> T cloneFrom(Object source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        String content = JsonBinder.buildNormalBinder().toJson(source);
        return JsonBinder.buildNormalBinder().fromJson(content, clazz);
    }

    /**
     * List列表对像克隆转换
     * 支持深克隆
     * @param source 原对像
     * @param type   List里的转换目标类
     * @param <X>    List里的对像返回类型
     * @return List<X>
     */
    public static <X> List<X> cloneForList(Object source, final Class<X> type) {
        if (source == null) {
            return null;
        }
        JsonBinder jsonBinder = JsonBinder.buildNormalBinder();
        String content = jsonBinder.toJson(source);
        JavaType javaType = jsonBinder.getCollectionType(List.class, type);
        return jsonBinder.fromJson(content, javaType);
    }

    /**
     * Map对像深克隆
     *
     * @param source 被克隆的源map对像
     * @param type   源map key对应的类型
     * @param type2  源map value对应的类型
     * @return Map<X,Y>
     */
    public static <X, Y> Map<X, Y> cloneForMap(Object source, final Class<X> type, final Class<Y> type2) {
        if (source == null) {
            return null;
        }
        JsonBinder jsonBinder = JsonBinder.buildNormalBinder();
        String content = jsonBinder.toJson(source);
        JavaType javaType = jsonBinder.getCollectionType(Map.class, type, type2);
        return jsonBinder.fromJson(content, javaType);
    }

}
