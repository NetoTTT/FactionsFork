/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.command.type.Type
 *  com.massivecraft.massivecore.command.type.container.TypeSet
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.type.TypeMPlayer;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsInvitedChange;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.Type;
import com.massivecraft.massivecore.command.type.container.TypeSet;
import java.util.HashSet;
import java.util.Set;

public class CmdFactionsConviteDel
extends FactionsCommand {
    public CmdFactionsConviteDel() {
        this.addAliases(new String[]{"delete", "remove", "remover", "deletar"});
        this.setDesc("\u00a76 convite del \u00a7e<player> \u00a78-\u00a77 Deleta um convite enviado.");
        this.addParameter((Type)TypeSet.get(TypeMPlayer.get()), "players", true);
    }

    public void perform() throws MassiveException {
        if (this.msender.getRole() != Rel.LEADER && this.msender.getRole() != Rel.OFFICER && !this.msender.isOverriding()) {
            this.msender.message("\u00a7cVoc\u00ea precisar ser capit\u00e3o ou superior para poder deletar convites enviados.");
            return;
        }
        @SuppressWarnings("unchecked") Set<MPlayer> mplayers = (Set<MPlayer>)this.readArgAt(0);
        for (MPlayer mplayer : mplayers) {
            if (this.msender == mplayer) {
                this.msender.message("\u00a7cVoc\u00ea j\u00e1 faz parte de uma fac\u00e7\u00e3o e voc\u00ea n\u00e3o pode remover um convite enviado a voc\u00ea.");
                return;
            }
            if (mplayer.getFaction() == this.msenderFaction) {
                this.msg("\u00a7c%s\u00a7c j\u00e1 \u00e9 membro da sua fac\u00e7\u00e3o.", new Object[]{mplayer.getName()});
                continue;
            }
            boolean isInvited = this.msenderFaction.isInvited(mplayer);
            if (isInvited) {
                EventFactionsInvitedChange event = new EventFactionsInvitedChange(this.sender, mplayer, this.msenderFaction, isInvited);
                event.run();
                if (event.isCancelled()) continue;
                isInvited = event.isNewInvited();
                mplayer.msg("\u00a7e%s\u00a7e removeu seu convite para entrar na fac\u00e7\u00e3o \u00a7f%s\u00a7e.", new Object[]{this.msender.getName(), this.msenderFaction.getName()});
                this.msenderFaction.msg("\u00a7e%s\u00a7e removeu o convite de fac\u00e7\u00e3o de \u00a7e\"%s\"\u00a7e.", String.valueOf(this.msender.getRole().getPrefix()) + this.msender.getName(), mplayer.getName());
                this.msenderFaction.uninvite(mplayer);
                this.msenderFaction.changed();
                continue;
            }
            this.msg("\u00a7c\"%s\"\u00a7c n\u00e3o possui um convite para entrar na sua fac\u00e7\u00e3o.", new Object[]{mplayer.getName()});
        }
    }
}

