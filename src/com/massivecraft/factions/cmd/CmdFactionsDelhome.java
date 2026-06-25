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
import com.massivecraft.factions.event.EventFactionsHomeChange;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;

public class CmdFactionsDelhome
extends FactionsCommand {
    public CmdFactionsDelhome() {
        this.addAliases(new String[]{"unsethome", "removerbase", "delbase"});
        this.setDesc("\u00a76 delhome \u00a78-\u00a77 Deleta a home da fac\u00e7\u00e3o.");
        this.addRequirements(new Requirement[]{ReqHasFaction.get()});
    }

    public void perform() throws MassiveException {
        if (this.msender.getRole() != Rel.LEADER && this.msender.getRole() != Rel.OFFICER && !this.msender.isOverriding()) {
            this.msender.message("\u00a7cVoc\u00ea precisar ser capit\u00e3o ou superior para poder deletar a home da fac\u00e7\u00e3o.");
            return;
        }
        if (!this.msenderFaction.hasHome()) {
            this.msender.msg("\u00a7cA sua fac\u00e7\u00e3o ainda n\u00e3o definiu a home da fac\u00e7\u00e3o.");
            return;
        }
        EventFactionsHomeChange event = new EventFactionsHomeChange(this.sender, this.msenderFaction, null);
        event.run();
        if (event.isCancelled()) {
            return;
        }
        this.msenderFaction.setHome(null);
        this.msenderFaction.msg("\u00a7e%s\u00a7e deletou a home da fac\u00e7\u00e3o!", String.valueOf(this.msender.getRole().getPrefix()) + this.msender.getName());
    }
}

