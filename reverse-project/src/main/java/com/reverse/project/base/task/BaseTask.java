package com.reverse.project.base.task;


import com.reverse.project.base.task.chain.Chain;
import com.reverse.project.base.task.chain.ChainContext;

import java.util.Date;

/**
 * 任务执行抽象类
 *
 * @author guoguoqiang
 * @since 2020年07月03日
 */
public abstract class BaseTask<T extends TaskContext> implements Task<T> {

    private Chain chain = new Chain();

    @Override
    public void addCommand(Command command) {
        chain.addCommand(command);
    }

    /**
     * 执行命令
     * @param context 上下文对象
     * @return 执行是否成功
     * @throws Exception 执行异常时抛出
     */
    @Override
    public boolean execute(T context) throws Exception {
        boolean result = false;
        try {
            beforeExecute(context);
            result = doExecute(context);
            afterExecute(context);
        } catch (Exception e) {
            checkException(context, e);
        }
        context.setResult(result);
        return context.getResult();
    }

    protected void beforeExecute(T context) throws Exception {
        context.setBeginTime(new Date());
    }

    protected boolean doExecute(T context) throws Exception {
        ChainContext chainContext = new ChainContext(context);
        return chain.execute(chainContext);
    }

    protected void afterExecute(T context) {
        context.setEndTime(new Date());
    }

    protected void checkException(T context, Exception e) throws Exception {
        context.setResult(false);
        if (e instanceof TaskRuntimeException) {
            throw e;
        } else {
            throw new TaskException(e.getMessage(), e);
        }
    }
}
