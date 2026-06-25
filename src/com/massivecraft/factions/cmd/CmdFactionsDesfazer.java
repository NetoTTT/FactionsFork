/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.command.requirement.Requirement
 *  org.bukkit.command.CommandSender
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.req.ReqHasFaction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsDisband;
import com.massivecraft.factions.event.EventFactionsMembershipChange;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;
import org.bukkit.command.CommandSender;

public class CmdFactionsDesfazer
extends FactionsCommand {
    public CmdFactionsDesfazer() {
        this.addAliases(new String[]{"disband", "deletar", "excluir"});
        this.addRequirements(new Requirement[]{ReqHasFaction.get()});
        this.setDesc("\u00a76 desfazer \u00a78-\u00a77 Desfaz a sua fac\u00e7\u00e3o.");
    }

    public void perform() throws MassiveException {
        if (this.msender.getRole() != Rel.LEADER && !this.msender.isOverriding()) {
            this.msender.message("\u00a7cApenas o l\u00edder da fac\u00e7\u00e3o pode desfazer a fac\u00e7\u00e3o.");
            return;
        }
        if (this.msenderFaction.getFlag(MFlag.getFlagPermanent())) {
            this.msg("\u00a7cEsta fac\u00e7\u00e3o \u00e9 uma fac\u00e7\u00e3o permanente portanto n\u00e3o pode ser desfeita.");
            return;
        }
        EventFactionsDisband event = new EventFactionsDisband((CommandSender)this.me, this.msenderFaction);
        event.run();
        if (event.isCancelled()) {
            return;
        }
        for (MPlayer mplayer : this.msenderFaction.getMPlayers()) {
            EventFactionsMembershipChange membershipChangeEvent = new EventFactionsMembershipChange(this.sender, mplayer, FactionColl.get().getNone(), EventFactionsMembershipChange.MembershipChangeReason.DISBAND);
            membershipChangeEvent.run();
        }
        for (MPlayer mplayer : this.msenderFaction.getMPlayersWhereOnline(true)) {
            mplayer.msg("\u00a7e%s\u00a7e desfez a sua fac\u00e7\u00e3o!", new Object[]{this.msender.describeTo(mplayer).replace("voc\u00ea", "\u00a7eVoc\u00ea")});
        }
        this.msenderFaction.detach();
    }
}

