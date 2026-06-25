/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.command.Visibility
 *  com.massivecraft.massivecore.command.requirement.Requirement
 *  com.massivecraft.massivecore.command.requirement.RequirementIsPlayer
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.req.ReqHasFaction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.massivecore.command.Visibility;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;

public class CmdFactionsBau
extends FactionsCommand {
    public CmdFactionsBau() {
        this.addAliases(new String[]{"chest"});
        this.addRequirements(new Requirement[]{RequirementIsPlayer.get()});
        this.addRequirements(new Requirement[]{ReqHasFaction.get()});
        this.setDesc("\u00a76 bau \u00a78-\u00a77 Abre o ba\u00fa virtual da fac\u00e7\u00e3o.");
        if (!MConf.get().colocarIconeDoFBauNoMenuGUI) {
            this.setVisibility(Visibility.INVISIBLE);
        }
    }
}

