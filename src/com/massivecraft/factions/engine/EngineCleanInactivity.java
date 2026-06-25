/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.Engine
 *  com.massivecraft.massivecore.event.EventMassiveCorePlayerCleanInactivityToleranceMillis
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 */
package com.massivecraft.factions.engine;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.entity.MPlayerColl;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.event.EventMassiveCorePlayerCleanInactivityToleranceMillis;
import java.util.Map;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class EngineCleanInactivity
extends Engine {
    private static EngineCleanInactivity i = new EngineCleanInactivity();

    public static EngineCleanInactivity get() {
        return i;
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void ageBonus(EventMassiveCorePlayerCleanInactivityToleranceMillis event) {
        if (event.getColl() != MPlayerColl.get()) {
            return;
        }
        this.applyPlayerAgeBonus(event);
        this.applyFactionAgeBonus(event);
    }

    public void applyPlayerAgeBonus(EventMassiveCorePlayerCleanInactivityToleranceMillis event) {
        Long bonus;
        Long firstPlayed = event.getEntity().getFirstPlayed();
        Long age = 0L;
        if (firstPlayed != null) {
            age = System.currentTimeMillis() - firstPlayed;
        }
        if ((bonus = this.calculateBonus(age, MConf.get().cleanInactivityToleranceMillisPlayerAgeToBonus)) == null) {
            return;
        }
        event.getToleranceCauseMillis().put("Player B\u00f4nus de tempo (idade)", bonus);
    }

    public void applyFactionAgeBonus(EventMassiveCorePlayerCleanInactivityToleranceMillis event) {
        Long bonus;
        Faction faction = ((MPlayer)event.getEntity()).getFaction();
        long age = 0L;
        if (!faction.isNone()) {
            age = faction.getAge();
        }
        if ((bonus = this.calculateBonus(age, MConf.get().cleanInactivityToleranceMillisFactionAgeToBonus)) == null) {
            return;
        }
        event.getToleranceCauseMillis().put("Faction B\u00f4nus de tempo (idade)", bonus);
    }

    private Long calculateBonus(long age, Map<Long, Long> ageToBonus) {
        if (ageToBonus.isEmpty()) {
            return null;
        }
        Long bonus = 0L;
        for (Map.Entry<Long, Long> entry : ageToBonus.entrySet()) {
            Long value;
            Long key = entry.getKey();
            if (key == null || (value = entry.getValue()) == null || age < key) continue;
            bonus = value;
            break;
        }
        return bonus;
    }
}

