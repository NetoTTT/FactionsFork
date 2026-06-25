/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.command.type.Type
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.CmdFactionsSetX;
import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.massivecore.command.type.Type;

public abstract class CmdFactionsSetXSimple
extends CmdFactionsSetX {
    public CmdFactionsSetXSimple(boolean claim) {
        super(claim);
        if (claim) {
            this.addParameter((Type)TypeFaction.get(), "fac\u00e7\u00e3o", "voc\u00ea");
            this.setFactionArgIndex(0);
        }
    }
}

