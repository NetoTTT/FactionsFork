package com.massivecraft.factions.integration.V19;

import com.massivecraft.factions.Factions;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.MassivePlugin;

// AreaEffectCloudApplyEvent is 1.9+ — stub on 1.8.8
public class EngineV19
extends Engine {
    private static EngineV19 i = new EngineV19();

    public static EngineV19 get() {
        return i;
    }

    public MassivePlugin getActivePlugin() {
        return Factions.get();
    }
}
