/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.command.requirement.Requirement
 *  com.massivecraft.massivecore.command.requirement.RequirementIsPlayer
 *  com.massivecraft.massivecore.command.type.Type
 *  com.massivecraft.massivecore.command.type.primitive.TypeBooleanOn
 *  com.massivecraft.massivecore.ps.PS
 *  org.bukkit.Location
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.Type;
import com.massivecraft.massivecore.command.type.primitive.TypeBooleanOn;
import com.massivecraft.massivecore.ps.PS;
import java.util.Collections;
import java.util.Set;
import org.bukkit.Location;

public class CmdFactionsSetAuto
extends FactionsCommand {
    private boolean claim = true;

    public boolean isClaim() {
        return this.claim;
    }

    public void setClaim(boolean claim) {
        this.claim = claim;
    }

    public CmdFactionsSetAuto(boolean claim) {
        this.setClaim(claim);
        this.setSetupEnabled(false);
        this.addParameter((Type)TypeBooleanOn.get(), "ativar", "desativar");
        this.addAliases(new String[]{"auto"});
        this.addRequirements(new Requirement[]{RequirementIsPlayer.get()});
        this.setDesc("\u00a76 claim auto \u00a7e[on/off] \u00a78-\u00a77 Conquista as terras automaticamente enquanto voc\u00ea anda.");
    }

    public void perform() throws MassiveException {
        boolean old = this.msender.getAutoClaimFaction() != null;
        boolean target = (Boolean)this.readArg(!old);
        if (!old && !target) {
            this.msender.msg("\u00a7aO modo auto conquistar terras j\u00e1 est\u00e1 \u00a7cdesativado\u00a7a.");
        } else if (old && target) {
            this.msender.msg("\u00a7aO modo auto conquistar terras j\u00e1 est\u00e1 \u00a72ativado\u00a7a.");
        } else {
            Faction newFaction = this.claim ? this.msenderFaction : FactionColl.get().getNone();
            if (newFaction == null || newFaction == this.msender.getAutoClaimFaction()) {
                this.msender.setAutoClaimFaction(null);
                this.msg("\u00a7aModo auto conquistar terras \u00a7cdesativado\u00a7a.");
                return;
            }
            if (newFaction.isNormal() && !MPerm.getPermTerritory().has(this.msender, newFaction, true)) {
                return;
            }
            this.msender.setAutoClaimFaction(newFaction);
            this.msg("\u00a7aModo auto conquistar terras \u00a72ativado\u00a7a.");
            PS chunk = PS.valueOf((Location)this.me.getLocation()).getChunk(true);
            Set<PS> chunks = Collections.singleton(chunk);
            this.msender.tryClaim(newFaction, chunks);
        }
    }
}

