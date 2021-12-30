package com.henriquenapimo1.eventmanager.utils;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.objetos.*;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class Utils {

    private final static FileConfiguration config = Main.getMain().getConfig();

    public static String getPref(CmdContext.CommandType type) {
        String pref = "";
        switch (type) {
            case MAIN: pref = CustomMessages.getString("prefix.plugin");break;
            case EVENTO: pref = CustomMessages.getString("prefix.evento"); break;
            case QUIZ: pref = CustomMessages.getString("prefix.quiz"); break;
            case VOUF: pref = CustomMessages.getString("prefix.vouf"); break;
            case BOLAO: pref = CustomMessages.getString("prefix.bolao"); break;
            case LOTERIA: pref = CustomMessages.getString("prefix.loteria"); break;
        }

        return pref;
    }

    public static String getString(String path) {
        //noinspection ConstantConditions
        return ChatColor.translateAlternateColorCodes('&', config.getString(path));
    }

    public static int getInt(String path) {
        return config.getInt(path);
    }

    public static boolean getBool(String path) {
        return config.getBoolean(path);
    }

    public static List<?> getList(String path) {
        return config.getList(path);
    }

    // anúncio Evento
    public static ComponentBuilder getAnuncio(Evento e) {
        ComponentBuilder msg = new ComponentBuilder("§7 \n" + Utils.getPref(CmdContext.CommandType.EVENTO) + " " +
                CustomMessages.getString("events.evento.anuncio",e.getName(),String.valueOf(e.getPrize())));

        msg.append(new ComponentBuilder("\n" +
                CustomMessages.getString("events.evento.button"))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new Text("§7Clique para entrar no evento")))
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/evento entrar"))
                .create());
        msg.append("\n§7 ");
        return msg;
    }

    // anúncio Quiz
    public static ComponentBuilder getAnuncio(Quiz q) {
        ComponentBuilder msg = new ComponentBuilder("§7 \n" + Utils.getPref(CmdContext.CommandType.QUIZ) + " " +
                CustomMessages.getString("events.quiz.anuncio",String.valueOf(q.getPremio())));

        msg.append("\n§7"+q.getPergunta());

        msg.append(new ComponentBuilder("\n" +
                CustomMessages.getString("events.quiz.button"))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new Text("§7Clique para responder")))
                .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"/quiz resposta "))
                .create());
        msg.append("\n§7 ");
        return msg;
    }

    // anúncio VouF
    public static ComponentBuilder getAnuncio(Vouf v) {
        ComponentBuilder msg = new ComponentBuilder("§7 \n" + Utils.getPref(CmdContext.CommandType.VOUF) + " " +
                CustomMessages.getString("events.vouf.anuncio",String.valueOf(v.getPremio())));

        msg.append("\n§7"+v.getPergunta());

        msg.append(new ComponentBuilder("\n" + CustomMessages.getString("events.vouf.true"))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new Text("§7Clique para marcar como VERDADEIRO")))
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/vouf resposta true"))
                .create());
        msg.append(new ComponentBuilder(" " + CustomMessages.getString("events.vouf.false"))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new Text("§7Clique para marcar como FALSO")))
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/vouf resposta false"))
                .create());
        msg.append("\n§7 ");
        return msg;
    }

    // anúncio Bolão
    public static ComponentBuilder getAnuncio(Bolao b) {
        ComponentBuilder msg = new ComponentBuilder("§7 \n" + Utils.getPref(CmdContext.CommandType.BOLAO) + " " +
                CustomMessages.getString("events.bolao.anuncio",String.valueOf(b.getValorAcumulado())));

        msg.append(new ComponentBuilder("\n" + CustomMessages.getString("events.bolao.button"))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new Text("§7Clique para apostar no bolão")))
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/bolao apostar"))
                .create());
        msg.append("\n§7 ");
        return msg;
    }

    // anúncio Loteria
    public static ComponentBuilder getAnuncio(Loteria l) {
        ComponentBuilder msg = new ComponentBuilder("§7 \n" + Utils.getPref(CmdContext.CommandType.LOTERIA) + " " +
                CustomMessages.getString("events.loteria.anuncio",String.valueOf(l.getPremio()),String.valueOf(l.getMaxNumero())));

        msg.append(new ComponentBuilder("\n" + CustomMessages.getString("events.loteria.button"))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new Text("§7Clique para fazer uma aposta")))
                .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"/loteria apostar "))
                .create());
        msg.append("\n§7 ");
        return msg;
    }

    // https://bukkit.org/threads/spawn-firework.118019/
    public static void spawnFirework(Player p, int quanto) {
        new BukkitRunnable() {
            int vezes = 0;
            @Override
            public void run() {
                if(vezes==quanto) {
                    cancel();
                    return;
                }

                Firework fw = (Firework) p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
                FireworkMeta fwm = fw.getFireworkMeta();

                Random r = new Random();
                int rt = r.nextInt(5) + 1;
                FireworkEffect.Type type = FireworkEffect.Type.BALL;
                if (rt == 2) type = FireworkEffect.Type.BALL_LARGE;
                if (rt == 3) type = FireworkEffect.Type.BURST;
                if (rt == 4) type = FireworkEffect.Type.CREEPER;
                if (rt == 5) type = FireworkEffect.Type.STAR;

                int r1i = r.nextInt(17) + 1;
                int r2i = r.nextInt(17) + 1;
                Color c1 = getColor(r1i);
                Color c2 = getColor(r2i);

                FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();
                fwm.addEffect(effect);

                int rp = r.nextInt(2) + 1;
                fwm.setPower(rp);
                fw.setFireworkMeta(fwm);

                vezes = vezes+1;
            }
        }.runTaskTimer(Main.getMain(),0,10);
    }

    // https://bukkit.org/threads/spawn-firework.118019/
    private static Color getColor(int i) {
        Color c = null;
        if(i==1){
            c=Color.AQUA;
        }
        if(i==2){
            c=Color.BLACK;
        }
        if(i==3){
            c=Color.BLUE;
        }
        if(i==4){
            c=Color.FUCHSIA;
        }
        if(i==5){
            c=Color.GRAY;
        }
        if(i==6){
            c=Color.GREEN;
        }
        if(i==7){
            c=Color.LIME;
        }
        if(i==8){
            c=Color.MAROON;
        }
        if(i==9){
            c=Color.NAVY;
        }
        if(i==10){
            c=Color.OLIVE;
        }
        if(i==11){
            c=Color.ORANGE;
        }
        if(i==12){
            c=Color.PURPLE;
        }
        if(i==13){
            c=Color.RED;
        }
        if(i==14){
            c=Color.SILVER;
        }
        if(i==15){
            c=Color.TEAL;
        }
        if(i==16){
            c=Color.WHITE;
        }
        if(i==17){
            c=Color.YELLOW;
        }

        return c;
    }
}
