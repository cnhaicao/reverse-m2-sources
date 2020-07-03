package com.reverse.project.base.task.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 任务类职责链注解
 *
 * @author guoguoqiang
 * @since 2020年07月03日
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TaskChain {

    String[] value() default {};

    Class<?>[] types() default {};
}
