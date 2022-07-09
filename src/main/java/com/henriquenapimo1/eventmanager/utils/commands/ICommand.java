package com.henriquenapimo1.eventmanager.utils.commands;

import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;

public interface ICommand {

    String getName();

    String getDescription();

    String getUse();

    default String getParent() {
        return null;
    }

    default String[] getArgs() {
        return null;
    }

    default int minArgs() {
        return 0;
    }

    CmdContext.CommandType getType();

    default String getPermission() {
        return null;
    }

    void run(CmdContext ctx);

}
