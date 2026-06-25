/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.Integration
 */
package com.massivecraft.factions.integration.V18;

import com.massivecraft.massivecore.Integration;

public class IntegrationV18
extends Integration {
    private static IntegrationV18 i = new IntegrationV18();

    public static IntegrationV18 get() {
        return i;
    }

    private IntegrationV18() {
        this.setClassNames(new String[]{"org.bukkit.entity.ArmorStand"});
    }
}

