package com.henriquenapimo1.eventmanager.commands.admin;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.CmdContext;
import com.henriquenapimo1.eventmanager.utils.Evento;
import org.bukkit.GameMode;

public class GamemodeCommand {

    public GamemodeCommand(CmdContext ctx) {
        Evento evento = Main.getMain().getEvento();

        if(evento == null) {
            ctx.reply("§7Não há nenhum evento acontecendo no momento!");
            return;
        }

        if(ctx.getArgs().length < 2) {
            ctx.reply("§cErro! Argumento requerido: §7/evento gamemode [gamemode]");
            return;
        }

        GameMode gm;

        switch (ctx.getArg(1).toLowerCase()) {
            case "survival":
            case "s":
            case "0": {
                gm = GameMode.SURVIVAL; break;
            }
            case "creative":
            case "criativo":
            case "c":
            case "1": {
                gm = GameMode.CREATIVE; break;
            }
            case "adventure":
            case "aventura":
            case "a":
            case "2": {
                gm = GameMode.ADVENTURE; break;
            }
            case "spectator":
            case "espectador":
            case "e":
            case "3": {
                gm = GameMode.SPECTATOR; break;
            }
            default: {
                ctx.reply("§cErro! Gamemode inválido");
                return;
            }
        }

        evento.getFlags().setGamemode(gm);
        ctx.reply("§aGamemode dos jogadores alterado com sucesso!");
    }
}
