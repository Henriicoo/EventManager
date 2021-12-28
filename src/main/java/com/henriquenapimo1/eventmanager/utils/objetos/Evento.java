package com.henriquenapimo1.eventmanager.utils.objetos;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.Utils;
import com.henriquenapimo1.eventmanager.utils.gui.InventoryGUIs;
import com.henriquenapimo1.eventmanager.utils.gui.Itens;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
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
    private final HashMap<Player, Map.Entry<Inventory, Location>> playerOldSettings = new HashMap<>();
    private final List<UUID> bannedPlayers = new ArrayList<>();

    private final List<ItemStack> itens = new ArrayList<>();
    private final List<PotionEffect> effects = new ArrayList<>();

    private final Flags flags = new Flags(this);

    private int bcTaskId;

    public Evento(String nome, Location spawn, int prize) {
        this.name = nome;
        this.prize = prize;
        this.spawn = spawn;

        if(Utils.getBool("auto-anunciar"))
            anunciar();
    }

    public void broadcast(String msg) {
        players.forEach(player -> player.sendMessage("§8[§6" + name + "§8]§f " + ChatColor.translateAlternateColorCodes('&',msg)));
        spectators.forEach(player -> player.sendMessage("§8[§6" + name + " §7Players §8]§f " + ChatColor.translateAlternateColorCodes('&',msg)));

        playsound(Sound.BLOCK_NOTE_BLOCK_PLING);
    }

    public void broadcastStaff(String msg) {
        Bukkit.getOnlinePlayers().forEach(p -> {
            if(p.hasPermission("eventmanager.admin") || p.hasPermission("eventmanager.mod")) {
                p.sendMessage("§8[§6EventManager §eStaff§8] §f" + msg);
            }
        });
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
        playerOldSettings.put(p, new AbstractMap.SimpleEntry<>(p.getInventory(),p.getLocation()));
        p.getInventory().clear();

        // adiciona os itens especiais
        p.getActivePotionEffects().forEach(e -> p.removePotionEffect(e.getType()));
        p.getInventory().setItem(8, Itens.getItem(Material.BARRIER,"§c§lSair do Evento","§7Clique direito para sair do evento"));

        itens.forEach(p.getInventory()::addItem);
        effects.forEach(p::addPotionEffect);

        // mensagens
        p.sendMessage("§7 \n §7Não se preocupe! Seu inventário foi salvo e seus itens não serão perdidos.\n§7 ");
        p.sendTitle("§6Evento " + name,"§eSeja bem-vindo(a) ao evento!",5,30,5);

        broadcast("§a" + p.getDisplayName() + "§e entrou no evento!");
        playsound(Sound.BLOCK_NOTE_BLOCK_PLING);
    }

    public void addDeadPlayer(Player p) {
        flags.addPlayerFlags(p);
        p.teleport(spawn);

        p.getInventory().clear();
        p.getInventory().setItem(8, Itens.getItem(Material.BARRIER,"§c§lSair do Evento","§7Clique direito para sair do evento"));

        itens.forEach(p.getInventory()::addItem);
        effects.forEach(p::addPotionEffect);

        p.sendMessage("§aVocê reespawnou!");
        playsound(Sound.BLOCK_NOTE_BLOCK_PLING);
    }

    public void removePlayer(Player p, boolean ban) {
        players.remove(p);

        // restaura o inv e dá clear nos efeitos
        p.getInventory().clear();
        p.getInventory().setContents(playerOldSettings.get(p).getKey().getContents());
        p.getActivePotionEffects().forEach(e -> p.removePotionEffect(e.getType()));

        p.teleport(playerOldSettings.get(p).getValue());

        playerOldSettings.remove(p);

        if(ban) {
            p.sendMessage("§cVocê foi removido do evento por um staff! Você não poderá entrar novamente.");
            bannedPlayers.add(p.getUniqueId());
        }

        broadcast("§f" + p.getDisplayName() + "§7 saiu do evento");
    }

    public void banPlayer(Player p) {
        removePlayer(p,true);
        broadcastStaff("§cO jogador §4" + p.getDisplayName() + " §cfoi banido do evento!");
        playsoundStaff(Sound.BLOCK_NOTE_BLOCK_BASS);
    }

    public void unbanPlayer(Player p) {
        bannedPlayers.remove(p.getUniqueId());
        broadcastStaff("§aO jogador §7" + p.getDisplayName() + " §afoi desbanido do evento!");
        playsoundStaff(Sound.BLOCK_NOTE_BLOCK_BASS);
    }

    public void darItem(ItemStack item) {
        itens.add(item);
        players.forEach(p -> p.getInventory().addItem(item));
        broadcast("§eVocê recebeu um item do evento!");
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
        broadcast("§eVocê recebeu um efeito de poção do evento!");
        playsound(Sound.ENTITY_ITEM_PICKUP);
    }

    public void effectClear() {
        effects.clear();
        players.forEach(p -> p.getActivePotionEffects().forEach(e -> p.removePotionEffect(e.getType())));
    }

    public void teleportAll(Location loc) {
        players.forEach(p -> p.teleport(loc));
        broadcast("§eVocê foi teletransportado!");
        playsound(Sound.BLOCK_NOTE_BLOCK_BIT);
    }

    public void setSpawn(Location loc) {
        this.spawn = loc;
    }

    public void lockEvent(boolean locked) {
        this.locked = locked;
    }

    public void addSpectator(Player p) {
        this.spectators.add(p);
        this.playerOldSettings.put(p,new AbstractMap.SimpleEntry<>(null,p.getLocation()));

        p.teleport(spawn);
        p.getInventory().setItem(8, Itens.getItem(Material.BARRIER,"§c§lSair do Evento","§7Clique direito para sair do evento"));

        p.sendMessage("§7 \n §7Você entrou no evento como um espectador!\n§7 ");
        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,100000,1,false,false));
    }

    public void removeSpectator(Player p) {
        spectators.remove(p);

        p.getActivePotionEffects().forEach(e -> p.removePotionEffect(e.getType()));
        p.teleport(playerOldSettings.get(p).getValue());

        InventoryGUIs.clearHotbar(p);

        playerOldSettings.remove(p);
    }

    void setGamemode(GameMode gm) {
        players.forEach(p -> p.setGameMode(gm));
        broadcast("§aSeu modo de jogo foi alterado!");
        playsound(Sound.BLOCK_NOTE_BLOCK_BIT);
    }

    public void finalizar() {
        Bukkit.getScheduler().cancelTask(bcTaskId);

        if(players.isEmpty()) {
            if(spectators.isEmpty()) {
                Main.getMain().evento = null;
            } else {
                AtomicInteger i = new AtomicInteger();
                AtomicInteger size = new AtomicInteger(spectators.size());

                spectators.forEach(s -> {
                    spectators.remove(s);

                    // dá clear nos efeitos
                    s.getInventory().clear();
                    s.getActivePotionEffects().forEach(e -> s.removePotionEffect(e.getType()));

                    s.teleport(playerOldSettings.get(s).getValue());

                    playerOldSettings.remove(s);

                    if(i.get()==size.get()) {
                        Main.getMain().evento = null;
                    }

                    i.getAndIncrement();
                    });

            }
            return;
        }

        AtomicInteger i = new AtomicInteger();
        AtomicInteger size = new AtomicInteger(players.size());

        players.forEach(p -> {
            players.remove(p);

            // restaura o inv e dá clear nos efeitos
            p.getInventory().clear();
            p.getInventory().setContents(playerOldSettings.get(p).getKey().getContents());
            p.getActivePotionEffects().forEach(e -> p.removePotionEffect(e.getType()));

            p.teleport(playerOldSettings.get(p).getValue());

            playerOldSettings.remove(p);

            if(i.get()==size.get()) {
                size.set(spectators.size());
                i.set(0);

                spectators.forEach(s -> {
                    spectators.remove(s);

                    // dá clear nos efeitos
                    s.getInventory().clear();
                    s.getActivePotionEffects().forEach(e -> s.removePotionEffect(e.getType()));

                    s.teleport(playerOldSettings.get(s).getValue());

                    playerOldSettings.remove(s);

                    if(i.get()==size.get()) {
                        Main.getMain().evento = null;
                    }

                    i.getAndIncrement();
                });
                return;
            }

            i.getAndIncrement();
        });
    }

    private void anunciar() {
        this.bcTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), () ->
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
