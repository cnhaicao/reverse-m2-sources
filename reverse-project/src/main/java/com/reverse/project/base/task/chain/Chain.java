package com.reverse.project.base.task.chain;


import com.reverse.project.base.task.Command;
import org.apache.commons.chain.impl.ChainBase;

/**
 * 职责链(用于包装自定义命令对象)
 *
 * @author guoguoqiang
 * @since 2020年07月03日
 */
public class Chain extends ChainBase {

    /**
     * 自定义的添加命令 委托给ChaingBase的addCommand
     * @param command 自定义命令对象
     */
    public void addCommand(Command command) {
        ChainCommand chainCommand = new ChainCommand(command);
        super.addCommand(chainCommand);
    }

}
