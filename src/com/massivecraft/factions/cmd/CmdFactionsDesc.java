/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.command.requirement.Requirement
 *  com.massivecraft.massivecore.command.type.Type
 *  com.massivecraft.massivecore.command.type.TypeNullable
 *  com.massivecraft.massivecore.command.type.primitive.TypeString
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.req.ReqHasFaction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsDescriptionChange;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.type.Type;
import com.massivecraft.massivecore.command.type.TypeNullable;
import com.massivecraft.massivecore.command.type.primitive.TypeString;

public class CmdFactionsDesc
extends FactionsCommand {
    public CmdFactionsDesc() {
        this.addAliases(new String[]{"descricao", "description"});
        this.addParameter((Type)TypeNullable.get((Type)TypeString.get()), "novaDesc", "erro", true);
        this.addRequirements(new Requirement[]{ReqHasFaction.get()});
        this.setDesc("\u00a76 desc \u00a7e<desc> \u00a78-\u00a77 Altera a descri\u00e7\u00e3o da fac\u00e7\u00e3o.");
    }

    public void perform() throws MassiveException {
        for (String newDescription : this.getArgs()) {
            if (this.msender.getRole() != Rel.LEADER && this.msender.getRole() != Rel.OFFICER && !this.msender.isOverriding()) {
                this.msender.message("\u00a7cVoc\u00ea precisar ser capit\u00e3o ou superior para poder alterar a descri\u00e7\u00e3o da fac\u00e7\u00e3o.");
                return;
            }
            if (!this.argIsSet(0)) {
                this.msender.msg("\u00a7cArgumentos insuficientes, use /f desc <descri\u00e7\u00e3o>");
                return;
            }
            EventFactionsDescriptionChange event = new EventFactionsDescriptionChange(this.sender, this.msenderFaction, newDescription);
            event.run();
            if (event.isCancelled()) {
                return;
            }
            newDescription = event.getNewDescription().replace('&', '\u00a7');
            this.msenderFaction.setDescription(newDescription);
            for (MPlayer follower : this.msenderFaction.getMPlayers()) {
                follower.msg("\u00a7e%s \u00a7edefiniu a descri\u00e7\u00e3o da fac\u00e7\u00e3o para:\n\u00a77'\u00a7f%s\u00a77'", new Object[]{String.valueOf(this.msender.getRole().getPrefix()) + this.msender.getName(), this.msenderFaction.getDescriptionDesc()});
            }
        }
    }
}

