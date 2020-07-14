package com.reverse.project.task.sources.cmd;

import com.google.common.collect.Maps;
import com.reverse.project.base.task.AbstractTaskCommand;
import com.reverse.project.constants.ReverseFailEnum;
import com.reverse.project.exception.ParentPomException;
import com.reverse.project.task.sources.context.ReverseSourceContext;
import com.reverse.project.task.sources.vo.ErrorSourceVO;
import com.reverse.project.task.sources.vo.ModuleVO;
import com.reverse.project.task.sources.vo.Pom;
import com.reverse.project.task.sources.vo.SourceVO;
import com.reverse.project.utils.JsonCloneUtils;
import com.reverse.project.utils.MapKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 项目模块结构分析
 * 输入：middle.sourceMap
 * 输出：middle.moduleMap
 * @author guoguoqiang
 * @since 2020年07月08日
 */
@Slf4j
@Component
public class ProjectAnalysisCmd extends AbstractTaskCommand<ReverseSourceContext> {

    @Override
    public boolean exec(ReverseSourceContext context) throws Exception {
        final Map<String, SourceVO> sourceMap = context.getMiddle().getSourceMap();
        List<ErrorSourceVO> errorSources = context.getOutput().getErrorSources();
        Map<String, ModuleVO> moduleMap = Maps.newHashMap();
        sourceMap.forEach((k, v) -> {
            try {
                ModuleVO module = JsonCloneUtils.cloneFrom(v, ModuleVO.class);
                loadSubModule(module, sourceMap);
                if (module != null && module.getParent() == null) {
                    moduleMap.put(k, module);
                }
            } catch (Exception e) {
                log.error("load failed:{}, error:{}", k, e.getMessage(), e);
                ErrorSourceVO errorSource = new ErrorSourceVO();
                BeanUtils.copyProperties(v, errorSource);
                errorSource.setFailEnum(ReverseFailEnum.FAIL_ANALYSIS_SOURCE);
                errorSource.setReverseFailDescription(ReverseFailEnum.FAIL_ANALYSIS_SOURCE.getName() + ":" + e.getMessage());
                errorSources.add(errorSource);
            }
        });
        context.getMiddle().setModuleMap(moduleMap);
        context.getOutput().setErrorSources(errorSources);
        return false;
    }

    /**
     * 按pom递归加载父模块及所有子模块
     * @param module module
     * @param sourceMap sourceMap
     */
    private void loadSubModule(ModuleVO module, Map<String, SourceVO> sourceMap) {
        if (module == null) {
            return;
        }
        boolean allModuleMatch = true;
        Pom pom = module.getPom();
        // 加载父模块
        if (module.getParent() == null) {
            Optional<SourceVO> parentSource = sourceMap.values().parallelStream()
                .filter(s -> s.getGroupId().equals(pom.getGroupId())
                    && s.getVersion().equals(pom.getVersion())
                    && s.getPom().getModules().contains(pom.getArtifactId())
                    // 跳过父模块是当前模块时
                    && !s.getPom().getArtifactId().equals(pom.getArtifactId())
                    ).findFirst();
            if (parentSource.isPresent()) {
                ModuleVO parent = JsonCloneUtils.cloneFrom(parentSource.get(), ModuleVO.class);
                if (parent != null) {
                    module.setParent(parent);
                    //loadSubModule(parent, sourceMap);
                }
            }
        }
        if (CollectionUtils.isEmpty(module.getPom().getModules())) {
            return;
        }
        // 加载子模块
        for (String moduleArtifactId: module.getPom().getModules()) {
            String subModuleKey = MapKeyUtil.mapKey(pom.getGroupId(), moduleArtifactId, pom.getVersion());
            // 子模块是当前模块时跳过加载
            if (moduleArtifactId.equals(module.getArtifactId())) {
                continue;
            }
            if (sourceMap.containsKey(subModuleKey)) {
                ModuleVO subModule = JsonCloneUtils.cloneFrom(sourceMap.get(subModuleKey), ModuleVO.class);
                if (subModule != null) {
                    module.addSubModule(subModule);
                    subModule.setParent(module);
                    loadSubModule(subModule, sourceMap);
                }
            } else {
                allModuleMatch = false;
            }
        }
        module.setMatch(allModuleMatch);
    }

}
