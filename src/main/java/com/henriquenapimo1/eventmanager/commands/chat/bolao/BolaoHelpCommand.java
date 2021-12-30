package com.henriquenapimo1.eventmanager.commands.chat.bolao;

import com.henriquenapimo1.eventmanager.utils.Utils;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class BolaoHelpCommand {

    public BolaoHelpCommand(CmdContext ctx) {
        ComponentBuilder b = new ComponentBuilder(Utils.getPref(CmdContext.CommandType.BOLAO) + " §6§lMenu de ajuda Bolão");
        b.append("\n§a/bolao help §7- §eMostra o menu de ajuda;");
        b.append("\n§a/bolao apostar §7- §eAposta no bolão atual;");

        if(ctx.getSender().hasPermission("eventmanager.quiz.criar")) {
            b.append("\n§a§lComandos de Criação§7:");
            b.append("\n§a/bolao criar [valorInicial] §7- §eCria um Bolão customizado com o valor inicial inserido;");
            b.append("\n§a/bolao finalizar §7- §eForça a finalização do bolão atual para a criação de outro.");
        }

        ctx.getSender().spigot().sendMessage(b.create());
    }
}
