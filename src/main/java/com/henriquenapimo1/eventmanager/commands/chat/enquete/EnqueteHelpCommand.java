package com.henriquenapimo1.eventmanager.commands.chat.enquete;

import com.henriquenapimo1.eventmanager.utils.Utils;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class EnqueteHelpCommand {

    public EnqueteHelpCommand(CmdContext ctx) {
        ComponentBuilder b = new ComponentBuilder(Utils.getPref(CmdContext.CommandType.ENQUETE) + " §6§lMenu de ajuda Enquete");
        b.append("\n§a/enquete help §7- §eMostra o menu de ajuda;");
        b.append("\n§a/enquete votar [a/b/c/d] §7- §eVota numa opção disponível da enquete;");

        if(ctx.getSender().hasPermission("eventmanager.loteria.criar")) {
            b.append("\n§a§lComandos de Criação§7:");
            b.append("\n§a/enquete criar [pergunta] §7- §eCria uma Enquete com a pergunta inserida;");
            b.append("\n§a/enquete add [opção] §7- §eAdiciona uma opção de voto na enquete;");
            b.append("\n§a/enquete iniciar §7- §eInicia a Enquete; (Necessário no mínimo duas opções)");
            b.append("\n§a/enquete finalizar <-s> §7- §eFinaliza a Enquete, caso a flag -s seja inserida, o resultado só aparecerá para admins.");
        }

        ctx.getSender().spigot().sendMessage(b.create());
    }
}
