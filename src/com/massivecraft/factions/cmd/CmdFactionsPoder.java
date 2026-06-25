/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.command.Visibility
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.CmdFactionsPoderFaction;
import com.massivecraft.factions.cmd.CmdFactionsPoderPlayer;
import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.massivecore.command.Visibility;

public class CmdFactionsPoder
extends FactionsCommand {
    public CmdFactionsPoderPlayer cmdFactionsPoderPlayer;
    public CmdFactionsPoderFaction cmdFactionsPoderFaction;

    public CmdFactionsPoder() {
        this.addAliases(new String[]{"powerboost", "power", "pb"});
        this.setDesc("\u00a76 poder \u00a78-\u00a77 Adiciona ou remove poder de um player ou fac\u00e7\u00e3o.");
        this.setVisibility(Visibility.SECRET);
        this.cmdFactionsPoderPlayer = new CmdFactionsPoderPlayer();
        this.cmdFactionsPoderFaction = new CmdFactionsPoderFaction();
        this.addChild(this.cmdFactionsPoderPlayer);
        this.addChild(this.cmdFactionsPoderFaction);
        this.setVisibility(Visibility.SECRET);
    }
}

