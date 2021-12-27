package com.henriquenapimo1.eventmanager.commands;

import com.henriquenapimo1.eventmanager.utils.CmdContext;
import com.henriquenapimo1.eventmanager.utils.Utils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class HelpCommand {

    public HelpCommand(CmdContext ctx) {

        if(ctx.getArgs().length <= 1 || ctx.getArg(1).equals("1")) {
            ComponentBuilder b = new ComponentBuilder(Utils.getPref() + " §6§lMenu de ajuda");
            b.append("\n§a/evento help §7- §eMostra o menu de ajuda;");
            b.append("\n§a/evento entrar §7- §eEntra no evento;");
            b.append("\n§a/evento sair §7- §aSei do evento;");

            if (ctx.getSender().hasPermission("eventmanager.mod")) {
                b.append("\n§a§lComandos de Moderação§7:");
                b.append("\n§a/evento anunciar §7- §eEnvia um anúncio do evento para os jogadores online;");
                b.append("\n§a/evento bc [mensagem] §7- §eEnvia uma mensagem para todos os jogadores que estão no evento;");
                b.append("\n§a/evento ban [nick] §7- §eBane um jogador de poder entrar no evento;");
                b.append("\n§a/evento unban [nick] §7- §eDá unban em um jogador que foi banido do evento.");
            }

            if (ctx.getSender().hasPermission("eventmanager.admin")) {
                b.append(new ComponentBuilder("\n§7§lPágina 2 >>")
                        .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/evento help 2")).create());

            }
            ctx.getSender().spigot().sendMessage(b.create());

        } else if(ctx.getArg(1).equals("2") && ctx.getSender().hasPermission("eventmanager.admin")) {
            ComponentBuilder b = new ComponentBuilder("§7Página 2 | " + Utils.getPref() + " §c§lComandos de Administração§7:");
            b.append("\n§a/evento criar [nome] [prêmio] §7- §eCria um evento com o nome e prêmio definidos;");
            b.append("\n§a/evento flags §7- §eAbre o menu de flags do evento;");
            b.append("\n§a/evento trancar §7- §eTranca ou destranca o evento;");
            b.append("\n§a/evento cancelar §7- §eCancela o evento sem dar prêmio à ninguém;");
            b.append("\n§a/evento finalizar [ganhador] [ganhador]... §7- §eFinaliza o evento e dá a vitória à um ou mais jogadores separados por espaço;");
            b.append("\n§a/evento reload §7- §eRecarrega as configurações do plugin;");
            b.append("\n§a/evento darefeito §7§o(Segure a poção na mão)§r §7- §eDá os efeitos da poção selecionada aos jogadores;");
            b.append("\n§a/evento daritem §7§o(Segure o item na mão)§r §7- §eDá o item selecionado aos jogadores;");

            b.append(new ComponentBuilder("\n§7§lPágina 3 >>")
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/evento help 3")).create());

            ctx.getSender().spigot().sendMessage(b.create());
        } else if(ctx.getArg(1).equals("3") && ctx.getSender().hasPermission("eventmanager.admin"))  {
            StringBuilder b = new StringBuilder("§7Página 3 | " + Utils.getPref() + " §c§lComandos de Administração§7:");
            b.append("\n§a/evento gamemode [gamemode] §7- §eMuda o gamemode de todos os jogadores;");
            b.append("\n§a/evento itemclear §7- §eLimpa todos os itens dados aos jogadores;");
            b.append("\n§a/evento effectclear §7- §eLimpa todos os efeitos de poção dados aos jogadores;");
            b.append("\n§a/evento setspawn §7- §eSeta o spawn do evento na sua posição atual;");
            b.append("\n§a/evento setpremio [prêmio] §7- §eTroca o valor do prêmio inicial");
            b.append("\n§a/evento tphere §7- §eTeletransporta todos os jogadores na sua posição atual;");

            ctx.getSender().sendMessage(b.toString());
        } else {
            ctx.reply("§7Página não encontrada");
        }
    }
}
