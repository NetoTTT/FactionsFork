/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.command.type.Type
 *  com.massivecraft.massivecore.command.type.primitive.TypeString
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.CmdFactionsSetX;
import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.Type;
import com.massivecraft.massivecore.command.type.primitive.TypeString;

public abstract class CmdFactionsSetXAll
extends CmdFactionsSetX {
    public CmdFactionsSetXAll(boolean claim) {
        super(claim);
        this.addParameter((Type)TypeString.get(), "all");
        this.addParameter((Type)TypeFaction.get(), "fac\u00e7\u00e3o");
        if (claim) {
            this.addParameter((Type)TypeFaction.get(), "novaFac\u00e7\u00e3o");
            this.setFactionArgIndex(2);
        }
    }

    public Faction getOldFaction() throws MassiveException {
        return (Faction)this.readArgAt(1);
    }
}

