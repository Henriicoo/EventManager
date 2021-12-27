package com.henriquenapimo1.eventmanager.commands.admin;

import com.henriquenapimo1.eventmanager.utils.CmdContext;
import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.Evento;
import com.henriquenapimo1.eventmanager.utils.Utils;
import com.henriquenapimo1.eventmanager.utils.gui.InventoryGUIs;
import org.bukkit.Bukkit;

public class CriarCommand {

    public CriarCommand(CmdContext ctx) {

        if(ctx.getArgs().length < 3) {
            ctx.reply("§cErro! Argumentos requeridos: §7/evento criar [nome] [prêmio]");
            return;
        }

        if(Main.getMain().getEvento() != null) {
            ctx.reply("§cErro! Você não pode criar um evento enquanto já tem um acontecendo.");
            return;
        }

        int i;

        try {
            i = Integer.parseInt(ctx.getArg(ctx.getArgs().length-1));
        } catch (Exception e) {
            ctx.reply("§cErro! Você precisa colocar um valor como prêmio.");
            return;
        }

        if(i > Utils.getInt("max-premio")) {
            ctx.reply("§cErro! O prêmio excede o valor máximo ("+Utils.getInt("max-premio")+")");
            return;
        }

        Evento evento = new Evento(String.join(" ",ctx.getArgs())
                .replace(String.valueOf(i),"")
                .replace("criar ","")
                .replaceAll(".$", "")
                ,ctx.getSender().getLocation(),i);

        Main.getMain().setEvento(evento);
        InventoryGUIs.setStaffHotbar(ctx.getSender());
        evento.addSpectator(ctx.getSender());

        ctx.reply("§6Evento criado com sucesso! §eUse /evento anunciar para anunciar o evento à todos.");

        Bukkit.getOnlinePlayers().forEach(p -> {
            if(p.hasPermission("eventmanager.admin") && !p.equals(ctx.getSender()) || p.hasPermission("eventmanager.mod") && !p.equals(ctx.getSender())) {
                p.sendMessage(Utils.getPref() + " §7Um novo evento foi criado! Use §f/evento entrar §7para entrar no evento como um espectador.");
            }
        });
    }
}
