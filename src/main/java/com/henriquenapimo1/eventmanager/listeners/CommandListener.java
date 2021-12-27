package com.henriquenapimo1.eventmanager.listeners;

import com.henriquenapimo1.eventmanager.commands.EntrarCommand;
import com.henriquenapimo1.eventmanager.commands.HelpCommand;
import com.henriquenapimo1.eventmanager.commands.SairCommand;
import com.henriquenapimo1.eventmanager.commands.admin.*;
import com.henriquenapimo1.eventmanager.commands.mod.AnunciarCommand;
import com.henriquenapimo1.eventmanager.commands.mod.BanCommand;
import com.henriquenapimo1.eventmanager.commands.mod.BroadcastCommand;
import com.henriquenapimo1.eventmanager.commands.mod.UnbanCommand;
import com.henriquenapimo1.eventmanager.utils.CmdContext;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandListener implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) { sender.sendMessage("§cVocê precisa ser um jogador para poder executar esse comando!"); return false; }

        CmdContext ctx = new CmdContext(sender, command, args);

        if(args.length==0) {
            new HelpCommand(ctx);
            return false;
        }

        // player commands
        switch (args[0]) {
            case "entrar": {
                new EntrarCommand(ctx); return false;
            }
            case "sair": {
                new SairCommand(ctx); return false;
            }
            case "help": {
                new HelpCommand(ctx); return false;
            }
        }

        // mod commands
        if(sender.hasPermission("eventmanager.mod")) {
            switch (args[0]) {
                case "anunciar": {
                    new AnunciarCommand(ctx); return false;
                }
                case "ban": {
                    new BanCommand(ctx); return false;
                }
                case "unban": {
                    new UnbanCommand(ctx); return false;
                }
                case "bc": {
                    new BroadcastCommand(ctx); return false;
                }
            }
        } else {
            sender.sendMessage("§cVocê não tem permissão! Você precisa da permissão §7eventmanager.mod §cpara poder fazer isso.");
            return false;
        }

        // admin commands
        if(sender.hasPermission("eventmanager.admin")) {
            switch (args[0]) {
                case "criar": {
                    new CriarCommand(ctx); return false;
                }
                case "daritem": {
                    new DarItemCommand(ctx); return false;
                }
                case "darefeito": {
                    new DarEfeitoCommand(ctx); return false;
                }
                case "tphere": {
                    new TphereCommand(ctx); return false;
                }
                case "trancar": {
                    new TrancarCommand(ctx); return false;
                }
                case "setspawn": {
                    new SetSpawnCommand(ctx); return false;
                }
                case "itemclear": {
                    new ItemClearCommand(ctx); return false;
                }
                case "effectclear": {
                    new EffectClearCommand(ctx); return false;
                }
                case "finalizar": {
                    new FinalizarCommand(ctx); return false;
                }
                case "reload": {
                    new ReloadCommand(ctx); return false;
                }
                case "cancelar": {
                    new CancelarCommand(ctx); return false;
                }
                case "setpremio": {
                    new SetPremioCommand(ctx); return false;
                }
                case "gamemode": {
                    new GamemodeCommand(ctx); return false;
                }
                case "flags": {
                    new FlagCommand(ctx); return false;
                }
            }
        } else {
            sender.sendMessage("§cVocê não tem permissão! Você precisa da permissão §7eventmanager.admin §cpara poder fazer isso.");
            return false;
        }

        return false;
    }
}
