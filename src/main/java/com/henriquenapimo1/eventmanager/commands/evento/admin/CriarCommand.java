package com.henriquenapimo1.eventmanager.commands.evento.admin;

import com.henriquenapimo1.eventmanager.utils.CustomMessages;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.events.Evento;
import com.henriquenapimo1.eventmanager.utils.Utils;
import com.henriquenapimo1.eventmanager.utils.gui.InventoryGUIs;
import org.bukkit.Bukkit;

public class CriarCommand {

    public CriarCommand(CmdContext ctx) {

        if(ctx.getArgs().length < 3) {
            ctx.reply("utils.args", CmdContext.CommandType.EVENTO,"/evento criar [nome] [prêmio]");
            return;
        }

        if(Main.getMain().evento != null) {
            ctx.reply("evento.criar.error", CmdContext.CommandType.EVENTO);
            return;
        }

        int i;

        try {
            i = Integer.parseInt(ctx.getArg(ctx.getArgs().length-1));
        } catch (Exception e) {
            ctx.reply("utils.not-number", CmdContext.CommandType.EVENTO,"prêmio");
            return;
        }

        if(i > Utils.getInt("max-premio-evento")) {
            ctx.reply("utils.max-premio", CmdContext.CommandType.EVENTO,String.valueOf(Utils.getInt("max-premio-evento")));
            return;
        }

        Evento evento = new Evento(String.join(" ",ctx.getArgs())
                .replace(String.valueOf(i),"")
                .replace("criar ","")
                .replaceAll(".$", "")
                ,ctx.getSender().getLocation(),i);

        Main.getMain().evento = evento;
        InventoryGUIs.setStaffHotbar(ctx.getSender());
        evento.addSpectator(ctx.getSender());

        ctx.reply("evento.criar.success", CmdContext.CommandType.EVENTO);

        Bukkit.getOnlinePlayers().forEach(p -> {
            if(p.hasPermission("eventmanager.admin") && !p.equals(ctx.getSender()) || p.hasPermission("eventmanager.mod") && !p.equals(ctx.getSender())) {
                p.sendMessage(
                        CustomMessages.getString("prefix.evento") + " " +
                                CustomMessages.getString("commands.evento.criar.anuncio-staff")
                );
            }
        });
    }
}
