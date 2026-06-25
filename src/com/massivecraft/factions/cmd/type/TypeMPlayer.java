/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.command.type.Type
 */
package com.massivecraft.factions.cmd.type;

import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.entity.MPlayerColl;
import com.massivecraft.massivecore.command.type.Type;

public class TypeMPlayer {
    public static Type<MPlayer> get() {
        return MPlayerColl.get().getTypeEntity();
    }
}

