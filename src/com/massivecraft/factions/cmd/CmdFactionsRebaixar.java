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
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;

public class CmdFactionsRebaixar
extends FactionsCommand {
    public CmdFactionsRebaixar() {
        this.addAliases(new String[]{"demotar", "demote", "demover"});
        this.addParameter(TypeMPlayer.get(), "jogador");
        this.addRequirements(new Requirement[]{ReqHasFaction.get()});
        this.setDesc("\u00a76 rebaixar \u00a7e<player> \u00a78-\u00a77 Rebaixa um player de cargo.");
    }

    public void perform() throws MassiveException {
        Faction facTarget;
        if (this.msender.getRole() != Rel.LEADER && this.msender.getRole() != Rel.OFFICER && !this.msender.isOverriding()) {
            this.msender.message("\u00a7cVoc\u00ea precisar ser capit\u00e3o ou superior para poder administrar os cargos da fac\u00e7\u00e3o.");
            return;
        }
        MPlayer target = (MPlayer)this.readArg(this.msender);
        Faction facSender = this.msender.getFaction();
        if (facSender != (facTarget = target.getFaction())) {
            this.msender.message("\u00a7cEste jogador n\u00e3o esta na sua fac\u00e7\u00e3o.");
            return;
        }
        if (this.msender == target) {
            this.msender.message("\u00a7cVoc\u00ea n\u00e3o pode rebaixar voc\u00ea mesmo.");
            return;
        }
        Rel cargoms = this.msender.getRole();
        Rel cargomp = target.getRole();
        if (cargomp == Rel.LEADER) {
            this.msender.message("\u00a7c" + cargomp.getName() + "\u00a7c \u00e9 o l\u00edder da fac\u00e7\u00e3o portanto n\u00e3o pode ser rebaixado.");
            return;
        }
        if (cargomp == Rel.RECRUIT) {
            this.msender.message("\u00a7cEste jogador j\u00e1 esta no cargo mais baixo da fac\u00e7\u00e3o caso queira expulsa-lo use /f expulsar \u00a7c" + target.getName() + "\u00a7c");
            return;
        }
        if (cargomp == Rel.MEMBER) {
            facSender.msg("\u00a7e" + this.msender.getRole().getPrefix() + this.msender.getName() + "\u00a7e rebaixou \"\u00a7e" + target.getName() + "\u00a7e\" para o cargo de recruta da fac\u00e7\u00e3o.");
            target.setRole(Rel.RECRUIT);
            return;
        }
        if (cargomp == Rel.OFFICER) {
            if (cargoms == Rel.OFFICER) {
                this.msender.message("\u00a7cApenas o l\u00edder da fac\u00e7\u00e3o pode rebaixar um capit\u00e3o.");
                return;
            }
            facSender.msg("\u00a7e" + this.msender.getRole().getPrefix() + this.msender.getName() + "\u00a7e rebaixou \"\u00a7e" + target.getName() + "\u00a7e\" para o cargo de membro da fac\u00e7\u00e3o.");
            target.setRole(Rel.MEMBER);
            return;
        }
    }
}

