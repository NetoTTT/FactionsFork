/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.command.Visibility
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.CmdFactionsPoderAbstract;
import com.massivecraft.factions.cmd.type.TypeMPlayer;
import com.massivecraft.massivecore.command.Visibility;

public class CmdFactionsPoderPlayer
extends CmdFactionsPoderAbstract {
    public CmdFactionsPoderPlayer() {
        super(TypeMPlayer.get(), "player");
        this.setVisibility(Visibility.SECRET);
        this.setDesc("\u00a76 poder p \u00a7e<player> <quantia> \u00a78-\u00a77 Adiciona poder a um player.");
    }
}

