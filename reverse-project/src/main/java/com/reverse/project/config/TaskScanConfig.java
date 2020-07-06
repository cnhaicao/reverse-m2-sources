package com.reverse.project.config;

import com.reverse.project.base.task.annotation.TaskChainBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * task scan
 *
 * @author guoguoqiang
 * @since 2020年07月06日
 */
@Configuration
public class TaskScanConfig {
    @Bean
    public TaskChainBeanPostProcessor taskChainBeanPostProcessor() {
        return new TaskChainBeanPostProcessor();
    }
}
