package com.henriquenapimo1.eventmanager.utils.objetos;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.CustomMessages;
import com.henriquenapimo1.eventmanager.utils.Utils;
import com.henriquenapimo1.eventmanager.utils.gui.Itens;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Evento {

    private final String name;
    private Location spawn;
    public int prize;
    private boolean locked = false;

    private final List<Player> players = new ArrayList<>();
    private final List<Player> spectators = new ArrayList<>();
    private final HashMap<Player, PlayerOld> playerOldSettings = new HashMap<>();
    private final List<UUID> bannedPlayers = new ArrayList<>();

    private final List<ItemStack> itens = new ArrayList<>();
    private final List<PotionEffect> effects = new ArrayList<>();

    private final Flags flags = new Flags(this);

    private int TaskId;

    public Evento(String nome, Location spawn, int prize) {
        this.name = nome;
        this.prize = prize;
        this.spawn = spawn;

        if(Utils.getBool("auto-anunciar"))
            anunciar();
    }

    public void broadcast(String msg) {
        players.forEach(player -> player.sendMessage(
                CustomMessages.getString("events.evento.broadcast.players",
                        name, ChatColor.translateAlternateColorCodes('&',msg))));
        spectators.forEach(player -> player.sendMessage(CustomMessages.getString("events.evento.broadcast.players",
                name, ChatColor.translateAlternateColorCodes('&',msg))));

        playsound(Sound.BLOCK_NOTE_BLOCK_PLING);
    }

    public void broadcastStaff(String msg) {
        Bukkit.getOnlinePlayers().forEach(p -> {
            if(p.hasPermission("eventmanager.admin") || p.hasPermission("eventmanager.mod")) {
                p.sendMessage(
                        CustomMessages.getString("events.evento.broadcast.staff",name,msg)
                );}});
        playsoundStaff(Sound.BLOCK_NOTE_BLOCK_PLING);
    }

    private void playsound(Sound s) {
        players.forEach(p -> p.playSound(p.getLocation(),s,10,1));
    }

    private void playsoundStaff(Sound s) {
        Bukkit.getOnlinePlayers().forEach(p -> {
            if(p.hasPermission("eventmanager.admin") || p.hasPermission("eventmanager.mod")) {
                p.playSound(p.getLocation(),s,10,1);
            }
        });
    }

    public void addPlayer(Player p) {
        players.add(p);
        flags.addPlayerFlags(p);

        p.teleport(spawn);

        // salva o inv
        playerOldSettings.put(p, new PlayerOld(p));
        p.getInventory().clear();

        // adiciona os itens especiais
        p.getActivePotionEffects().forEach(e -> p.removePotionEffect(e.getType()));
        p.getInventory().setItem(8, Itens.getItem(Material.BARRIER,"§c§lSair do Evento","§7Clique direito para sair do evento"));

        itens.forEach(p.getInventory()::addItem);
        effects.forEach(p::addPotionEffect);

        // mensagens
        p.sendMessage("§7 \n"+ CustomMessages.getString("events.evento.join.itens") +"\n§7 ");
        p.sendTitle(CustomMessages.getString("events.evento.join.title",name),
                CustomMessages.getString("events.evento.join.subtitle"),5,30,5);

        broadcast(CustomMessages.getString("events.evento.join.message",p.getDisplayName()));
        playsound(Sound.BLOCK_NOTE_BLOCK_PLING);
    }

    public void addDeadPlayer(Player p) {
        flags.addPlayerFlags(p);
        p.teleport(spawn);

        p.getInventory().clear();
        p.getInventory().setItem(8, Itens.getItem(Material.BARRIER,"§c§lSair do Evento","§7Clique direito para sair do evento"));

        itens.forEach(p.getInventory()::addItem);
        effects.forEach(p::addPotionEffect);

        p.sendMessage(CustomMessages.getString("events.evento.respawn"));
        playsound(Sound.BLOCK_NOTE_BLOCK_PLING);
    }

    public void removePlayer(Player p, boolean ban) {
        players.remove(p);

        playerOldSettings.get(p).restaurar();
        playerOldSettings.remove(p);

        if(ban) {
            p.sendMessage(CustomMessages.getString("events.evento.leave.ban"));
            bannedPlayers.add(p.getUniqueId());
        }

        broadcast(CustomMessages.getString("events.evento.leave.message",p.getDisplayName()));
    }

    public void banPlayer(Player p) {
        removePlayer(p,true);
        broadcastStaff(CustomMessages.getString("events.evento.ban",p.getDisplayName()));
        playsoundStaff(Sound.BLOCK_NOTE_BLOCK_BASS);
    }

    public void unbanPlayer(Player p) {
        bannedPlayers.remove(p.getUniqueId());
        broadcastStaff(CustomMessages.getString("events.evento.unban",p.getDisplayName()));
        playsoundStaff(Sound.BLOCK_NOTE_BLOCK_BASS);
    }

    public void darItem(ItemStack item) {
        itens.add(item);
        players.forEach(p -> p.getInventory().addItem(item));
        broadcast(CustomMessages.getString("events.evento.recebido","item"));
        playsound(Sound.ENTITY_ITEM_PICKUP);
    }

    public void itemClear() {
        itens.clear();
        players.forEach(p -> {
            p.getInventory().clear();
            p.getInventory().setItem(8, Itens.getItem(Material.BARRIER,"§c§lSair do Evento","§7Clique direito para sair do evento"));
        });
    }

    public void darEfeito(List<PotionEffect> effects) {
        this.effects.addAll(effects);

        players.forEach(p -> p.addPotionEffects(effects));
        broadcast(CustomMessages.getString("events.evento.recebido","efeito de poção"));
        playsound(Sound.ENTITY_ITEM_PICKUP);
    }

    public void effectClear() {
        effects.clear();
        players.forEach(p -> p.getActivePotionEffects().forEach(e -> p.removePotionEffect(e.getType())));
    }

    public void teleportAll(Location loc) {
        players.forEach(p -> p.teleport(loc));
        broadcast(CustomMessages.getString("events.evento.teleport"));
        playsound(Sound.BLOCK_NOTE_BLOCK_BIT);
    }

    public void setSpawn(Location loc) {
        this.spawn = loc;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void lockEvent(boolean locked) {
        this.locked = locked;
        if(locked) {
            Bukkit.getScheduler().cancelTask(TaskId);
        } else {
            anunciar();
        }
    }

    public void addSpectator(Player p) {
        this.spectators.add(p);
        this.playerOldSettings.put(p,new PlayerOld(p));

        p.teleport(spawn);
        p.getInventory().setItem(8, Itens.getItem(Material.BARRIER,"§c§lSair do Evento","§7Clique direito para sair do evento"));

        p.sendMessage("§7 \n"+ CustomMessages.getString("events.evento.join.spectator") +"\n§7 ");
        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,100000,1,false,false));
    }

    public void removeSpectator(Player p) {
        spectators.remove(p);

        playerOldSettings.get(p).restaurar();
        playerOldSettings.remove(p);
    }

    void setGamemode(GameMode gm) {
        players.forEach(p -> p.setGameMode(gm));
        broadcast(CustomMessages.getString("events.evento.gamemode"));
        playsound(Sound.BLOCK_NOTE_BLOCK_BIT);
    }

    public void finalizar() {
        Bukkit.getScheduler().cancelTask(TaskId);

        if(players.isEmpty()) {
            if (!spectators.isEmpty()) {
                AtomicInteger i = new AtomicInteger();
                spectators.forEach(s -> {
                    i.getAndIncrement();

                    playerOldSettings.get(s).restaurar();
                    playerOldSettings.remove(s);

                    if (i.get() == spectators.size()) {
                        Main.getMain().evento = null;
                    }
                });
            }
            Main.getMain().evento = null;
            return;
        }

        AtomicInteger i = new AtomicInteger();

        players.forEach(p -> {
            i.getAndIncrement();

            playerOldSettings.get(p).restaurar();
            playerOldSettings.remove(p);

            if(i.get()==players.size()) {
                i.set(0);
                spectators.forEach(s -> {
                    i.getAndIncrement();

                    playerOldSettings.get(s).restaurar();
                    playerOldSettings.remove(s);

                    if(i.get()==spectators.size()) {
                        Main.getMain().evento = null;
                    }
                });
            }
        });
    }

    private void anunciar() {
        this.TaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), () ->
                Bukkit.getOnlinePlayers().forEach(p ->
                        p.spigot().sendMessage(Utils.getAnuncio(this).create())),
                0, Utils.getInt("anunciar-evento")* 20L);
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean isPlayerBanned(Player p) {
        return bannedPlayers.contains(p.getUniqueId());
    }

    public String getName() {
        return name;
    }

    public int getPrize() {
        return prize;
    }

    public Flags getFlags() {
        return flags;
    }

    public List<Player> getPlayers() {
        return players;
    }
    public List<Player> getSpectators() {
        return spectators;
    }
    public List<UUID> getBannedPlayers() {
        return bannedPlayers;
    }
}
