/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.command.requirement.Requirement
 *  com.massivecraft.massivecore.command.type.Type
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.req.ReqHasntFaction;
import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.event.EventFactionsMembershipChange;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.type.Type;

public class CmdFactionsEntrar
extends FactionsCommand {
    public CmdFactionsEntrar() {
        this.addAliases(new String[]{"join", "aceitar"});
        this.addParameter((Type)TypeFaction.get(), "fac\u00e7\u00e3o");
        this.addRequirements(new Requirement[]{ReqHasntFaction.get()});
        this.setDesc("\u00a76 entrar \u00a7e<fac\u00e7\u00e3o> \u00a78-\u00a77 Entra em uma fac\u00e7\u00e3o.");
    }

    public void perform() throws MassiveException {
        Faction faction = (Faction)this.readArg();
        if (MConf.get().factionMemberLimit > 0 && faction.getMPlayers().size() >= MConf.get().factionMemberLimit) {
            this.msg("\u00a7cA fac\u00e7\u00e3o %s atingiu o limite maximo de membros (%d), portanto voc\u00ea n\u00e3o podera entrar na fac\u00e7\u00e3o.", new Object[]{faction.getName(), MConf.get().factionMemberLimit});
            return;
        }
        if (!faction.isInvited(this.msender) && !this.msender.isOverriding()) {
            this.msg("\u00a7cVoc\u00ea precisa de um convite para poder entrar na fac\u00e7\u00e3o.");
            return;
        }
        EventFactionsMembershipChange membershipChangeEvent = new EventFactionsMembershipChange(this.sender, this.msender, faction, EventFactionsMembershipChange.MembershipChangeReason.JOIN);
        membershipChangeEvent.run();
        if (membershipChangeEvent.isCancelled()) {
            return;
        }
        faction.msg("\u00a7e\"%s\" \u00a7eentrou na sua fac\u00e7\u00e3o\u00a7e.", this.msender.getName());
        this.msender.msg("\u00a7aVoc\u00ea entrou na fac\u00e7\u00e3o \u00a7f%s\u00a7a.", new Object[]{faction.getName()});
        this.msender.resetFactionData();
        this.msender.setFaction(faction);
        faction.uninvite(this.msender);
    }
}

