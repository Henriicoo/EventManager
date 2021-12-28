package com.henriquenapimo1.eventmanager.commands.chat.bolao;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.ChatEventManager;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;

public class BolaoCriarCommand {

    public BolaoCriarCommand(CmdContext ctx) {

        if(Main.getMain().bolao != null) {
            ctx.reply("§7Você não pode criar um bolão enquanto há outro acontecendo! Use /bolao finalizar caso você queira forçar o fim do bolão atual.");
            return;
        }

        if(ctx.getArgs().length == 1) {
            ctx.reply("§7Argumento inválido! Uso: /bolao criar [valorInicial]");
            return;
        }

        int i;

        try {
            i = Integer.parseInt(ctx.getArg(1));
        } catch (Exception e) {
            ctx.reply("§cErro! Você precisa colocar um número como valor inicial.");
            return;
        }

        ChatEventManager.iniciarBolao(i);
        ctx.reply("§aBolão iniciado com sucesso!");
    }
}
