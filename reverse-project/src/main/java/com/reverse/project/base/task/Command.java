package com.reverse.project.base.task;

/**
 * 自定义命令对象
 *
 * @author guoguoqiang
 * @since 2020年07月03日
 */
public interface Command<T extends Context> {
    /**
     * 命令执行接口
     * @param context 上下文对象
     * @return 处理是否完成 true已完成(结束职责链)
     * @throws Exception 执行异常时抛出
     */
    boolean execute(T context) throws Exception;
}
