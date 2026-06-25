/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.ps.PS
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.ps.PS;
import java.util.Set;

public abstract class CmdFactionsSetX
extends FactionsCommand {
    private String formatOne = null;
    private String formatMany = null;
    private boolean claim = true;
    private int factionArgIndex = 0;

    public String getFormatOne() {
        return this.formatOne;
    }

    public void setFormatOne(String formatOne) {
        this.formatOne = formatOne;
    }

    public String getFormatMany() {
        return this.formatMany;
    }

    public void setFormatMany(String formatMany) {
        this.formatMany = formatMany;
    }

    public boolean isClaim() {
        return this.claim;
    }

    public void setClaim(boolean claim) {
        this.claim = claim;
    }

    public int getFactionArgIndex() {
        return this.factionArgIndex;
    }

    public void setFactionArgIndex(int factionArgIndex) {
        this.factionArgIndex = factionArgIndex;
    }

    public CmdFactionsSetX(boolean claim) {
        this.setClaim(claim);
        this.setSetupEnabled(false);
    }

    public void perform() throws MassiveException {
        Faction newFaction = this.getNewFaction();
        Set<PS> chunks = this.getChunks();
        this.msender.tryClaim(newFaction, chunks, this.getFormatOne(), this.getFormatMany());
    }

    public abstract Set<PS> getChunks() throws MassiveException;

    public Faction getNewFaction() throws MassiveException {
        if (this.isClaim()) {
            return (Faction)this.readArgAt(this.getFactionArgIndex(), this.msenderFaction);
        }
        return FactionColl.get().getNone();
    }
}

