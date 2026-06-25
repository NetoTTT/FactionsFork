/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.Engine
 *  com.massivecraft.massivecore.Integration
 */
package com.massivecraft.factions.integration.spigot;

import com.massivecraft.factions.integration.spigot.EngineSpigot;
import com.massivecraft.factions.integration.spigot.PredicateSpigot;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.Integration;

public class IntegrationSpigot
extends Integration {
    private static IntegrationSpigot i = new IntegrationSpigot();

    public static IntegrationSpigot get() {
        return i;
    }

    private IntegrationSpigot() {
        this.setPredicate(PredicateSpigot.get());
    }

    public Engine getEngine() {
        return EngineSpigot.get();
    }
}

