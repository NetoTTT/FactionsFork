/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.command.requirement.Requirement
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.req.ReqHasFaction;
import com.massivecraft.factions.cmd.type.TypeMPlayer;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsMembershipChange;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;

public class CmdFactionsKick
extends FactionsCommand {
    public CmdFactionsKick() {
        this.addAliases(new String[]{"expulsar", "kickar"});
        this.addParameter(TypeMPlayer.get(), "player");
        this.addRequirements(new Requirement[]{ReqHasFaction.get()});
        this.setDesc("\u00a76 kick \u00a7e<player> \u00a78-\u00a77 Expulsa um player da fac\u00e7\u00e3o.");
    }

    public void perform() throws MassiveException {
        if (this.msender.getRole() != Rel.LEADER && this.msender.getRole() != Rel.OFFICER && !this.msender.isOverriding()) {
            this.msender.message("\u00a7cVoc\u00ea precisar ser capit\u00e3o ou superior para poder expulsar membros da fac\u00e7\u00e3o.");
            return;
        }
        MPlayer mplayer = (MPlayer)this.readArg();
        if (mplayer.getFaction() != this.msenderFaction) {
            this.msender.message("\u00a7cEste jogador n\u00e3o esta na sua fac\u00e7\u00e3o.");
            return;
        }
        if (this.msender == mplayer) {
            this.msg("\u00a7cVoc\u00ea n\u00e3o pode se expulsar da fac\u00e7\u00e3o, caso queira sair use /f sair.");
            return;
        }
        if (mplayer.getRole() == Rel.LEADER && !this.msender.isOverriding()) {
            throw new MassiveException().addMsg("\u00a7cO l\u00edder da fac\u00e7\u00e3o n\u00e3o pode ser expulso!");
        }
        if (mplayer.getRole() == Rel.OFFICER && this.msender.getRole() == Rel.OFFICER && !this.msender.isOverriding()) {
            throw new MassiveException().addMsg("\u00a7cApenas o l\u00edder da fac\u00e7\u00e3o pode expulsar ou rebaixar outros capit\u00f5es.");
        }
        EventFactionsMembershipChange event = new EventFactionsMembershipChange(this.sender, mplayer, FactionColl.get().getNone(), EventFactionsMembershipChange.MembershipChangeReason.KICK);
        event.run();
        if (event.isCancelled()) {
            return;
        }
        Faction mplayerFaction = mplayer.getFaction();
        if (mplayer.getRole() == Rel.LEADER) {
            mplayerFaction.promoteNewLeader();
        }
        mplayerFaction.uninvite(mplayer);
        mplayer.resetFactionData();
        mplayerFaction.msg("\u00a7e%s\u00a7e expulsou \u00a7e\"%s\"\u00a7e da fac\u00e7\u00e3o! :O", String.valueOf(this.msender.getRole().getPrefix()) + this.msender.getName(), mplayer.getName());
        mplayer.msg("\u00a7eVoc\u00ea foi expulso da fac\u00e7\u00e3o \u00a7f%s \u00a7epor \u00a7e%s!\u00a7e :O", new Object[]{mplayerFaction.getName(), String.valueOf(this.msender.getRole().getPrefix()) + this.msender.getName()});
    }
}

