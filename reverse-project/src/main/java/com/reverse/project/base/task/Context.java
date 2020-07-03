package com.reverse.project.base.task;

import java.io.Serializable;

/**
 * 任务上下文
 * common-chain的Context继承自Map, 自定义Context可以让任务上下文具备明确的领域属性
 * @author guoguoqiang
 * @since 2020年07月03日
 */
public interface Context extends Serializable {

}
