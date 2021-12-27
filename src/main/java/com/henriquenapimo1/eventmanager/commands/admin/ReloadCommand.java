package com.henriquenapimo1.eventmanager.commands.admin;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.CmdContext;

public class ReloadCommand {

    public ReloadCommand(CmdContext ctx) {

        Main.getMain().onDisable();

        Main.getMain().onEnable();

        Main.getMain().reloadConfig();
        Main.getMain().saveConfig();

        ctx.reply("§aConfigurações recarregadas!");
        Main.getMain().getLogger().info(String.format("[%s] Plugin recarregado com sucesso.",Main.getMain().getDescription().getName()));
    }
}
