/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.command.MassiveCommand
 *  com.massivecraft.massivecore.command.type.Type
 *  com.massivecraft.massivecore.mson.Mson
 *  org.bukkit.Bukkit
 *  org.bukkit.Color
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.cmd.CmdFactions;
import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.cmd.type.TypeRelation;
import com.massivecraft.factions.engine.EngineEditSource;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.event.EventFactionsRelationChange;
import com.massivecraft.factions.util.ItemBuilder;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.MassiveCommand;
import com.massivecraft.massivecore.command.type.Type;
import com.massivecraft.massivecore.mson.Mson;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CmdFactionsRelacaoDefinir
extends FactionsCommand {
    public CmdFactionsRelacaoDefinir() {
        this.addAliases(new String[]{"set", "setar"});
        this.setDesc("\u00a76 relacao definir \u00a7e<fac\u00e7\u00e3o> \u00a78-\u00a77 Define uma rela\u00e7\u00e3o.");
        this.addParameter((Type)TypeFaction.get(), "fac\u00e7\u00e3o");
        this.addParameter((Type)TypeRelation.get(), "rela\u00e7\u00e3o", "abrirMenu");
    }

    public void perform() throws MassiveException {
        Faction otherFaction = (Faction)this.readArg();
        Rel newRelation = (Rel)((Object)this.readArg());
        if (otherFaction == this.msenderFaction) {
            throw new MassiveException().setMsg("\u00a7cVoc\u00ea n\u00e3o pode definir uma rela\u00e7\u00e3o com sua pr\u00f3pria fac\u00e7\u00e3o.");
        }
        if (newRelation == null && this.msender.isPlayer()) {
            Player p = this.msender.getPlayer();
            String factionNome = otherFaction.getName();
            Inventory inv = Bukkit.createInventory(null, (int)27, (String)("\u00a78Rela\u00e7\u00e3o com " + factionNome));
            if (this.msenderFaction.getRelationTo(otherFaction) == Rel.ALLY) {
                inv.setItem(11, new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("\u00a7bDefinir alian\u00e7a com " + factionNome).setLore("\u00a7cSua a fac\u00e7\u00e3o j\u00e1 \u00e9 \u00a7caliada da " + factionNome + "\u00a7c.").setLeatherArmorColor(Color.AQUA).toItemStack());
            } else if (this.msenderFaction.getRelationWish(otherFaction) == Rel.ALLY || otherFaction.getRelationWish(this.msenderFaction) == Rel.ALLY) {
                inv.setItem(11, new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("\u00a7bDefinir alian\u00e7a com " + factionNome).setLore("\u00a7eSua fac\u00e7\u00e3o j\u00e1 possui um", "\u00a7econvite de alian\u00e7a pendente", "\u00a7ecom a \u00a7f" + factionNome + "\u00a7e!", "", "\u00a7fClique para ver a lista de", "\u00a7fconvites de alian\u00e7a pendentes.").setLeatherArmorColor(Color.AQUA).toItemStack());
            } else {
                inv.setItem(11, new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("\u00a7bDefinir alian\u00e7a com " + factionNome).setLore("\u00a7fClique para definir a fac\u00e7\u00e3o", "\u00a77" + factionNome + "\u00a7f como fac\u00e7\u00e3o \u00a7baliada\u00a7f.").setLeatherArmorColor(Color.AQUA).toItemStack());
            }
            if (this.msenderFaction.getRelationWish(otherFaction) == Rel.NEUTRAL) {
                inv.setItem(13, new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("\u00a7fDefinir neutralidade com " + factionNome).setLore("\u00a7cSua a fac\u00e7\u00e3o j\u00e1 \u00e9 neutra com a " + factionNome + ".").setLeatherArmorColor(Color.WHITE).toItemStack());
            } else {
                inv.setItem(13, new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("\u00a7fDefinir neutralidade com " + factionNome).setLore("\u00a7fClique para definir a fac\u00e7\u00e3o", "\u00a77" + factionNome + "\u00a7f como fac\u00e7\u00e3o \u00a7fneutra\u00a7f.").setLeatherArmorColor(Color.WHITE).toItemStack());
            }
            if (this.msenderFaction.getRelationWish(otherFaction) == Rel.ENEMY) {
                inv.setItem(15, new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("\u00a7cDefinir rivalidade com " + factionNome).setLore("\u00a7cSua a fac\u00e7\u00e3o j\u00e1 \u00e9 \u00a7crival da " + factionNome + ".").setLeatherArmorColor(Color.RED).toItemStack());
            } else {
                inv.setItem(15, new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("\u00a7cDefinir rivalidade com " + factionNome).setLore("\u00a7fClique para definir a fac\u00e7\u00e3o", "\u00a77" + factionNome + "\u00a7f como fac\u00e7\u00e3o \u00a7cinimiga\u00a7f.").setLeatherArmorColor(Color.RED).toItemStack());
            }
            p.openInventory(inv);
            return;
        }
        if (newRelation == null && this.msender.isConsole()) {
            throw new MassiveException().setMsg("\u00a7cUtilize /f relacao definir <faccao> <relacao>");
        }
        if (newRelation == Rel.TRUCE) {
            throw new MassiveException().setMessage((Object)"\u00a7cDesculpe mas o sistema de fac\u00e7\u00f5es em tr\u00e9gua foi desabilitado neste servidor.");
        }
        if (this.msender.getRole() != Rel.LEADER && this.msender.getRole() != Rel.OFFICER && !this.msender.isOverriding()) {
            this.msender.message("\u00a7cVoc\u00ea precisar ser capit\u00e3o ou superior para poder gerenciar as rela\u00e7\u00f5es da fac\u00e7\u00e3o.");
            return;
        }
        if (this.msenderFaction.getRelationWish(otherFaction) == newRelation) {
            throw new MassiveException().setMsg("\u00a7eA sua fac\u00e7\u00e3o j\u00e1 \u00e9 %s\u00a7e da \u00a7f%s\u00a7e.", new Object[]{newRelation.getDescFactionOne(), otherFaction.getName()});
        }
        if (EngineEditSource.getAliados(this.msenderFaction).size() >= MConf.get().factionAllyLimit && newRelation == Rel.ALLY) {
            throw new MassiveException().setMsg("\u00a7eA sua fac\u00e7\u00e3o j\u00e1 antingiu o limite m\u00e1ximo de aliados permitidos por fac\u00e7\u00e3o (%s).", new Object[]{MConf.get().factionAllyLimit});
        }
        if (EngineEditSource.getAliados(otherFaction).size() >= MConf.get().factionAllyLimit && newRelation == Rel.ALLY) {
            throw new MassiveException().setMsg("\u00a7eA a fac\u00e7\u00e3o %s\u00a7e j\u00e1 antingiu o limite m\u00e1ximo de aliados permitidos por fac\u00e7\u00e3o (%s).", new Object[]{otherFaction.getName(), MConf.get().factionAllyLimit});
        }
        EventFactionsRelationChange event = new EventFactionsRelationChange(this.sender, this.msenderFaction, otherFaction, newRelation);
        event.run();
        if (event.isCancelled()) {
            return;
        }
        newRelation = event.getNewRelation();
        this.msenderFaction.setRelationWish(otherFaction, newRelation);
        Rel currentRelation = this.msenderFaction.getRelationTo(otherFaction, true);
        if (newRelation == currentRelation) {
            otherFaction.msg("\u00a7f%s\u00a7e agora \u00e9 %s\u00a7e.", this.msenderFaction.getName(), newRelation.getDescFactionOne());
            this.msenderFaction.msg("\u00a7f%s\u00a7e agora \u00e9 %s\u00a7e.", otherFaction.getName(), newRelation.getDescFactionOne());
        } else {
            CmdFactionsRelacaoDefinir command = CmdFactions.get().cmdFactionsRelacao.cmdFactionsRelacaoDefinir;
            String colorOne = newRelation.getColor() + newRelation.getDescFactionOne();
            Mson factionsRelationshipChange = CmdFactionsRelacaoDefinir.mson((Object[])new Object[]{Mson.parse((String)"\u00a7f%s\u00a7e deseja se tornar %s\u00a7e.", (Object[])new Object[]{this.msenderFaction.getName(), colorOne}), Mson.SPACE, CmdFactionsRelacaoDefinir.mson((Object[])new Object[]{"\u00a7e[ACEITAR]"}).command((MassiveCommand)command, new String[]{this.msenderFaction.getName(), newRelation.name()})});
            otherFaction.sendMessage((Object)factionsRelationshipChange);
            this.msenderFaction.msg("\u00a7f%s\u00a7e foi informada de que a sua fac\u00e7\u00e3o deseja se tornar %s\u00a7e.", otherFaction.getName(), colorOne);
        }
        this.msenderFaction.changed();
    }
}

