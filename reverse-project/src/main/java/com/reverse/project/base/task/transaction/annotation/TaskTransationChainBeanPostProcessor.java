package com.reverse.project.base.task.transaction.annotation;

import com.reverse.project.base.task.Command;
import com.reverse.project.base.task.transaction.TransationTask;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 带事务任务职责任注解实现
 * 用于将同组任务所有命令放在同一个数据库事务中
 *
 * @author guoguoqiang
 * @since 2020年07月03日
 */
public class TaskTransationChainBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {


    private static ApplicationContext applicationContext = null;


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        if (bean instanceof TransationTask) {

            TransationTask task = (TransationTask) bean;
            TaskTransationChain taskChain = bean.getClass().getAnnotation(TaskTransationChain.class);
            if (taskChain == null) {
                return bean;
            }

            if (ArrayUtils.isNotEmpty(taskChain.pre())) {
                String[] commandNames = taskChain.pre();
                for (String commandName : commandNames) {
                    Command command = (Command) applicationContext.getBean(commandName);
                    task.addPreCommand(command);
                }
            } else if (ArrayUtils.isNotEmpty(taskChain.preTypes())) {
                Class[] commandTypes = taskChain.preTypes();
                for (Class commandType : commandTypes) {
                    Command command = (Command) applicationContext.getBean(commandType);
                    task.addPreCommand(command);
                }
            }

            if (ArrayUtils.isNotEmpty(taskChain.transation())) {
                String[] commandNames = taskChain.transation();
                for (String commandName : commandNames) {
                    Command command = (Command) applicationContext.getBean(commandName);
                    task.addCommand(command);
                }
            } else if (ArrayUtils.isNotEmpty(taskChain.transationTypes())) {
                Class[] commandTypes = taskChain.transationTypes();
                for (Class commandType : commandTypes) {
                    Command command = (Command) applicationContext.getBean(commandType);
                    task.addCommand(command);
                }
            }

            if (ArrayUtils.isNotEmpty(taskChain.post())) {
                String[] commandNames = taskChain.post();
                for (String commandName : commandNames) {
                    Command command = (Command) applicationContext.getBean(commandName);
                    task.addPostCommand(command);
                }
            } else if (ArrayUtils.isNotEmpty(taskChain.postTypes())) {
                Class[] commandTypes = taskChain.postTypes();
                for (Class commandType : commandTypes) {
                    Command command = (Command) applicationContext.getBean(commandType);
                    task.addPostCommand(command);
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
