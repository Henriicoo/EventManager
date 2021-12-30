package com.henriquenapimo1.eventmanager.commands.chat.loteria;

import com.henriquenapimo1.eventmanager.utils.Utils;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class LoteriaHelpCommand {

    public LoteriaHelpCommand(CmdContext ctx) {
        ComponentBuilder b = new ComponentBuilder(Utils.getPref(CmdContext.CommandType.LOTERIA) + " §6§lMenu de ajuda Loteria");
        b.append("\n§a/loteria help §7- §eMostra o menu de ajuda;");
        b.append("\n§a/loteria apostar [número] §7- §eAposta um número na loteria atual;");

        if(ctx.getSender().hasPermission("eventmanager.loteria.criar")) {
            b.append("\n§a§lComandos de Criação§7:");
            b.append("\n§a/loteria criar [prêmio] [número/random] §7- §eCria uma Loteria customizado com o prêmio inserido e o número premiado inserido, ou aleatório (caso random);");
        }

        ctx.getSender().spigot().sendMessage(b.create());
    }
}
