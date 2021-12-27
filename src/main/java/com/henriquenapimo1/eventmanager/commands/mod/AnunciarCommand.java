package com.henriquenapimo1.eventmanager.commands.mod;

import com.henriquenapimo1.eventmanager.utils.CmdContext;
import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.Utils;
import com.henriquenapimo1.eventmanager.utils.Evento;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

public class AnunciarCommand {

    public AnunciarCommand(CmdContext ctx) {
        Evento e = Main.getMain().getEvento();

        if(e == null) {
            ctx.reply("§7Não há nenhum evento para anunciar.");
            return;
        }

        ComponentBuilder msg = Utils.getAnuncio(e);

        Bukkit.getOnlinePlayers().forEach(p -> {
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 1);
            p.spigot().sendMessage(msg.create());
        });
    }
}
