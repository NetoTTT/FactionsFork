/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.store.Coll
 */
package com.massivecraft.factions.entity;

import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.massivecore.store.Coll;
import java.util.ArrayList;
import java.util.List;

public class MFlagColl
extends Coll<MFlag> {
    private static MFlagColl i = new MFlagColl();

    public static MFlagColl get() {
        return i;
    }

    private MFlagColl() {
        this.setLowercasing(true);
    }

    public void onTick() {
        super.onTick();
    }

    public void setActive(boolean active) {
        super.setActive(active);
        if (!active) {
            return;
        }
        MFlag.setupStandardFlags();
    }

    public List<MFlag> getAll(boolean registered) {
        ArrayList<MFlag> ret = new ArrayList<MFlag>();
        for (MFlag mflag : this.getAll()) {
            if (mflag.isRegistered() != registered) continue;
            ret.add(mflag);
        }
        return ret;
    }
}

