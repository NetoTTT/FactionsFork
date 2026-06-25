/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.Engine
 *  org.bukkit.Bukkit
 *  org.bukkit.Color
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.LeatherArmorMeta
 */
package com.massivecraft.factions.engine;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.cmd.CmdFactionsPermissoes;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.Engine;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class EngineMenuPermissoes
extends Engine {
    private static EngineMenuPermissoes i = new EngineMenuPermissoes();

    public static EngineMenuPermissoes get() {
        return i;
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void onClick(InventoryClickEvent e) {
        if (e.getInventory().getName().contains("\u00a78Permiss\u00f5es")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            Player p = (Player)e.getWhoClicked();
            MPlayer mp = MPlayer.get(p);
            Faction f = mp.getFaction();
            int slot = e.getSlot();
            if (slot == 40) {
                CmdFactionsPermissoes.abrirMenuPermissoes(p, mp, f);
                return;
            }
            if (e.getInventory().getName().contains("Construir")) {
                MPerm perme = MPerm.getPermBuild();
                if (slot == 21) {
                    this.update(f, perme, Rel.RECRUIT);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                if (slot == 20) {
                    this.update(f, perme, Rel.MEMBER);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                if (slot == 19) {
                    this.update(f, perme, Rel.OFFICER);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                if (slot == 23) {
                    this.update(f, perme, Rel.ALLY);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                if (slot == 24) {
                    this.update(f, perme, Rel.NEUTRAL);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                if (slot == 25) {
                    this.update(f, perme, Rel.ENEMY);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                return;
            }
            if (e.getInventory().getName().contains("Usar bot\u00f5es")) {
                MPerm perme = MPerm.getPermButton();
                if (slot == 21) {
                    this.update(f, perme, Rel.RECRUIT);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                if (slot == 20) {
                    this.update(f, perme, Rel.MEMBER);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                if (slot == 19) {
                    this.update(f, perme, Rel.OFFICER);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                if (slot == 23) {
                    this.update(f, perme, Rel.ALLY);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                if (slot == 24) {
                    this.update(f, perme, Rel.NEUTRAL);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                if (slot == 25) {
                    this.update(f, perme, Rel.ENEMY);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                return;
            }
            if (e.getInventory().getName().contains("Usar alavancas")) {
                MPerm perme = MPerm.getPermLever();
                if (slot == 21) {
                    this.update(f, perme, Rel.RECRUIT);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                if (slot == 20) {
                    this.update(f, perme, Rel.MEMBER);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                if (slot == 19) {
                    this.update(f, perme, Rel.OFFICER);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                if (slot == 23) {
                    this.update(f, perme, Rel.ALLY);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                if (slot == 24) {
                    this.update(f, perme, Rel.NEUTRAL);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                if (slot == 25) {
                    this.update(f, perme, Rel.ENEMY);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                return;
            }
            if (e.getInventory().getName().contains("Ir para home")) {
                MPerm perme = MPerm.getPermHome();
                if (slot == 21) {
                    this.update(f, perme, Rel.RECRUIT);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                if (slot == 20) {
                    this.update(f, perme, Rel.MEMBER);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                if (slot == 19) {
                    this.update(f, perme, Rel.OFFICER);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                if (slot == 23) {
                    this.update(f, perme, Rel.ALLY);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                if (slot == 24) {
                    this.update(f, perme, Rel.NEUTRAL);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                if (slot == 25) {
                    this.update(f, perme, Rel.ENEMY);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                return;
            }
            if (e.getInventory().getName().contains("Usar portas")) {
                MPerm perme = MPerm.getPermDoor();
                if (slot == 21) {
                    this.update(f, perme, Rel.RECRUIT);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                if (slot == 20) {
                    this.update(f, perme, Rel.MEMBER);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                if (slot == 19) {
                    this.update(f, perme, Rel.OFFICER);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                if (slot == 23) {
                    this.update(f, perme, Rel.ALLY);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                if (slot == 24) {
                    this.update(f, perme, Rel.NEUTRAL);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                if (slot == 25) {
                    this.update(f, perme, Rel.ENEMY);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                return;
            }
            if (e.getInventory().getName().contains("Usar containers")) {
                MPerm perme = MPerm.getPermContainer();
                if (slot == 21) {
                    this.update(f, perme, Rel.RECRUIT);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                if (slot == 20) {
                    this.update(f, perme, Rel.MEMBER);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                if (slot == 19) {
                    this.update(f, perme, Rel.OFFICER);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                if (slot == 23) {
                    this.update(f, perme, Rel.ALLY);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                if (slot == 24) {
                    this.update(f, perme, Rel.NEUTRAL);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                if (slot == 25) {
                    this.update(f, perme, Rel.ENEMY);
                    this.abrirMenuConfig(p, perme);
                    return;
                }
                return;
            }
            if (slot == 10) {
                if (mp.getRole() != Rel.LEADER && !mp.isOverriding()) {
                    p.sendMessage("\u00a7cApenas o l\u00edder da fac\u00e7\u00e3o pode administrar as permiss\u00f5es.");
                    p.closeInventory();
                } else {
                    this.abrirMenuConfig(p, MPerm.getPermBuild());
                }
            }
            if (slot == 11) {
                if (mp.getRole() != Rel.LEADER && !mp.isOverriding()) {
                    p.sendMessage("\u00a7cApenas o l\u00edder da fac\u00e7\u00e3o pode administrar as permiss\u00f5es.");
                    p.closeInventory();
                } else {
                    this.abrirMenuConfig(p, MPerm.getPermContainer());
                }
            }
            if (slot == 12) {
                if (mp.getRole() != Rel.LEADER && !mp.isOverriding()) {
                    p.sendMessage("\u00a7cApenas o l\u00edder da fac\u00e7\u00e3o pode administrar as permiss\u00f5es.");
                    p.closeInventory();
                } else {
                    this.abrirMenuConfig(p, MPerm.getPermHome());
                }
            }
            if (slot == 14) {
                if (mp.getRole() != Rel.LEADER && !mp.isOverriding()) {
                    p.sendMessage("\u00a7cApenas o l\u00edder da fac\u00e7\u00e3o pode administrar as permiss\u00f5es.");
                    p.closeInventory();
                } else {
                    this.abrirMenuConfig(p, MPerm.getPermDoor());
                }
            }
            if (slot == 15) {
                if (mp.getRole() != Rel.LEADER && !mp.isOverriding()) {
                    p.sendMessage("\u00a7cApenas o l\u00edder da fac\u00e7\u00e3o pode administrar as permiss\u00f5es.");
                    p.closeInventory();
                } else {
                    this.abrirMenuConfig(p, MPerm.getPermButton());
                }
            }
            if (slot == 16) {
                if (mp.getRole() != Rel.LEADER && !mp.isOverriding()) {
                    p.sendMessage("\u00a7cApenas o l\u00edder da fac\u00e7\u00e3o pode administrar as permiss\u00f5es.");
                    p.closeInventory();
                } else {
                    this.abrirMenuConfig(p, MPerm.getPermLever());
                }
            }
        }
    }

    private void abrirMenuConfig(Player p, MPerm perm) {
        String permnome = "";
        if (perm == MPerm.getPermBuild()) {
            permnome = "Construir";
        } else if (perm == MPerm.getPermButton()) {
            permnome = "Usar bot\u00f5es";
        } else if (perm == MPerm.getPermLever()) {
            permnome = "Usar alavancas";
        } else if (perm == MPerm.getPermHome()) {
            permnome = "Ir para home";
        } else if (perm == MPerm.getPermDoor()) {
            permnome = "Usar portas";
        } else if (perm == MPerm.getPermContainer()) {
            permnome = "Usar containers";
        }
        Inventory permconfig = Bukkit.createInventory(null, (int)45, (String)("\u00a78Permiss\u00f5es - " + permnome));
        ItemStack recruta = new ItemStack(Material.STONE_SWORD, 1);
        ItemMeta rm = recruta.getItemMeta();
        rm.setDisplayName("\u00a7eRecruta");
        recruta.setItemMeta(rm);
        ItemStack membro = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta mm = membro.getItemMeta();
        mm.setDisplayName("\u00a7eMembro");
        membro.setItemMeta(mm);
        ItemStack capitao = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta cm = capitao.getItemMeta();
        cm.setDisplayName("\u00a7eCapit\u00e3o");
        capitao.setItemMeta(cm);
        ItemStack aliado = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta am = (LeatherArmorMeta)aliado.getItemMeta();
        am.setColor(Color.LIME);
        am.setDisplayName("\u00a7eAliado");
        aliado.setItemMeta((ItemMeta)am);
        ItemStack neutro = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta nm = (LeatherArmorMeta)neutro.getItemMeta();
        nm.setColor(Color.WHITE);
        nm.setDisplayName("\u00a7eNeutro");
        neutro.setItemMeta((ItemMeta)nm);
        ItemStack inimigo = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta im = (LeatherArmorMeta)inimigo.getItemMeta();
        im.setColor(Color.RED);
        im.setDisplayName("\u00a7eInimigo");
        inimigo.setItemMeta((ItemMeta)im);
        Faction f = MPlayer.get(p).getFaction();
        boolean recruit = f.getPermitted(perm).contains((Object)Rel.RECRUIT);
        boolean member = f.getPermitted(perm).contains((Object)Rel.MEMBER);
        boolean officer = f.getPermitted(perm).contains((Object)Rel.OFFICER);
        boolean ally = f.getPermitted(perm).contains((Object)Rel.ALLY);
        boolean neutral = f.getPermitted(perm).contains((Object)Rel.NEUTRAL);
        boolean enemy = f.getPermitted(perm).contains((Object)Rel.ENEMY);
        ItemStack voltar = new ItemStack(Material.ARROW, 1);
        ItemMeta vm = voltar.getItemMeta();
        vm.setDisplayName("\u00a7cVoltar");
        voltar.setItemMeta(vm);
        permconfig.setItem(12, recruta);
        permconfig.setItem(11, membro);
        permconfig.setItem(10, capitao);
        permconfig.setItem(14, aliado);
        permconfig.setItem(15, neutro);
        permconfig.setItem(16, inimigo);
        permconfig.setItem(21, this.vidro(recruit));
        permconfig.setItem(20, this.vidro(member));
        permconfig.setItem(19, this.vidro(officer));
        permconfig.setItem(23, this.vidro(ally));
        permconfig.setItem(24, this.vidro(neutral));
        permconfig.setItem(25, this.vidro(enemy));
        permconfig.setItem(40, voltar);
        p.openInventory(permconfig);
    }

    private ItemStack vidro(boolean b) {
        ItemStack vidro;
        if (b) {
            vidro = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)5);
            ItemMeta vab = vidro.getItemMeta();
            vab.setDisplayName("\u00a7aPermitido");
            vidro.setItemMeta(vab);
        } else {
            vidro = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
            ItemMeta vab = vidro.getItemMeta();
            vab.setDisplayName("\u00a7cN\u00e3o permitido");
            vidro.setItemMeta(vab);
        }
        return vidro;
    }

    private void update(Faction fac, MPerm perm, Rel rel) {
        if (fac.getPermitted(perm).contains((Object)rel)) {
            fac.setRelationPermitted(perm, rel, false);
        } else {
            fac.setRelationPermitted(perm, rel, true);
        }
    }
}

