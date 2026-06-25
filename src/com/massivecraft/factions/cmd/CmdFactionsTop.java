/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.command.requirement.Requirement
 *  com.massivecraft.massivecore.command.requirement.RequirementIsPlayer
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;

public class CmdFactionsTop
extends FactionsCommand {
    public CmdFactionsTop() {
        this.addAliases(new String[]{"rank", "ranking"});
        this.addRequirements(new Requirement[]{RequirementIsPlayer.get()});
        this.setDesc("\u00a76 top \u00a78-\u00a77 Abre o menu do Rank das fac\u00e7\u00f5es.");
    }
}

