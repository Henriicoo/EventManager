package com.henriquenapimo1.eventmanager.commands.mod;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.Evento;
import com.henriquenapimo1.eventmanager.utils.CmdContext;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class UnbanCommand {

    public UnbanCommand(CmdContext ctx) {
        Evento evento = Main.getMain().getEvento();

        if(evento == null) {
            ctx.reply("§7Não há nenhum evento acontecendo no momento!");
            return;
        }

        if(ctx.getArgs().length < 2) {
            ctx.reply("§cErro! Você precisa especificar um player: §7/evento unban [nick]");
            return;
        }

        Player p = Bukkit.getPlayer(ctx.getArg(1));
        if(p == null) {
            ctx.reply("§cErro! Este jogador não está online.");
            return;
        }

        evento.unbanPlayer(p);
    }
}
