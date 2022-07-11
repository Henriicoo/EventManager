package com.henriquenapimo1.eventmanager.utils.commands;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.CustomMessages;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {

    private final HashMap<String, ICommand> commands = new HashMap<>();
    private final Main main;

    public CommandManager(Main plugin) {
        this.main = plugin;

        CommandSet.getSet().forEach(c -> {
            if(c.getParent() != null) {
                commands.put(c.getParent()+"."+c.getName(),c);

                PluginCommand cmd = main.getCommand(c.getParent());
                assert cmd != null;

                cmd.setExecutor(this);
                cmd.setTabCompleter(this);
                return;
            }

            commands.put(c.getName(),c);

            PluginCommand cmd = main.getCommand(c.getName());
            assert cmd != null;

            cmd.setExecutor(this);
            cmd.setTabCompleter(this);
        });
    }

    public boolean runCommand(CmdContext ctx) {
        ICommand cmd = getMatch(ctx.getCommand().getName());

        if(ctx.getArgs().length >=1) {
            ICommand cmdArg = getMatch(ctx.getCommand().getName()+"."+ctx.getArg(0));
            if(cmdArg != null) cmd = cmdArg;
        }

        if(cmd == null) return false;

        if(cmd.getPermission() != null && !ctx.getSender().hasPermission(cmd.getPermission())) {
            ctx.reply("utils.no-permission", cmd.getType(),cmd.getPermission());
            return true;
        }

        if(ctx.getArgs().length < cmd.minArgs()) {
            ctx.reply("utils.args",cmd.getType(),cmd.getUse());
            return true;
        }

        cmd.run(ctx);
        return true;
    }

    public ICommand getMatch(String name) {
        return commands.get(name);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) { sender.sendMessage(CustomMessages.getString("commands.utils.console")); return false; }

        CmdContext ctx = new CmdContext(sender,command,args);

        if(!runCommand(ctx)) {
            sender.sendMessage("Comando não encontrado!"); return false;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return TabComplete.complete(sender, command, args);
    }

}
