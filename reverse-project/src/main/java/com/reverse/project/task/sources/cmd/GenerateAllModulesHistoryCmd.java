package com.reverse.project.task.sources.cmd;

import com.google.common.collect.Sets;
import com.reverse.project.base.task.AbstractTaskCommand;
import com.reverse.project.task.sources.context.ReverseSourceContext;
import com.reverse.project.task.sources.vo.SourceVO;
import com.reverse.project.utils.MapKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * 收集pom.xml所有<module>模块 以便用于判断模块是否是别人的子模块
 * 输出middle.allModuleHistory
 *
 * @author guoguoqiang
 * @since 2020年07月09日
 */
@Slf4j
@Component
public class GenerateAllModulesHistoryCmd extends AbstractTaskCommand<ReverseSourceContext> {
    @Override
    public boolean exec(ReverseSourceContext context) throws Exception {
        Map<String, SourceVO> sourceMap = context.getMiddle().getSourceMap();
        Set<String> allModuleHistory = Sets.newConcurrentHashSet();

        sourceMap.values().parallelStream().forEach(s -> s.getPom().getModules().forEach(m -> {
            String key = MapKeyUtil.mapKey(s.getGroupId(), m, s.getVersion());
            allModuleHistory.add(key);
        }));

        context.getMiddle().setAllModuleHistory(allModuleHistory);
        return false;
    }
}
