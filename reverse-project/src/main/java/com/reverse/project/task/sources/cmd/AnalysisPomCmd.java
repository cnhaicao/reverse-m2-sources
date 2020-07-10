package com.reverse.project.task.sources.cmd;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.reverse.project.base.task.AbstractTaskCommand;
import com.reverse.project.constants.ReverseFailEnum;
import com.reverse.project.exception.ParentPomException;
import com.reverse.project.task.sources.context.ReverseSourceContext;
import com.reverse.project.task.sources.vo.ErrorSourceVO;
import com.reverse.project.task.sources.vo.Pom;
import com.reverse.project.task.sources.vo.SourceVO;
import com.reverse.project.utils.JsonCloneUtils;
import com.reverse.project.utils.MapKeyUtil;
import com.reverse.project.utils.PomUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 解析pom.xml
 * 输入：middle.sourceMap
 * 输出：output.errorSources
 * @author guoguoqiang
 * @since 2020年07月08日
 */
@Slf4j
@Component
public class AnalysisPomCmd extends AbstractTaskCommand<ReverseSourceContext> {

    @SuppressWarnings("Duplicates")
    @Override
    public boolean exec(ReverseSourceContext context) throws Exception {
        Map<String, SourceVO> sourceMap = context.getMiddle().getSourceMap();
        Map<String, SourceVO> pomSuccessMap = Maps.newHashMap();
        if (MapUtils.isEmpty(sourceMap)) {
            log.error("The list of files to be reversed is empty.");
            return true;
        }
        List<ErrorSourceVO> errorSources = Lists.newArrayList();
        sourceMap.forEach((k, v) -> {
            try {
                Pom pom = JsonCloneUtils.cloneFrom(v, Pom.class);
                PomUtils.analysisPom(v.getPomPath(), pom);
                analysisParentPom(pom, sourceMap);
                v.setPom(pom);
                pomSuccessMap.put(k, v);
            } catch (Exception e) {
                ErrorSourceVO errorSource = JsonCloneUtils.cloneFrom(v, ErrorSourceVO.class);
                if (errorSource == null) {
                    errorSource = new ErrorSourceVO();
                }
                if (e instanceof ParentPomException) {
                    errorSource.setFailEnum(ReverseFailEnum.FAIL_NOT_EXISTS_PARENT_POM);
                } else {
                    errorSource.setFailEnum(ReverseFailEnum.FAIL_POM);
                }
                errorSources.add(errorSource);
            }
        });
        context.getMiddle().setSourceMap(pomSuccessMap);
        context.getOutput().setErrorSources(errorSources);
        return false;
    }

    /**
     * 递归解析parent pom.xml
     * 可尽早知道是否有缺失的父pom
     * @param pom       pom
     * @param sourceMap sourceMap
     * @throws ParentPomException 父pom不存在或解析异常时抛出
     */
    private void analysisParentPom(Pom pom, Map<String, SourceVO> sourceMap) throws ParentPomException {
        if (pom == null || pom.getParent() == null) {
            return;
        }
        Pom parent = pom.getParent();
        String parentKey = MapKeyUtil.mapKey(parent.getGroupId(), parent.getArtifactId(), parent.getVersion());
        if (!pom.getVersion().equals(parent.getVersion())) {
            log.debug("pom version:{} is different from parent version:{}, parent is not resolved.", MapKeyUtil.mapKey(pom.getGroupId(), pom.getArtifactId(), pom.getVersion()), parentKey);
            return;
        }
        try {
            SourceVO parentSource = sourceMap.get(parentKey);
            if (parentSource == null) {
                throw new ParentPomException("parent pom not exists:" + parentKey);
            }
            PomUtils.analysisPom(parentSource.getPomPath(), parent);
            analysisParentPom(parent, sourceMap);
        } catch (Exception e) {
            log.error("parent pom error," + e.getMessage());
            throw new ParentPomException(e.getMessage(), e);
        }
    }
}
