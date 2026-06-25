/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.command.MassiveCommand
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.command.MassiveCommand;

public class FactionsCommand
extends MassiveCommand {
    public MPlayer msender;
    public Faction msenderFaction;

    public FactionsCommand() {
        this.setSetupEnabled(true);
    }

    public void senderFields(boolean set) {
        this.msender = set ? MPlayer.get(this.sender) : null;
        this.msenderFaction = set ? this.msender.getFaction() : null;
    }
}

