package com.henriquenapimo1.eventmanager.commands;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class InfoCommand {

    public InfoCommand(CmdContext ctx) {
        ComponentBuilder b = new ComponentBuilder("§6§lEventManager §7- Versão §f" + Main.getMain().getDescription().getVersion());
        b.append(" §7Criado por: §fHenriqueNapimo1");
        b.event(new ClickEvent(ClickEvent.Action.OPEN_URL,"https://github.com/HenriqueNapimo1/EventManager"));

        ctx.getSender().spigot().sendMessage(b.create());
    }
}
