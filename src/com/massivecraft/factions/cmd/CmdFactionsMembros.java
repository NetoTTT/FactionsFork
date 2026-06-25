/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.command.requirement.Requirement
 *  com.massivecraft.massivecore.command.requirement.RequirementIsPlayer
 *  com.massivecraft.massivecore.command.type.Type
 *  com.massivecraft.massivecore.util.TimeDiffUtil
 *  com.massivecraft.massivecore.util.TimeUnit
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.engine.EngineKdr;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.util.ItemBuilder;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.Type;
import com.massivecraft.massivecore.util.TimeDiffUtil;
import com.massivecraft.massivecore.util.TimeUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CmdFactionsMembros
extends FactionsCommand {
    public CmdFactionsMembros() {
        this.addAliases(new String[]{"status", "s"});
        this.addRequirements(new Requirement[]{RequirementIsPlayer.get()});
        this.addParameter((Type)TypeFaction.get(), "fac\u00e7\u00e3o", "voc\u00ea");
        this.setDesc("\u00a76 membros \u00a7e<fac\u00e7\u00e3o> \u00a78-\u00a77 Mostra a lista de membros da fac\u00e7\u00e3o.");
    }

    public void perform() throws MassiveException {
        Faction faction = (Faction)this.readArg(this.msenderFaction);
        List<MPlayer> mps = faction.getMPlayers();
        if (faction.isNone() || mps.size() > 43) {
            this.msender.msg("\u00a7cA fac\u00e7\u00e3o \u00a7f" + faction.getName() + "\u00a7c possui muitos membros portanto o Menu GUI n\u00e3o sera aberto.");
            return;
        }
        if (mps.size() == 0) {
            this.msender.msg("\u00a7cA fac\u00e7\u00e3o \u00a7f" + faction.getName() + "\u00a7c n\u00e3o possui membros!");
            return;
        }
        int limitemembros = MConf.get().factionMemberLimit;
        int tamanhodomenu = 54;
        if (limitemembros <= 10) {
            tamanhodomenu = 36;
        } else if (limitemembros > 10 && limitemembros < 20) {
            tamanhodomenu = 45;
        }
        Inventory inv = Bukkit.createInventory(null, (int)tamanhodomenu, (String)("\u00a78Membros da " + faction.getName()));
        if (tamanhodomenu == 36) {
            inv.setItem(11, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(12, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(13, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(14, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(15, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(20, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(21, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(22, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(23, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(24, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
        } else if (tamanhodomenu == 45) {
            inv.setItem(11, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(12, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(13, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(14, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(15, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(20, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(21, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(22, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(23, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(24, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(29, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(30, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(31, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(32, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(33, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
        } else {
            inv.setItem(11, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(12, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(13, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(14, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(15, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(20, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(21, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(22, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(23, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(24, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(29, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(30, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(31, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(32, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(33, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(38, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(39, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(40, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(41, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
            inv.setItem(42, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("\u00a78Vago").toItemStack());
        }
        MPlayer mplayer = this.msender;
        Player p = mplayer.getPlayer();
        int slot = 11;
        int i = 0;
        while (i < faction.getMPlayers().size()) {
            MPlayer mp = mps.get(i);
            Rel cargo = mp.getRole();
            String nome = mp.getName();
            boolean isOnline = mp.isOnline();
            double poderMax = mp.getPowerMax();
            double poderAtual = mp.getPower();
            int kills = EngineKdr.getPlayerKills(mp);
            int deaths = EngineKdr.getPlayerDeaths(mp);
            String kdr2f = EngineKdr.getPlayerKdr(mp);
            String poderAtual1f = String.format("%.1f", poderAtual);
            long ultimoLoginMillis = mp.getLastActivityMillis() - System.currentTimeMillis();
            LinkedHashMap ageUnitcounts = TimeDiffUtil.limit((LinkedHashMap)TimeDiffUtil.unitcounts((long)ultimoLoginMillis, (TreeSet)TimeUnit.getAllButMillis()), (int)3);
            String ultimoLogin = TimeDiffUtil.formatedMinimal((Map)ageUnitcounts, (String)"\u00a7e");
            inv.setItem(slot, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setSkullOwner(nome).setName("\u00a77" + nome).setLore("\u00a7fPoder: \u00a77" + poderAtual1f + "/" + poderMax, "\u00a7fCargo: \u00a77" + cargo.getPrefix() + cargo.getName(), "\u00a7fAbates: \u00a77" + kills, "\u00a7fMortes: \u00a77" + deaths, "\u00a7fKdr: \u00a77" + kdr2f, "\u00a7fStats: " + (isOnline ? "\u00a7aOnline" : "\u00a7cOffline"), "\u00a7f\u00daltimo login: \u00a77" + ultimoLogin + "\u00a7e atr\u00e1s").toItemStack());
            slot += slot == 15 || slot == 24 || slot == 33 ? 5 : 1;
            ++i;
        }
        p.openInventory(inv);
    }
}

