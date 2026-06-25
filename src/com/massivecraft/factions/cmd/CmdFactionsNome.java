/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.command.requirement.Requirement
 *  com.massivecraft.massivecore.command.type.Type
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.req.ReqHasFaction;
import com.massivecraft.factions.cmd.type.TypeFactionNameLenient;
import com.massivecraft.factions.engine.EngineSobAtaque;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.event.EventFactionsNameChange;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.type.Type;

public class CmdFactionsNome
extends FactionsCommand {
    public CmdFactionsNome() {
        this.addParameter((Type)TypeFactionNameLenient.get(), "novoNome");
        this.addAliases(new String[]{"name", "renomear", "rename"});
        this.addRequirements(new Requirement[]{ReqHasFaction.get()});
        this.setDesc("\u00a76 nome \u00a7e<nome> \u00a78-\u00a77 Altera o nome da fac\u00e7\u00e3o.");
    }

    public void perform() throws MassiveException {
        if (this.msender.getRole() != Rel.LEADER && !this.msender.isOverriding()) {
            this.msender.message("\u00a7cApenas o l\u00edder da fac\u00e7\u00e3o pode alterar o nome da fac\u00e7\u00e3o.");
            return;
        }
        if (EngineSobAtaque.factionattack.containsKey(this.msenderFaction.getName())) {
            this.msender.message("\u00a7cVoc\u00ea n\u00e3o pode alterar o nome da sua fac\u00e7\u00e3o enquanto ela estiver sobre ataque!");
            return;
        }
        String newName = (String)this.readArg();
        Faction faction = this.msenderFaction;
        EventFactionsNameChange event = new EventFactionsNameChange(this.sender, faction, newName);
        event.run();
        if (event.isCancelled()) {
            return;
        }
        newName = event.getNewName();
        faction.setName(newName);
        faction.msg("\u00a7e%s\u00a7e definiu o nome da fac\u00e7\u00e3o para \u00a7f%s\u00a7e.", String.valueOf(this.msender.getRole().getPrefix()) + this.msender.getName(), newName);
    }
}

