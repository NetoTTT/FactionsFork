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

public class CmdFactionsTransferir
extends FactionsCommand {
    public CmdFactionsTransferir() {
        this.addAliases(new String[]{"lider", "lideran\u00e7a", "lideranca"});
        this.addParameter(TypeMPlayer.get(), "jogador");
        this.addRequirements(new Requirement[]{ReqHasFaction.get()});
        this.setDesc("\u00a76 transferir \u00a7e<player> \u00a78-\u00a77 Transfere a lideran\u00e7a da fac\u00e7\u00e3o.");
    }

    public void perform() throws MassiveException {
        MPlayer target = (MPlayer)this.readArg(this.msender);
        Faction facSender = this.msender.getFaction();
        Faction facTarget = target.getFaction();
        Rel cargoSender = this.msender.getRole();
        if (cargoSender != Rel.LEADER) {
            this.msender.message("\u00a7cApenas o l\u00edder da fac\u00e7\u00e3o pode promover um capit\u00e3o para l\u00edder.");
            return;
        }
        if (this.msender == target) {
            this.msender.message("\u00a7cVoc\u00ea n\u00e3o pode transferir a lideran\u00e7a para voc\u00ea mesmo");
            return;
        }
        if (facSender != facTarget) {
            this.msender.message("\u00a7cEste jogador n\u00e3o esta na sua fac\u00e7\u00e3o.");
            return;
        }
        this.msender.setRole(Rel.OFFICER);
        target.setRole(Rel.LEADER);
        facSender.msg("\u00a7e" + this.msender.getName() + "\u00a7e transferiu a lidera\u00e7\u00e3o da fac\u00e7\u00e3o para \"\u00a7e" + target.getName() + "\"\u00a7e.");
    }
}

