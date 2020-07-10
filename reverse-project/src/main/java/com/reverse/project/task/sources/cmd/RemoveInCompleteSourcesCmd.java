package com.reverse.project.task.sources.cmd;

import com.google.common.collect.Maps;
import com.reverse.project.base.task.AbstractTaskCommand;
import com.reverse.project.constants.ReverseFailEnum;
import com.reverse.project.task.sources.context.ReverseSourceContext;
import com.reverse.project.task.sources.vo.ErrorSourceVO;
import com.reverse.project.task.sources.vo.ModuleVO;
import com.reverse.project.utils.MapKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 移除不完整的源码包
 * 输入：middle.moduleMap
 * 输出：middle.moduleMap、output.errorSources
 * @author guoguoqiang
 * @since 2020年07月09日
 */
@Slf4j
@Component
public class RemoveInCompleteSourcesCmd extends AbstractTaskCommand<ReverseSourceContext> {
    @Override
    public boolean exec(ReverseSourceContext context) throws Exception {
        Map<String, ModuleVO> moduleMap = context.getMiddle().getModuleMap();
        Map<String, ModuleVO> successModuleMap = Maps.newConcurrentMap();
        List<ErrorSourceVO> errorSources = context.getOutput().getErrorSources();
        final Set<String> allModuleHistory = context.getMiddle().getAllModuleHistory();
        moduleMap.forEach((k, v) -> {
            String moduleKey = MapKeyUtil.mapKey(v.getGroupId(), v.getArtifactId(), v.getVersion());
            if (v.hasParentPom() && !allModuleHistory.contains(moduleKey)) {
                ErrorSourceVO errorSource = buildErrorSource(v, ReverseFailEnum.FAIL_UN_MATCH_IN_MODULE);
                errorSources.add(errorSource);
            } else if (!ModuleVO.checkAllModulesMatch(v)) {
                ErrorSourceVO errorSource = buildErrorSource(v, ReverseFailEnum.FAIL_MODULE_MISS);
                errorSources.add(errorSource);
            } else {
                successModuleMap.put(k, v);
            }
        });
        context.getMiddle().setModuleMap(successModuleMap);
        context.getOutput().setErrorSources(errorSources);
        return false;
    }

    private ErrorSourceVO buildErrorSource(ModuleVO module, ReverseFailEnum failEnum) {
        ErrorSourceVO errorSource = new ErrorSourceVO();
        BeanUtils.copyProperties(module, errorSource);
        errorSource.setFailEnum(failEnum);
        return errorSource;
    }
}
