/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.command.Visibility
 *  com.massivecraft.massivecore.command.type.Type
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.FactionsParticipator;
import com.massivecraft.factions.cmd.CmdFactionsPoderAbstract;
import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.massivecore.command.Visibility;
import com.massivecraft.massivecore.command.type.Type;

public class CmdFactionsPoderFaction
extends CmdFactionsPoderAbstract {
    public CmdFactionsPoderFaction() {
        super((Type<? extends FactionsParticipator>)TypeFaction.get(), "faction");
        this.setVisibility(Visibility.SECRET);
        this.setDesc("\u00a76 poder f \u00a7e<fac\u00e7\u00e3o> <quantia> \u00a78-\u00a77 Adiciona poder a um fac\u00e7\u00e3o.");
    }
}

