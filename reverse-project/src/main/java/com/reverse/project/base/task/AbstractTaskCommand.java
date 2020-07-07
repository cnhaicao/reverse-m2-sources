package com.reverse.project.base.task;

/**
 * 职责链抽象命令对象
 *
 * @author guoguoqiang
 * @since 2020年07月03日
 */
public abstract class AbstractTaskCommand<T extends TaskContext> implements Command<T> {

    @Override
    public boolean execute(T context) throws Exception {
        boolean result;
        preExec(context);
        result = exec(context);
        postExec(context);
        return result;
    }

    public void preExec(T context) {

    }

    public void postExec(T context) {

    }

    /**
     * 具体命令执行业务 由子类实现
     * @param context context
     * @return 处理是否完成 true已完成(结束职责链)
     * @throws Exception 业务异常时抛出
     */
    public abstract boolean exec(T context) throws Exception;

}
