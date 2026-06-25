/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.store.Coll
 */
package com.massivecraft.factions.entity;

import com.massivecraft.factions.entity.MConf;
import com.massivecraft.massivecore.store.Coll;

public class MConfColl
extends Coll<MConf> {
    private static MConfColl i = new MConfColl();

    public static MConfColl get() {
        return i;
    }

    public void onTick() {
        super.onTick();
    }

    public void setActive(boolean active) {
        super.setActive(active);
        if (!active) {
            return;
        }
        MConf.i = (MConf)this.get("instance", true);
    }
}

