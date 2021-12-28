package com.henriquenapimo1.eventmanager.commands.chat.bolao;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;

public class BolaoFinalizarCommand {

    public BolaoFinalizarCommand(CmdContext ctx) {
        if(Main.getMain().bolao == null) {
            ctx.reply("§7Não há nenhum bolão acontecendo no momento para finalizar!");
            return;
        }

        Main.getMain().bolao.finalizar();
        ctx.reply("§7Bolão finalizado com sucesso");
    }
}
