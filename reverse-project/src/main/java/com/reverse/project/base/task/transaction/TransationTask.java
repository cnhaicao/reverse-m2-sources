package com.reverse.project.base.task.transaction;


import com.reverse.project.base.task.Command;
import com.reverse.project.base.task.Task;

/**
 * 带事务任务
 * 用于将同组任务所有命令放在同一个数据库事务中
 *
 * @author guoguoqiang
 * @since 2020年07月03日
 */
public interface TransationTask extends Task {

    void addPreCommand(Command command);

    void addPostCommand(Command command);
}
