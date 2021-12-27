package com.henriquenapimo1.eventmanager.commands.admin;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.Evento;
import com.henriquenapimo1.eventmanager.utils.CmdContext;
import com.henriquenapimo1.eventmanager.utils.Utils;
import org.bukkit.Bukkit;

public class TrancarCommand {

    public TrancarCommand(CmdContext ctx) {
        Evento evento = Main.getMain().getEvento();

        if(evento == null) {
            ctx.reply("§7Não há nenhum evento acontecendo no momento!");
            return;
        }

        if(evento.isLocked()) {
            ctx.reply("§aEvento §ndestrancado§r§a com sucesso!");
            Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(Utils.getPref() + " §aO evento foi destrancado! Use §f/evento entrar §apara entrar no evento"));
        } else {
            ctx.reply("§cEvento §ntrancado§r§c com sucesso!");
            Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(Utils.getPref() + " §cO evento foi trancado! Novos jogadores não poderão entrar no evento."));
        }

        evento.lockEvent(!evento.isLocked());
    }
}
