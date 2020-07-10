package com.reverse.project.task.sources.vo;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 项目模块
 * 每个模块会持有父模块和所有子模块
 * 根级模块为独立项目
 * @author guoguoqiang
 * @since 2020年07月08日
 */
@Data
@ToString(exclude = {"parent"})
public class ModuleVO extends SourceVO implements Serializable {
    private static final long serialVersionUID = 2823321417352789087L;

    /**
     * 父模块
     */
    private ModuleVO parent;

    /**
     * 项目模块结构 每个是一个待生成源码的独立项目
     */
    private List<ModuleVO> modules;

    /**
     * 当前该模块源码是否存在
     */
    private Boolean match;

    /**
     * 模块生成目录
     */
    private String moduleGenerateDir;

    public void addSubModule(ModuleVO module) {
        if (modules == null) {
            modules = Lists.newArrayList();
        }
        modules.add(module);
    }

    /**
     * 检查当前模块所有子模块match均为true
     * @return true均存在 false有不存在的module
     */
    public static boolean checkAllModulesMatch(ModuleVO module) {
        boolean result;
        if (CollectionUtils.isEmpty(module.getModules()) || module.getMatch() == null) {
            return true;
        }
        for (ModuleVO subModule: module.getModules()) {
            result = checkAllModulesMatch(subModule) && Boolean.TRUE.equals(module.getMatch());
            if (!result) {
                return false;
            }
        }
        return true;
    }

    /**
     * 当前模块(含子模块)是否java sources.jar包
     * @param module module
     * @return true是sources.jar包 false: pom包
     */
    public static boolean moduleIsSources(ModuleVO module) {
        boolean result;
        if (StringUtils.isNotBlank(module.getSourcesPath())) {
            return true;
        }
        if (CollectionUtils.isEmpty(module.getModules())) {
            return false;
        }
        for (ModuleVO subModule: module.getModules()) {
            result = moduleIsSources(subModule);
            if (result) {
                return true;
            }
        }
        return false;
    }
}
