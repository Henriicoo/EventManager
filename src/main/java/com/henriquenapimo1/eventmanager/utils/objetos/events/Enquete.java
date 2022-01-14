package com.henriquenapimo1.eventmanager.utils.objetos.events;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.CustomMessages;
import com.henriquenapimo1.eventmanager.utils.Utils;
import com.henriquenapimo1.eventmanager.utils.objetos.CmdContext;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class Enquete {

    private final String pergunta;
    private final List<String> alternativas = new ArrayList<>();
    private final List<UUID> playerVotes = new ArrayList<>();
    private final HashMap<String,Integer> results = new HashMap<>();
    private boolean started = false;

    private int taskId;

    public Enquete(String pergunta) {
        this.pergunta = pergunta;
    }

    public boolean hasStarted() {
        return started;
    }

    public boolean addAlternativa(String alternativa) {
        if(alternativas.size() == 4) return false;

        alternativas.add(alternativa);
        return true;
    }

    public boolean vote(Player p, String c) {
        if(playerVotes.contains(p.getUniqueId())) return false;
        playerVotes.add(p.getUniqueId());

        int i = results.get(c);
        results.replace(c,i+1);

        return true;
    }

    public boolean iniciar() {
        if(alternativas.size() < 2) return false;
        getAlternativas().forEach(a -> results.put(a,0));
        this.started = true;

        anunciar();
        return true;
    }

    private void anunciar() {
     taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), () ->
                     Bukkit.getOnlinePlayers().forEach(p -> p.spigot().sendMessage(Utils.getAnuncio(this).create())),
             0,Utils.getInt("anunciar-enquete")*20L);
    }

    public void finalizar(boolean staffOnly) {
        Bukkit.getScheduler().cancelTask(taskId);

        String perm = "";
        String aviso = "";
        if(staffOnly) {
            perm = "eventmanager.admin";
            aviso = " §7(Somente para admins)";
        }

        StringBuilder sb = new StringBuilder(Utils.getPref(CmdContext.CommandType.ENQUETE)+" "+CustomMessages.getString("events.enquete.finalizar") + aviso);
        results.forEach((key,value) ->
                sb.append("\n").append(CustomMessages.getString("events.enquete.opcao", key.toUpperCase(), value.toString() + " votos")));

        String finalPerm = perm;
        Bukkit.getOnlinePlayers().forEach(p -> {
            if(p.hasPermission(finalPerm)) {
                p.sendMessage(sb.toString());
            }
        });

        Main.getMain().enquete = null;
    }

    public String getPergunta() {
        return pergunta;
    }

    public HashMap<String,String> getOpcoes() {
        HashMap<String,String> hm = new HashMap<>();

        for(int i = 0; i < getAlternativas().size(); i++) {
            hm.put(getAlternativas().get(i),alternativas.get(i));
        }
        return hm;
    }

    public List<String> getAlternativas() {
        List<String> c = new ArrayList<>();
        switch (alternativas.size()) {
            case 1: {
                c.add("a");
                return c;
            }
            case 2: {
                c.addAll(Arrays.asList("a","b"));
                return c;
            }
            case 3: {
                c.addAll(Arrays.asList("a","b","c"));
                return c;
            }
            case 4: {
                c.addAll(Arrays.asList("a","b","c","d"));
                return c;
            }
        }
        return c;
    }
}
