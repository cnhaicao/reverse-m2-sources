package com.reverse.project.base.task.chain;


import com.reverse.project.base.task.Command;
import org.apache.commons.chain.Context;

/**
 * 职责链命令对象包装(包装出自己的命令对象)
 *
 * @author guoguoqiang
 * @since 2020年07月03日
 */
public class ChainCommand implements org.apache.commons.chain.Command {
    private Command command;

    public ChainCommand(Command command) {
        this.command = command;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean execute(Context context) throws Exception {
        ChainContext chainContext = (ChainContext) context;
        return command.execute(chainContext.getContext());
    }
}
