/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.command.requirement.Requirement
 *  com.massivecraft.massivecore.command.requirement.RequirementIsPlayer
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.req.ReqHasFaction;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CmdFactionsPermissoes
extends FactionsCommand {
    public CmdFactionsPermissoes() {
        this.addAliases(new String[]{"perm"});
        this.addRequirements(new Requirement[]{RequirementIsPlayer.get()});
        this.addRequirements(new Requirement[]{ReqHasFaction.get()});
        this.setDesc("\u00a76 perm \u00a78-\u00a77 Gerencia as permiss\u00f5es da fac\u00e7\u00e3o.");
    }

    public void perform() throws MassiveException {
        Player p = this.msender.getPlayer();
        CmdFactionsPermissoes.abrirMenuPermissoes(p, this.msender, this.msenderFaction);
    }

    public static void abrirMenuPermissoes(Player p, MPlayer mp, Faction f) {
        Inventory perms = Bukkit.createInventory(null, (int)27, (String)("\u00a78Permiss\u00f5es - " + f.getName()));
        ArrayList<String> lore = new ArrayList<String>();
        ItemStack build = new ItemStack(Material.GRASS, 1);
        ItemMeta bm = build.getItemMeta();
        bm.setDisplayName("\u00a7eConstruir");
        lore.add("\u00a77");
        lore.add("\u00a77Clique para gerenciar a permiss\u00e3o de");
        lore.add("\u00a77construir nos territ\u00f3rios da sua fac\u00e7\u00e3o.");
        lore.add("\u00a77");
        lore.add("\u00a7eLista de permitidos:");
        for (Rel rel : f.getPermitted(MPerm.getPermBuild())) {
            lore.add(" \u00a7a- " + rel.getName());
        }
        bm.setLore(lore);
        lore.clear();
        build.setItemMeta(bm);
        ItemStack container = new ItemStack(Material.CHEST, 1);
        ItemMeta cm = container.getItemMeta();
        cm.setDisplayName("\u00a7eAbrir containers");
        lore.add("\u00a77");
        lore.add("\u00a77Clique para gerenciar a permiss\u00e3o de abrir");
        lore.add("\u00a77containers nos territ\u00f3rios da sua fac\u00e7\u00e3o.");
        lore.add("\u00a77");
        lore.add("\u00a7eLista de permitidos:");
        for (Rel rel : f.getPermitted(MPerm.getPermContainer())) {
            lore.add(" \u00a7a- " + rel.getName());
        }
        cm.setLore(lore);
        lore.clear();
        container.setItemMeta(cm);
        ItemStack home = new ItemStack(Material.BED, 1);
        ItemMeta homem = build.getItemMeta();
        homem.setDisplayName("\u00a7eIr para home");
        lore.add("\u00a77");
        lore.add("\u00a77Clique para gerenciar a permiss\u00e3o");
        lore.add("\u00a77de acesso \u00e0 home da fac\u00e7\u00e3o.");
        lore.add("\u00a77");
        lore.add("\u00a7eLista de permitidos:");
        for (Rel rel : f.getPermitted(MPerm.getPermHome())) {
            lore.add(" \u00a7a- " + rel.getName());
        }
        homem.setLore(lore);
        lore.clear();
        home.setItemMeta(homem);
        ItemStack porta = new ItemStack(Material.WOOD_DOOR, 1);
        ItemMeta pm = porta.getItemMeta();
        pm.setDisplayName("\u00a7eAbrir portas");
        lore.add("\u00a77");
        lore.add("\u00a77Clique para gerenciar a permiss\u00e3o de abrir");
        lore.add("\u00a77portas nos territ\u00f3rios da sua fac\u00e7\u00e3o.");
        lore.add("\u00a77");
        lore.add("\u00a7eLista de permitidos:");
        for (Rel rel : f.getPermitted(MPerm.getPermDoor())) {
            lore.add(" \u00a7a- " + rel.getName());
        }
        pm.setLore(lore);
        lore.clear();
        porta.setItemMeta(pm);
        ItemStack botao = new ItemStack(Material.STONE_BUTTON, 1);
        ItemMeta bom = botao.getItemMeta();
        bom.setDisplayName("\u00a7eUsar bot\u00f5es");
        lore.add("\u00a77");
        lore.add("\u00a77Clique para gerenciar a permiss\u00e3o de usar");
        lore.add("\u00a77bot\u00f5es nos territ\u00f3rios da sua fac\u00e7\u00e3o.");
        lore.add("\u00a77");
        lore.add("\u00a7eLista de permitidos:");
        for (Rel rel : f.getPermitted(MPerm.getPermButton())) {
            lore.add(" \u00a7a- " + rel.getName());
        }
        bom.setLore(lore);
        lore.clear();
        botao.setItemMeta(bom);
        ItemStack alav = new ItemStack(Material.LEVER, 1);
        ItemMeta am = alav.getItemMeta();
        am.setDisplayName("\u00a7eUsar alavancas");
        lore.add("\u00a77");
        lore.add("\u00a77Clique para gerenciar a permiss\u00e3o de usar");
        lore.add("\u00a77alavancas nos territ\u00f3rios da sua fac\u00e7\u00e3o.");
        lore.add("\u00a77");
        lore.add("\u00a7eLista de permitidos:");
        for (Rel rel : f.getPermitted(MPerm.getPermLever())) {
            lore.add(" \u00a7a- " + rel.getName());
        }
        am.setLore(lore);
        lore.clear();
        alav.setItemMeta(am);
        perms.setItem(10, build);
        perms.setItem(11, container);
        perms.setItem(12, home);
        perms.setItem(14, porta);
        perms.setItem(15, botao);
        perms.setItem(16, alav);
        p.openInventory(perms);
    }
}

