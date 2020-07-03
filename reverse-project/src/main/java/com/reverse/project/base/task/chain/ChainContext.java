package com.reverse.project.base.task.chain;

import com.reverse.project.base.task.Context;
import org.apache.commons.chain.impl.ContextBase;

/**
 * 职责链上下文包装(包装出自己的上下文对象)
 *
 * @author guoguoqiang
 * @since 2020年07月03日
 */
public class ChainContext extends ContextBase {

    private static final long serialVersionUID = 548946520273628193L;
    private Context context;

    public ChainContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

}
