package com.henriquenapimo1.eventmanager.commands.admin;

import com.henriquenapimo1.eventmanager.Main;
import com.henriquenapimo1.eventmanager.utils.Evento;
import com.henriquenapimo1.eventmanager.utils.CmdContext;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DarEfeitoCommand {

    public DarEfeitoCommand(CmdContext ctx) {
        Evento evento = Main.getMain().getEvento();

        if(evento == null) {
            ctx.reply("§7Não há nenhum evento acontecendo no momento!");
            return;
        }

        ItemStack item = ctx.getSender().getInventory().getItemInMainHand();
        if(item.getType().equals(Material.AIR)) {
            ctx.reply("§7Não há nenhum item selecionado na sua mão principal!");
            return;
        }

        if(!Arrays.asList(Material.POTION,Material.SPLASH_POTION,Material.LINGERING_POTION).contains(item.getType())) {
            ctx.reply("§cSelecione uma poção válida na sua mão principal!");
            return;
        }

        PotionMeta meta = (PotionMeta) item.getItemMeta();
        assert meta != null;

        List<PotionEffect> effects = new ArrayList<>(meta.getCustomEffects());

        PotionEffectType pType = meta.getBasePotionData().getType().getEffectType();

        if(pType == null) {
            ctx.reply("§cOcorreu um erro ao tentar dar essa poção!");
            return;
        }

        PotionEffect efeito = new PotionEffect(pType,10000,1,false,false);
        effects.add(efeito);

        evento.darEfeito(effects);

        ctx.reply("§7Efeitos dados com sucesso!");
    }
}
