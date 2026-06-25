/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.command.type.Type
 *  com.massivecraft.massivecore.command.type.container.TypeSet
 *  com.massivecraft.massivecore.util.IdUtil
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.type.TypeMPlayer;
import com.massivecraft.factions.entity.Invitation;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsInvitedChange;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.Type;
import com.massivecraft.massivecore.command.type.container.TypeSet;
import com.massivecraft.massivecore.util.IdUtil;
import java.util.Collection;

public class CmdFactionsConviteAdd
extends FactionsCommand {
    public CmdFactionsConviteAdd() {
        this.addAliases(new String[]{"a", "add", "enviar", "adicionar"});
        this.setDesc("\u00a76 convite add \u00a7e<player> \u00a78-\u00a77 Envia um convite para um player.");
        this.addParameter((Type)TypeSet.get(TypeMPlayer.get()), "players", true);
    }

    public void perform() throws MassiveException {
        if (this.msender.getRole() != Rel.LEADER && this.msender.getRole() != Rel.OFFICER && !this.msender.isOverriding()) {
            this.msender.message("\u00a7cVoc\u00ea precisar ser capit\u00e3o ou superior para poder gerenciar os convites da fac\u00e7\u00e3o.");
            return;
        }
        if (this.msenderFaction.getInvitations().size() >= 10) {
            this.msender.message("\u00a7cLimite m\u00e1ximo de convites pendentes atingido (10)! Apague alguns convites in\u00fateis\u00a0para poder enviar novos convites.");
            return;
        }
        @SuppressWarnings("unchecked") Collection<MPlayer> mplayers = (Collection<MPlayer>)this.readArg();
        String senderId = IdUtil.getId((Object)this.sender);
        long creationMillis = System.currentTimeMillis();
        for (MPlayer mplayer : mplayers) {
            if (this.msender == mplayer) {
                this.msender.message("\u00a7cVoc\u00ea j\u00e1 faz parte de uma fac\u00e7\u00e3o e voc\u00ea n\u00e3o pode adicionar um convite para voc\u00ea mesmo.");
                continue;
            }
            if (mplayer.getFaction() == this.msenderFaction) {
                this.msg("\u00a7c\"%s\"\u00a7c j\u00e1 \u00e9 membro da sua fac\u00e7\u00e3o.", new Object[]{mplayer.getName()});
                continue;
            }
            boolean isInvited = this.msenderFaction.isInvited(mplayer);
            if (!isInvited) {
                EventFactionsInvitedChange event = new EventFactionsInvitedChange(this.sender, mplayer, this.msenderFaction, isInvited);
                event.run();
                if (event.isCancelled()) continue;
                isInvited = event.isNewInvited();
                mplayer.msg("\u00a7a%s\u00a7a convidou voc\u00ea para entrar na fac\u00e7\u00e3o \u00a7f%s\u00a7a.", new Object[]{this.msender.getName(), this.msenderFaction.getName()});
                this.msenderFaction.msg("\u00a7e%s\u00a7e convidou \u00a7e\"%s\"\u00a7e para entrar na sua fac\u00e7\u00e3o.", String.valueOf(this.msender.getRole().getPrefix()) + this.msender.getName(), mplayer.getName());
                Invitation invitation = new Invitation(senderId, creationMillis);
                this.msenderFaction.invite(mplayer.getId(), invitation);
                this.msenderFaction.changed();
                continue;
            }
            this.msg("\u00a7c\"%s\"\u00a7c j\u00e1 possui um convite para entrar na sua fac\u00e7\u00e3o.", new Object[]{mplayer.getName()});
        }
    }
}

