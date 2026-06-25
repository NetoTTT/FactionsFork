/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.command.requirement.Requirement
 *  com.massivecraft.massivecore.command.requirement.RequirementIsPlayer
 *  com.massivecraft.massivecore.ps.PS
 *  org.bukkit.Location
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.event.EventFactionsHomeChange;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.Location;

public class CmdFactionsSethome
extends FactionsCommand {
    public CmdFactionsSethome() {
        this.addAliases(new String[]{"definirhome", "definirbase", "setbase"});
        this.addRequirements(new Requirement[]{RequirementIsPlayer.get()});
        this.setDesc("\u00a76 sethome \u00a78-\u00a77 Define a home da fac\u00e7\u00e3o.");
    }

    public void perform() throws MassiveException {
        Faction faction = this.msenderFaction;
        PS newHome = PS.valueOf((Location)this.me.getLocation());
        if (this.msender.getRole() != Rel.LEADER && this.msender.getRole() != Rel.OFFICER) {
            this.msender.message("\u00a7cVoc\u00ea precisar ser capit\u00e3o ou superior para poder definir a home da fac\u00e7\u00e3o.");
            return;
        }
        if (!faction.isValidHome(newHome)) {
            this.msender.msg("\u00a7cVoc\u00ea s\u00f3 pode definir a home da fac\u00e7\u00e3o dentro dos territ\u00f3rios da fac\u00e7\u00e3o.");
            return;
        }
        BoardColl coll = BoardColl.get();
        Faction factionC = coll.getFactionAt(PS.valueOf((Location)this.msender.getPlayer().getLocation()));
        if (!factionC.getMPlayers().contains(this.msender)) {
            this.msender.msg("\u00a7cVoc\u00ea s\u00f3 pode definir a home da fac\u00e7\u00e3o dentro dos territ\u00f3rios da fac\u00e7\u00e3o.");
            return;
        }
        EventFactionsHomeChange event = new EventFactionsHomeChange(this.sender, faction, newHome);
        event.run();
        if (event.isCancelled()) {
            return;
        }
        newHome = event.getNewHome();
        faction.setHome(newHome);
        faction.msg("\u00a7a%s\u00a7a definiu a nova home da fac\u00e7\u00e3o!", String.valueOf(this.msender.getRole().getPrefix()) + this.msender.getName());
    }
}

