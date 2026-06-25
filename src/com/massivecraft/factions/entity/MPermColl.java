/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.store.Coll
 */
package com.massivecraft.factions.entity;

import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.massivecore.store.Coll;
import java.util.ArrayList;
import java.util.List;

public class MPermColl
extends Coll<MPerm> {
    private static MPermColl i = new MPermColl();

    public static MPermColl get() {
        return i;
    }

    private MPermColl() {
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
        MPerm.setupStandardPerms();
    }

    public List<MPerm> getAll(boolean registered) {
        ArrayList<MPerm> ret = new ArrayList<MPerm>();
        for (MPerm mperm : this.getAll()) {
            if (mperm.isRegistered() != registered) continue;
            ret.add(mperm);
        }
        return ret;
    }
}

