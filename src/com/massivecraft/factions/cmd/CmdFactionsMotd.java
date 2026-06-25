/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.command.requirement.Requirement
 *  com.massivecraft.massivecore.command.type.Type
 *  com.massivecraft.massivecore.command.type.TypeNullable
 *  com.massivecraft.massivecore.command.type.primitive.TypeString
 *  com.massivecraft.massivecore.util.Txt
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.req.ReqHasFaction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsMotdChange;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.type.Type;
import com.massivecraft.massivecore.command.type.TypeNullable;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.util.Txt;

public class CmdFactionsMotd
extends FactionsCommand {
    public CmdFactionsMotd() {
        this.addAliases(new String[]{"mensagem"});
        this.addParameter((Type)TypeNullable.get((Type)TypeString.get()), "novaMotd", "erro", true);
        this.addRequirements(new Requirement[]{ReqHasFaction.get()});
        this.setDesc("\u00a76 motd \u00a7e[mensagem] \u00a78-\u00a77 Altera ou mostra a mensagem da fac\u00e7\u00e3o.");
    }

    public void perform() throws MassiveException {
        if (this.msender.getRole() != Rel.LEADER && this.msender.getRole() != Rel.OFFICER && !this.msender.isOverriding()) {
            this.msender.message("\u00a7cVoc\u00ea precisar ser capit\u00e3o ou superior para poder alterar a motd da fac\u00e7\u00e3o.");
            return;
        }
        if (!this.argIsSet(0)) {
            this.msender.msg("\u00a7cArgumentos insuficientes, use /f motd <mensagem>");
            return;
        }
        String target = (String)this.readArg();
        target = target.trim();
        target = Txt.parse((String)target);
        EventFactionsMotdChange event = new EventFactionsMotdChange(this.sender, this.msenderFaction, target);
        event.run();
        if (event.isCancelled()) {
            return;
        }
        target = event.getNewMotd().replace("\u00a7", "&");
        this.msenderFaction.setMotd(target);
        for (MPlayer follower : this.msenderFaction.getMPlayers()) {
            follower.msg("\u00a7e%s \u00a7edefiniu a motd da fac\u00e7\u00e3o para:\n\u00a77'\u00a7f%s\u00a77'", new Object[]{String.valueOf(this.msender.getRole().getPrefix()) + this.msender.getName(), this.msenderFaction.getMotdDesc()});
        }
    }
}

