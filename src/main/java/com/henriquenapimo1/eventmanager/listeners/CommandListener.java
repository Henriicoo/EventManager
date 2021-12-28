package com.henriquenapimo1.eventmanager.listeners;

import com.henriquenapimo1.eventmanager.commands.HelpCommand;
import com.henriquenapimo1.eventmanager.commands.InfoCommand;
import com.henriquenapimo1.eventmanager.commands.ReloadCommand;
import com.henriquenapimo1.eventmanager.commands.chat.bolao.ApostarCommand;
import com.henriquenapimo1.eventmanager.commands.chat.bolao.BolaoCriarCommand;
import com.henriquenapimo1.eventmanager.commands.chat.bolao.BolaoFinalizarCommand;
import com.henriquenapimo1.eventmanager.commands.chat.bolao.BolaoHelpCommand;
import com.henriquenapimo1.eventmanager.commands.chat.quiz.QuizCriarCommand;
import com.henriquenapimo1.eventmanager.commands.chat.quiz.QuizHelpCommand;
import com.henriquenapimo1.eventmanager.commands.chat.quiz.QuizRespostaCommand;
import com.henriquenapimo1.eventmanager.commands.chat.quiz.SetRespostaCommand;
import com.henriquenapimo1.eventmanager.commands.chat.vouf.VoufCriarCommand;
import com.henriquenapimo1.eventmanager.commands.chat.vouf.VoufFinalizarCommand;
import com.henriquenapimo1.eventmanager.commands.chat.vouf.VoufHelpCommand;
import com.henriquenapimo1.eventmanager.commands.chat.vouf.VoufRespostaCommand;
import com.henriquenapimo1.eventmanager.commands.evento.EntrarCommand;
import com.henriquenapimo1.eventmanager.commands.evento.EventoHelpCommand;
import com.henriquenapimo1.eventmanager.commands.evento.SairCommand;
import com.henriquenapimo1.eventmanager.commands.evento.admin.*;
import com.henriquenapimo1.eventmanager.commands.evento.mod.AnunciarCommand;
import com.henriquenapimo1.eventmanager.commands.evento.mod.BanCommand;
import com.henriquenapimo1.eventmanager.commands.evento.mod.BroadcastCommand;
import com.henriquenapimo1.eventmanager.commands.evento.mod.UnbanCommand;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandListener implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) { sender.sendMessage("§cVocê precisa ser um jogador para poder executar esse comando!"); return false; }

        CmdContext ctx = new CmdContext(sender, command, args);
        switch (command.getName().toLowerCase()) {
            case "evento": eventoCommands(ctx); return true;
            case "quiz": quizCommands(ctx); return true;
            case "vouf": voufCommands(ctx); return true;
            case "eventmanager": eventManagerCommands(ctx); return true;
            case "bolao": bolaoCommands(ctx); return true;
        }

        return false;
    }

    private void eventManagerCommands(CmdContext ctx) {
        if(ctx.getArgs().length==0) {
            new HelpCommand(ctx);
            return;
        }
        switch (ctx.getArg(0)) {
            case "help": new HelpCommand(ctx); return;
            case "reload": {
                if(ctx.getSender().hasPermission("eventmanager.admin")) {
                    new ReloadCommand(ctx);
                    return;
                }
                ctx.getSender().sendMessage("§cVocê não tem permissão! Você precisa da permissão §7eventmanager.admin §cpara poder fazer isso.");
            } return;
            case "info": new InfoCommand(ctx);
        }
    }

    private void eventoCommands(CmdContext ctx) {
        String[] args = ctx.getArgs();
        Player sender = ctx.getSender();

        if(args.length==0) {
            new EventoHelpCommand(ctx);
            return;
        }

        // player commands
        switch (args[0]) {
            case "entrar": {
                new EntrarCommand(ctx); return;
            }
            case "sair": {
                new SairCommand(ctx); return;
            }
            case "help": {
                new EventoHelpCommand(ctx); return;
            }
        }

        // mod commands
        if(sender.hasPermission("eventmanager.mod")) {
            switch (args[0]) {
                case "anunciar": {
                    new AnunciarCommand(ctx); return;
                }
                case "ban": {
                    new BanCommand(ctx); return;
                }
                case "unban": {
                    new UnbanCommand(ctx); return;
                }
                case "bc": {
                    new BroadcastCommand(ctx); return;
                }
            }
        } else {
            sender.sendMessage("§cVocê não tem permissão! Você precisa da permissão §7eventmanager.mod §cpara poder fazer isso.");
            return;
        }

        // admin commands
        if(sender.hasPermission("eventmanager.admin")) {
            switch (args[0].toLowerCase()) {
                case "criar": {
                    new CriarCommand(ctx); return;
                }
                case "daritem": {
                    new DarItemCommand(ctx); return;
                }
                case "darefeito": {
                    new DarEfeitoCommand(ctx); return;
                }
                case "tphere": {
                    new TphereCommand(ctx); return;
                }
                case "trancar": {
                    new TrancarCommand(ctx); return;
                }
                case "setspawn": {
                    new SetSpawnCommand(ctx); return;
                }
                case "itemclear": {
                    new ItemClearCommand(ctx); return;
                }
                case "effectclear": {
                    new EffectClearCommand(ctx); return;
                }
                case "finalizar": {
                    new FinalizarCommand(ctx); return;
                }
                case "reload": {
                    new ReloadCommand(ctx); return;
                }
                case "cancelar": {
                    new CancelarCommand(ctx); return;
                }
                case "setpremio": {
                    new SetPremioCommand(ctx); return;
                }
                case "gamemode": {
                    new GamemodeCommand(ctx); return;
                }
                case "flags": {
                    new FlagCommand(ctx);
                }
            }
        } else {
            sender.sendMessage("§cVocê não tem permissão! Você precisa da permissão §7eventmanager.admin §cpara poder fazer isso.");
        }
    }

    private void quizCommands(CmdContext ctx) {

        if(ctx.getArgs().length == 0) {
            new QuizHelpCommand(ctx);
            return;
        }

        switch (ctx.getArg(0).toLowerCase()) {
            case "help": new QuizHelpCommand(ctx); return;
            case "resposta": new QuizRespostaCommand(ctx); return;
        }

        if(ctx.getSender().hasPermission("eventmanager.quiz.criar")) {
            switch (ctx.getArg(0).toLowerCase()) {
                case "criar":
                    new QuizCriarCommand(ctx);
                    break;
                case "setresposta":
                    new SetRespostaCommand(ctx);
                    break;
            }
        } else {
            ctx.reply("§cVocê não tem permissão! Você precisa da permissão §7eventmanager.quiz.criar §cpara poder fazer isso.");
        }
    }

    private void voufCommands(CmdContext ctx) {
        if(ctx.getArgs().length == 0) {
            new VoufHelpCommand(ctx);
            return;
        }

        switch (ctx.getArg(0).toLowerCase()) {
            case "help": new VoufHelpCommand(ctx); return;
            case "resposta": new VoufRespostaCommand(ctx); return;
        }

        if(ctx.getSender().hasPermission("eventmanager.vouf.criar")) {
            switch (ctx.getArg(0).toLowerCase()) {
                case "criar":
                    new VoufCriarCommand(ctx);
                    break;
                case "finalizar":
                    new VoufFinalizarCommand(ctx);
                    break;
            }
        } else {
            ctx.reply("§cVocê não tem permissão! Você precisa da permissão §7eventmanager.vouf.criar §cpara poder fazer isso.");
        }
    }

    private void bolaoCommands(CmdContext ctx) {
        if(ctx.getArgs().length == 0) {
            new BolaoHelpCommand(ctx);
            return;
        }

        switch (ctx.getArg(0).toLowerCase()) {
            case "help": new BolaoHelpCommand(ctx); return;
            case "apostar": new ApostarCommand(ctx); return;
        }

        if(ctx.getSender().hasPermission("eventmanager.bolao.criar")) {
            switch (ctx.getArg(0).toLowerCase()) {
                case "criar":
                    new BolaoCriarCommand(ctx);
                    break;
                case "finalizar":
                    new BolaoFinalizarCommand(ctx);
                    break;
            }
        } else {
            ctx.reply("§cVocê não tem permissão! Você precisa da permissão §7eventmanager.bolao.criar §cpara poder fazer isso.");
        }
    }
}
