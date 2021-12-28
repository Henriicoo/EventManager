package com.henriquenapimo1.eventmanager.commands;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.Utils;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class ReloadCommand {

    public ReloadCommand(CmdContext ctx) {

        if (ctx.getArgs().length == 1) {
            ComponentBuilder b = new ComponentBuilder(Utils.getPref() + " §cIsso irá reiniciar o plugin e irá recarregar " +
                    "todas as configurações, cancelando todos os eventos ativos no momento.");
            b.append("\n§7Você tem certeza? ");
            b.append(new ComponentBuilder("§c[Confirmar]")
                     .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new Text("§7Clique para confirmar")))
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/eventmanager reload confirm"))
                    .create());

            ctx.getSender().spigot().sendMessage(b.create());
            return;
        }

        if (ctx.getArg(1).equalsIgnoreCase("confirm")) {

            Main.getMain().onDisable();
            Main.getMain().onEnable();

            Main.getMain().reloadConfig();
            Main.getMain().saveConfig();

            ctx.reply("§aConfigurações recarregadas!");
            Main.getMain().getLogger().info(String.format("[%s] Plugin recarregado com sucesso.", Main.getMain().getDescription().getName()));
        } else {
            ctx.reply("§7Argumento inválido!");
        }
    }
}
