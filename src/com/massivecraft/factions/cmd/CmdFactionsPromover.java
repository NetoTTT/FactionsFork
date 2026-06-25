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

public class CmdFactionsPromover
extends FactionsCommand {
    public CmdFactionsPromover() {
        this.addAliases(new String[]{"promote", "up"});
        this.addParameter(TypeMPlayer.get(), "jogador");
        this.addRequirements(new Requirement[]{ReqHasFaction.get()});
        this.setDesc("\u00a76 promover \u00a7e<player> \u00a78-\u00a77 Promove um player de cargo.");
    }

    public void perform() throws MassiveException {
        if (this.msender.getRole() != Rel.LEADER && this.msender.getRole() != Rel.OFFICER && !this.msender.isOverriding()) {
            this.msender.message("\u00a7cVoc\u00ea precisar ser capit\u00e3o ou superior para poder administrar os cargos da fac\u00e7\u00e3o.");
            return;
        }
        MPlayer target = (MPlayer)this.readArg(this.msender);
        Faction facSender = this.msender.getFaction();
        Faction facTarget = target.getFaction();
        if (this.msender == target) {
            this.msender.message("\u00a7cVoc\u00ea n\u00e3o pode promover voc\u00ea mesmo.");
            return;
        }
        if (facSender != facTarget) {
            this.msender.message("\u00a7cEste jogador n\u00e3o esta na sua fac\u00e7\u00e3o.");
            return;
        }
        Rel cargoms = this.msender.getRole();
        Rel cargomp = target.getRole();
        if (cargomp == Rel.LEADER) {
            this.msender.message("\u00a7c" + target.getName() + "\u00a7c j\u00e1 \u00e9 o l\u00edder da fac\u00e7\u00e3o.");
            return;
        }
        if (cargomp == Rel.RECRUIT) {
            facSender.msg("\u00a7e" + this.msender.getRole().getPrefix() + this.msender.getName() + "\u00a7e promoveu \"\u00a7e" + target.getName() + "\u00a7e\" para o cargo de membro da fac\u00e7\u00e3o.");
            target.setRole(Rel.MEMBER);
            return;
        }
        if (cargomp == Rel.MEMBER) {
            if (cargoms == Rel.OFFICER) {
                this.msender.message("\u00a7cApenas o l\u00edder da fac\u00e7\u00e3o pode promover um membro para capit\u00e3o.");
                return;
            }
            facSender.msg("\u00a7e" + this.msender.getRole().getPrefix() + this.msender.getName() + "\u00a7e promoveu \"\u00a7e" + target.getName() + "\u00a7e\"\u00a7e para o cargo de capit\u00e3o da fac\u00e7\u00e3o.");
            target.setRole(Rel.OFFICER);
            return;
        }
        if (cargomp == Rel.OFFICER) {
            if (cargoms == Rel.LEADER) {
                this.msender.message("\u00a7cEste jogador j\u00e1 \u00e9 capit\u00e3o da fac\u00e7\u00e3o, caso queira transferir a lideran\u00e7a da fac\u00e7\u00e3o use /f transferir \u00a7c" + target.getName() + "\u00a7c");
                return;
            }
            this.msender.message("\u00a7cApenas o l\u00edder da fac\u00e7\u00e3o pode promover um capit\u00e3o para l\u00edder.");
            return;
        }
    }
}

