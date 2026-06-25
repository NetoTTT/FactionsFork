/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.command.requirement.Requirement
 *  com.massivecraft.massivecore.command.requirement.RequirementHasPerm
 *  com.massivecraft.massivecore.command.requirement.RequirementIsPlayer
 *  com.massivecraft.massivecore.ps.PS
 *  org.bukkit.Location
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Perm;
import com.massivecraft.factions.cmd.CmdFactionsSetXSimple;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.ps.PS;
import java.util.Collections;
import java.util.Set;
import org.bukkit.Location;

public class CmdFactionsSetOne
extends CmdFactionsSetXSimple {
    public CmdFactionsSetOne(boolean claim) {
        super(claim);
        this.addAliases(new String[]{"um", "one", "1"});
        this.addRequirements(new Requirement[]{RequirementIsPlayer.get()});
        Perm perm = claim ? Perm.CLAIM_ONE : Perm.UNCLAIM_ONE;
        this.addRequirements(new Requirement[]{RequirementHasPerm.get((Object)((Object)perm))});
    }

    @Override
    public Set<PS> getChunks() {
        PS chunk = PS.valueOf((Location)this.me.getLocation()).getChunk(true);
        Set<PS> chunks = Collections.singleton(chunk);
        return chunks;
    }
}

