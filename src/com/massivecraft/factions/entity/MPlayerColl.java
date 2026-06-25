/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.store.SenderColl
 */
package com.massivecraft.factions.entity;

import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.store.SenderColl;

public class MPlayerColl
extends SenderColl<MPlayer> {
    private static MPlayerColl i = new MPlayerColl();

    public static MPlayerColl get() {
        return i;
    }

    public MPlayerColl() {
        this.setCleanTaskEnabled(true);
    }

    public void onTick() {
        super.onTick();
    }

    public long getCleanInactivityToleranceMillis() {
        return MConf.get().cleanInactivityToleranceMillis;
    }
}

