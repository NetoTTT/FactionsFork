/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.command.requirement.Requirement
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.CmdFactionsSetAll;
import com.massivecraft.factions.cmd.CmdFactionsSetOne;
import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.req.ReqHasFaction;
import com.massivecraft.massivecore.command.requirement.Requirement;

public class CmdFactionsUnclaim
extends FactionsCommand {
    public CmdFactionsSetOne cmdFactionsUnclaimOne;
    public CmdFactionsSetAll cmdFactionsUnclaimAll;

    public CmdFactionsUnclaim() {
        this.addAliases(new String[]{"desproteger", "abandonar"});
        this.setDesc("\u00a76 unclaim \u00a78-\u00a77 Abandona territ\u00f3rios da sua fac\u00e7\u00e3o.");
        this.addRequirements(new Requirement[]{ReqHasFaction.get()});
        this.cmdFactionsUnclaimOne = new CmdFactionsSetOne(false);
        this.cmdFactionsUnclaimAll = new CmdFactionsSetAll(false);
    }
}

