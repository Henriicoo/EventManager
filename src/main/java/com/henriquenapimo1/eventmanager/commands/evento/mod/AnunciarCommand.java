package com.henriquenapimo1.eventmanager.commands.evento.mod;

import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.Utils;
import com.henriquenapimo1.eventmanager.utils.objetos.Evento;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

public class AnunciarCommand {

    public AnunciarCommand(CmdContext ctx) {
        Evento e = Main.getMain().evento;

        if(e == null) {
            ctx.reply("evento.no-evento", CmdContext.CommandType.EVENTO);
            return;
        }

        if(e.isLocked()) {
            ctx.reply("evento.anunciar.locked", CmdContext.CommandType.EVENTO);
            return;
        }

        ComponentBuilder msg = Utils.getAnuncio(e);

        Bukkit.getOnlinePlayers().forEach(p -> {
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 1);
            p.spigot().sendMessage(msg.create());
        });
    }
}
