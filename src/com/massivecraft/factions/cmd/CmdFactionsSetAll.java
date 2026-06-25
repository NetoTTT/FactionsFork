/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.command.requirement.Requirement
 *  com.massivecraft.massivecore.command.requirement.RequirementHasPerm
 *  com.massivecraft.massivecore.mixin.MixinWorld
 *  com.massivecraft.massivecore.ps.PS
 *  com.massivecraft.massivecore.util.MUtil
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Perm;
import com.massivecraft.factions.cmd.CmdFactionsSetXAll;
import com.massivecraft.factions.entity.Board;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.mixin.MixinWorld;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.MUtil;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class CmdFactionsSetAll
extends CmdFactionsSetXAll {
    public static final List<String> LIST_ALL = Collections.unmodifiableList(MUtil.list("a", "al", "all"));

    public CmdFactionsSetAll(boolean claim) {
        super(claim);
        this.addAliases(new String[]{"all"});
        Perm perm = claim ? Perm.CLAIM_ALL : Perm.UNCLAIM_ALL;
        this.addRequirements(new Requirement[]{RequirementHasPerm.get((Object)((Object)perm))});
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Set<PS> getChunks() throws MassiveException {
        String word = this.isClaim() ? "claim" : "unclaim";
        Set<PS> chunks = null;
        Faction oldFaction = this.getOldFaction();
        if (LIST_ALL.contains(this.argAt(0).toLowerCase())) {
            chunks = BoardColl.get().getChunks(oldFaction);
            this.setFormatOne("\u00a7a%s\u00a7a %s \u00a7d%d \u00a7achunk usando \u00a7a" + word + " \u00a7aall.");
            this.setFormatMany("\u00a7a%s\u00a7a %s \u00a7d%d \u00a7achunks usando \u00a7a" + word + " \u00a7aall.");
            return chunks;
        }
        String worldId = null;
        if (LIST_ALL.contains(this.argAt(0).toLowerCase())) {
            if (this.me == null) {
                this.msg("\u00a7cPara utilizar este comando pelo console voce deve especificar o nome do mapa no qual voce quer executar a acao.");
                return null;
            }
            worldId = this.me.getWorld().getName();
        } else {
            worldId = this.argAt(0);
            if (worldId == null) {
                return null;
            }
        }
        Board board = (Board)BoardColl.get().get(worldId);
        chunks = board.getChunks(oldFaction);
        String worldDisplayName = MixinWorld.get().getWorldDisplayName(worldId);
        this.setFormatOne("\u00a7a%s\u00a7a %s \u00a7d%d \u00a7achunk usando " + word + " \u00a7a" + worldDisplayName + "\u00a7a.");
        this.setFormatMany("\u00a7a%s\u00a7a %s \u00a7d%d \u00a7achunks usando " + word + " \u00a7a" + worldDisplayName + "\u00a7a.");
        return chunks;
    }
}

