/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.command.requirement.Requirement
 *  com.massivecraft.massivecore.command.requirement.RequirementIsPlayer
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.CmdFactionsSetAuto;
import com.massivecraft.factions.cmd.CmdFactionsSetOne;
import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.req.ReqHasFaction;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;

public class CmdFactionsClaim
extends FactionsCommand {
    public CmdFactionsSetOne cmdFactionsClaimOne;
    public CmdFactionsSetAuto cmdFactionsClaimAuto;

    public CmdFactionsClaim() {
        this.addAliases(new String[]{"proteger", "conquistar", "dominar"});
        this.setDesc("\u00a76 claim \u00a78-\u00a77 Conquista territ\u00f3rios para a sua fac\u00e7\u00e3o.");
        this.addRequirements(new Requirement[]{ReqHasFaction.get()});
        this.addRequirements(new Requirement[]{RequirementIsPlayer.get()});
        this.cmdFactionsClaimOne = new CmdFactionsSetOne(true);
        this.cmdFactionsClaimAuto = new CmdFactionsSetAuto(true);
    }
}

