package com.reverse.project.base.task.transaction;

import com.reverse.project.base.task.BaseTask;
import com.reverse.project.base.task.Command;
import com.reverse.project.base.task.Context;
import com.reverse.project.base.task.TaskContext;
import com.reverse.project.base.task.TaskException;
import com.reverse.project.base.task.TaskRuntimeException;
import com.reverse.project.base.task.chain.Chain;
import com.reverse.project.base.task.chain.ChainContext;

import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 带事务任务
 * 用于将同组任务所有命令放在同一个数据库事务中
 *
 * @author guoguoqiang
 * @since 2020年07月03日
 */
public abstract class BaseTransactionTask<T extends TaskContext> implements TransationTask {

    private Chain chain = new Chain();
    private Chain preChain = new Chain();
    private Chain postChain = new Chain();


    @Override
    public void addCommand(Command command) {
        chain.addCommand(command);
    }
    @Override
    public void addPreCommand(Command command) {
        preChain.addCommand(command);
    }
    @Override
    public void addPostCommand(Command command) {
        postChain.addCommand(command);
    }

    @Override
    public boolean execute(Context context) throws Exception {

        T t = (T) context;
        beforeExecute(t);
        boolean result = false;
        try {
            doPreExecute(t);
            result = doExecute(t);
            doPostExecute(t);
        } catch (Exception e) {
            checkException(t, e);
        }
        afterExecute(t);
        return result;
    }

    @Transactional(rollbackFor = Throwable.class)
    protected boolean doExecute(T t) throws Exception {
        ChainContext context = new ChainContext(t);
        return chain.execute(context);
    }

    private boolean doPreExecute(T t) throws Exception {
        ChainContext context = new ChainContext(t);
        return preChain.execute(context);
    }

    private boolean doPostExecute(T t) throws Exception {
        ChainContext context = new ChainContext(t);
        return postChain.execute(context);
    }


    protected void beforeExecute (T context) {
        context.setBeginTime(new Date());
    }

    protected void afterExecute (T context) {
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
