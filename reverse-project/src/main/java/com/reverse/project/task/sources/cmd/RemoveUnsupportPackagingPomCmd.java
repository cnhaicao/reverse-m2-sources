package com.reverse.project.task.sources.cmd;

import com.google.common.collect.Maps;
import com.reverse.project.base.task.AbstractTaskCommand;
import com.reverse.project.constants.MavenPackagingEnum;
import com.reverse.project.constants.ReverseFailEnum;
import com.reverse.project.task.sources.context.ReverseSourceContext;
import com.reverse.project.task.sources.vo.ErrorSourceVO;
import com.reverse.project.task.sources.vo.SourceVO;
import com.reverse.project.utils.JsonCloneUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 移除pom.xml中不支持的packaging的源码
 * 源码逆向只支持maven pom.xml packaging为jar或pom
 * 输出middle.sourceMap及middle.errorSources
 * @author guoguoqiang
 * @since 2020年07月08日
 */
@Slf4j
@Component
public class RemoveUnsupportPackagingPomCmd extends AbstractTaskCommand<ReverseSourceContext> {

    @SuppressWarnings("Duplicates")
    @Override
    public boolean exec(ReverseSourceContext context) throws Exception {
        Map<String, SourceVO> sourceMap = context.getMiddle().getSourceMap();
        Map<String, SourceVO> pomSuccessMap = Maps.newHashMap();
        List<ErrorSourceVO> errorSources = context.getOutput().getErrorSources();
        if (MapUtils.isEmpty(sourceMap)) {
            log.error("需要逆向的文件列表为空");
            return true;
        }
        sourceMap.forEach((k, v) -> {
            if (MavenPackagingEnum.isValidPackagingEnum(v.getPom().getPackaging())) {
                pomSuccessMap.put(k, v);
            } else {
                ErrorSourceVO errorSource = JsonCloneUtils.cloneFrom(v, ErrorSourceVO.class);
                if (errorSource == null) {
                    errorSource = new ErrorSourceVO();
                }
                errorSource.setFailEnum(ReverseFailEnum.FAIL_NO_SUPPORT);
                errorSources.add(errorSource);
            }
        });
        context.getMiddle().setSourceMap(pomSuccessMap);
        context.getOutput().setErrorSources(errorSources);
        return false;
    }
}
