/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.util.extractor.Extractor
 */
package com.massivecraft.factions;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.util.extractor.Extractor;

public class ExtractorFactionAccountId
implements Extractor {
    private static ExtractorFactionAccountId i = new ExtractorFactionAccountId();

    public static ExtractorFactionAccountId get() {
        return i;
    }

    public Object extract(Object o) {
        if (o instanceof Faction) {
            String factionId = ((Faction)o).getId();
            if (factionId == null) {
                return null;
            }
            return "fac\u00e7\u00e3o-" + factionId;
        }
        return null;
    }
}

