package com.henriquenapimo1.eventmanager.commands.admin;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.CmdContext;
import com.henriquenapimo1.eventmanager.utils.Evento;
import com.henriquenapimo1.eventmanager.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FinalizarCommand {

    public FinalizarCommand(CmdContext ctx) {

        Evento evento = Main.getMain().getEvento();

        if(evento == null) {
            ctx.reply("§7Não há nenhum evento acontecendo no momento!");
            return;
        }

        if(ctx.getArgs().length < 2) {
            ctx.reply("§cErro! Você precisa especificar um ou mais players como vencedor: §n/evento finalizar [nick] [nick]...");
            return;
        }

        List<Player> ganhadores = new ArrayList<>();

        for (String p : ctx.getArgs()) {
            Player pl = Bukkit.getPlayer(p);

            if(pl != null)
                ganhadores.add(pl);
        }

        if(ganhadores.isEmpty()) {
            ctx.reply("§cErro! Este(s) jogador(es) não está(ão) online.");
            return;
        }

        StringBuilder aviso = new StringBuilder();
        ganhadores.forEach(j -> {
            if(!evento.getPlayers().contains(j)) {
                aviso.append("O jogador ").append(j.getName()).append(" não está participando do evento! ");
                ganhadores.remove(j);
            }
            if(j.hasPermission("eventmanager.staff") || j.hasPermission("eventmanager.mod") || j.hasPermission("eventmanager.admin")) {
                aviso.append("O jogador ").append(j.getName()).append(" é staff e não pode ser o ganhador do evento! ");
                ganhadores.remove(j);
            }
        });

        evento.getPlayers().forEach(p -> {
            if(ganhadores.contains(p)) {
                p.sendTitle("§6Você ganhou o evento!", "§eParabéns, e obrigado por jogar!",5,30,5);
                for (int i = 0; i < 5; i++) {
                    Utils.spawnFirework(p);
                }
                Main.getEconomy().depositPlayer(p, evento.getPrize());
                return;
            }

            if(ganhadores.size() > 1) {
                p.sendTitle("§6Obrigado por jogar!","",5,30,5);
            } else {
                p.sendTitle("§6Obrigado por jogar!","§eO ganhador(a) é:" + ganhadores.get(0).getName(),5,30,5);
            }

            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,10,1);
        });

        List<String> gNick = new ArrayList<>();
        ganhadores.forEach(g -> gNick.add(g.getName()));

        Bukkit.getOnlinePlayers().forEach(p -> {
            if(ganhadores.size() > 1) {
                p.sendMessage(Utils.getPref() + " §aO evento foi finalizado! Os ganhadores são §e" + String.join("§a, §e",gNick) + "§a! Parabéns!");
            } else {
                p.sendMessage(Utils.getPref() + " §aO evento foi finalizado! O ganhador(a) é §e" + ganhadores.get(0).getName() + "§a! Parabéns!");
            }
        });

        evento.finalizar();
    }
}
