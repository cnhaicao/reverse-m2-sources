package com.reverse.project.base.task;

/**
 * 任务接口
 *
 * @author guoguoqiang
 * @since 2020年07月03日
 */
public interface Task<T extends Context> {
    /**
     * 往任务添加命令
     * @param command 命令对象
     */
    void addCommand(Command command);

    /**
     * 执行命令
     * @param context 上下文对象
     * @return 执行是否成功
     * @throws Exception 执行异常时抛出
     */
    boolean execute(T context) throws Exception;
}
