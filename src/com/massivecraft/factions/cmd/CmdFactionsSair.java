/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.command.requirement.Requirement
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.req.ReqHasFaction;
import com.massivecraft.factions.engine.EngineSobAtaque;
import com.massivecraft.massivecore.command.requirement.Requirement;

public class CmdFactionsSair
extends FactionsCommand {
    public CmdFactionsSair() {
        this.addAliases(new String[]{"leave", "deixar"});
        this.addRequirements(new Requirement[]{ReqHasFaction.get()});
        this.setDesc("\u00a76 sair \u00a78-\u00a77 Abandona a sua fac\u00e7\u00e3o atual.");
    }

    public void perform() {
        if (EngineSobAtaque.factionattack.containsKey(this.msenderFaction.getName())) {
            this.msender.message("\u00a7cVoc\u00ea n\u00e3o pode abandonar a sua fac\u00e7\u00e3o enquanto ela estiver sobre ataque!");
            return;
        }
        this.msender.leave();
    }
}

