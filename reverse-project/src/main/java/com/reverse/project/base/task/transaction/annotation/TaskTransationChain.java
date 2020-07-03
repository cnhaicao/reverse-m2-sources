package com.reverse.project.base.task.transaction.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 带事务职责链注解
 * 用于将同组任务所有命令放在同一个数据库事务中
 *
 * @author guoguoqiang
 * @since 2020年07月03日
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TaskTransationChain {

    String[] pre() default {};

    Class<?>[] preTypes() default {};

    String[] transation() default {};

    Class<?>[] transationTypes() default {};

    String[] post() default {};

    Class<?>[] postTypes() default {};
}
