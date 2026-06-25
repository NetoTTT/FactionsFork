/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.command.requirement.Requirement
 *  com.massivecraft.massivecore.command.requirement.RequirementIsPlayer
 *  com.massivecraft.massivecore.command.type.Type
 *  com.massivecraft.massivecore.mixin.MixinMessage
 *  com.massivecraft.massivecore.mixin.MixinTeleport
 *  com.massivecraft.massivecore.mixin.TeleporterException
 *  com.massivecraft.massivecore.ps.PS
 *  com.massivecraft.massivecore.teleport.Destination
 *  com.massivecraft.massivecore.teleport.DestinationSimple
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.event.EventFactionsHomeTeleport;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.Type;
import com.massivecraft.massivecore.mixin.MixinMessage;
import com.massivecraft.massivecore.mixin.MixinTeleport;
import com.massivecraft.massivecore.mixin.TeleporterException;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.teleport.Destination;
import com.massivecraft.massivecore.teleport.DestinationSimple;

public class CmdFactionsHome
extends FactionsCommand {
    public CmdFactionsHome() {
        this.addAliases(new String[]{"base", "h"});
        this.addParameter((Type)TypeFaction.get(), "fac\u00e7\u00e3o", "voc\u00ea");
        this.addRequirements(new Requirement[]{RequirementIsPlayer.get()});
        this.setDesc("\u00a76 home \u00a7e[fac\u00e7\u00e3o] \u00a78-\u00a77 Teleporta para a home da fac\u00e7\u00e3o.");
    }

    public void perform() throws MassiveException {
        Faction faction = (Faction)this.readArg(this.msenderFaction);
        PS home = faction.getHome();
        String homeDesc = "\u00a7ehome da " + (this.msenderFaction == faction ? "\u00a7esua fac\u00e7\u00e3o" : "\u00a7efac\u00e7\u00e3o \u00a7f" + faction.getName());
        if (!MPerm.getPermHome().has(this.msender, faction, true)) {
            return;
        }
        if (home == null) {
            this.msender.msg("\u00a7f" + (this.msenderFaction == faction ? "\u00a7cSua fac\u00e7\u00e3o" : faction.getName()) + "\u00a7c ainda n\u00e3o definiu a home da fac\u00e7\u00e3o.");
            return;
        }
        if (!MConf.get().homesTeleportAllowedFromEnemyTerritory && this.msender.isInEnemyTerritory()) {
            this.msender.msg("\u00a7eVoc\u00ea n\u00e3o pode se teleportar para %s \u00a7epois voc\u00ea esta em um territ\u00f3rio de uma fac\u00e7\u00e3o inimiga.", new Object[]{homeDesc});
            return;
        }
        EventFactionsHomeTeleport event = new EventFactionsHomeTeleport(this.sender);
        event.run();
        if (event.isCancelled()) {
            return;
        }
        try {
            DestinationSimple destination = new DestinationSimple(home, homeDesc);
            MixinTeleport.get().teleport((Object)this.me, (Destination)destination, MConf.get().homeSeconds);
        }
        catch (TeleporterException e) {
            String message = e.getMessage();
            MixinMessage.get().messageOne((Object)this.me, (Object)message);
        }
    }
}

