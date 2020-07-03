package com.reverse.project.base.task.annotation;

import com.reverse.project.base.task.Command;
import com.reverse.project.base.task.Task;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 任务职责连注解实现
 *
 * @author guoguoqiang
 * @since 2020年07月03日
 */
public class TaskChainBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {


    private ApplicationContext applicationContext = null;


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        if (bean instanceof Task) {
            Task task = (Task) bean;
            TaskChain taskChain = bean.getClass().getAnnotation(TaskChain.class);
            if (taskChain == null) {
                return bean;
            }
            if (ArrayUtils.isNotEmpty(taskChain.value())) {
                String[] commandNames = taskChain.value();
                for (String commandName : commandNames) {
                    Command command = (Command) applicationContext.getBean(commandName);
                    task.addCommand(command);
                }
            } else if (ArrayUtils.isNotEmpty(taskChain.types())) {
                Class[] commandTypes = taskChain.types();
                for (Class commandType : commandTypes) {
                    Command command = (Command) applicationContext.getBean(commandType);
                    task.addCommand(command);
                }
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
