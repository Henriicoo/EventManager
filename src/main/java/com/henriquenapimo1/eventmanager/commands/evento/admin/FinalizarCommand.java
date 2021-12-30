package com.henriquenapimo1.eventmanager.commands.evento.admin;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.CustomMessages;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import com.henriquenapimo1.eventmanager.utils.objetos.Evento;
import com.henriquenapimo1.eventmanager.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FinalizarCommand {

    public FinalizarCommand(CmdContext ctx) {

        Evento evento = Main.getMain().evento;

        if(evento == null) {
            ctx.reply("evento.no-evento", CmdContext.CommandType.EVENTO);
            return;
        }

        if(ctx.getArgs().length < 2) {
            ctx.reply("args", CmdContext.CommandType.EVENTO,"/evento finalizar [nick] [nick]...");
            return;
        }

        List<Player> ganhadores = new ArrayList<>();

        for (String p : ctx.getArgs()) {
            Player pl = Bukkit.getPlayer(p);

            if(pl != null)
                ganhadores.add(pl);
        }

        if(ganhadores.isEmpty()) {
            ctx.reply("evento.finalizar.error.offline", CmdContext.CommandType.EVENTO);
            return;
        }

        StringBuilder aviso = new StringBuilder();
        ganhadores.forEach(j -> {
            if(!evento.getPlayers().contains(j)) {
                aviso.append(
                        CustomMessages.getString("commands.evento.finalizar.error.not-playing").replace("{0}",j.getName())
                );
                ganhadores.remove(j);
            }
            if(j.hasPermission("eventmanager.staff") || j.hasPermission("eventmanager.mod") || j.hasPermission("eventmanager.admin")) {
                aviso.append(
                        CustomMessages.getString("commands.evento.finalizar.error.staff").replace("{0}",j.getName())
                );
                ganhadores.remove(j);
            }
        });

        ctx.replyText(aviso.toString(), CmdContext.CommandType.EVENTO);

        if(ganhadores.isEmpty()) {
            return;
        }

        evento.getPlayers().forEach(p -> {
            if(ganhadores.contains(p)) {
                p.sendTitle(
                        CustomMessages.getString("commands.evento.finalizar.ganhador.title"),
                        CustomMessages.getString("commands.evento.finalizar.ganhador.subtitle"),5,30,5);
                p.sendMessage(
                        CustomMessages.getString("commands.evento.finalizar.ganhador.message")
                                .replace("{0}",String.valueOf(evento.getPrize()))
                );
                Utils.spawnFirework(p,5);

                Main.getEconomy().depositPlayer(p, evento.getPrize());
                return;
            }

            p.sendTitle(CustomMessages.getString("commands.evento.finalizar.players.title"),
                    CustomMessages.getString("commands.evento.finalizar.players.subtitle"),5,30,5);

            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,10,1);
        });

        List<String> gNick = new ArrayList<>();
        ganhadores.forEach(g -> gNick.add(g.getName()));

        Bukkit.getOnlinePlayers().forEach(p -> {
            if(ganhadores.size() > 1) {
                p.sendMessage(
                        CustomMessages.getString("prefix.evento") + " " +
                                CustomMessages.getString("commands.finalizar.anuncio.more")
                                        .replace("{0}",String.join("§a, §e",gNick))
                );
            } else {
                p.sendMessage(
                        CustomMessages.getString("prefix.evento") + " " +
                                CustomMessages.getString("commands.finalizar.anuncio.one")
                                        .replace("{0}",ganhadores.get(0).getName())
                );
            }
        });

        evento.finalizar();
    }
}
