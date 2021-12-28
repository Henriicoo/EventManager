package com.henriquenapimo1.eventmanager.commands.evento;

import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.Evento;
import com.henriquenapimo1.eventmanager.utils.gui.InventoryGUIs;

public class EntrarCommand {

    public EntrarCommand(CmdContext ctx) {

        if(!ctx.getSender().hasPermission("eventmanager.entrar")) {
            ctx.reply("§cVocê não tem permissão!");
            return;
        }

        Evento evento = Main.getMain().evento;

        if(evento == null) {
            ctx.reply("evento");
            return;
        }

        if(evento.isPlayerBanned(ctx.getSender())) {
            ctx.reply("§CVocê está banido desse evento e portanto não poderá entrar!");
            return;
        }

        if(evento.isLocked()) {
            ctx.reply("§cO evento está trancado! Você não pode mais entrar.");
            return;
        }

        if(evento.getPlayers().contains(ctx.getSender()) || evento.getSpectators().contains(ctx.getSender())) {
            ctx.reply("§7Você já está no evento!");
            return;
        }

        // se é staff, vai entrar como espectador
        if(ctx.getSender().hasPermission("eventmanager.staff") || ctx.getSender().hasPermission("eventmanager.admin") || ctx.getSender().hasPermission("eventmanager.mod")) {
            if(ctx.getSender().hasPermission("eventmanager.admin") || ctx.getSender().hasPermission("eventmanager.mod")) {
                InventoryGUIs.setStaffHotbar(ctx.getSender());
            }

            evento.addSpectator(ctx.getSender());
            return;
        }

        evento.addPlayer(ctx.getSender());
    }
}
