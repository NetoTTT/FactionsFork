/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.PriorityLines
 *  org.bukkit.command.CommandSender
 *  org.bukkit.event.HandlerList
 */
package com.massivecraft.factions.event;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.event.EventFactionsAbstractSender;
import com.massivecraft.massivecore.PriorityLines;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;

public class EventFactionsFactionShowAsync
extends EventFactionsAbstractSender {
    private static final HandlerList handlers = new HandlerList();
    private final Faction faction;
    private final Map<String, PriorityLines> idPriorityLiness;

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Faction getFaction() {
        return this.faction;
    }

    public Map<String, PriorityLines> getIdPriorityLiness() {
        return this.idPriorityLiness;
    }

    public EventFactionsFactionShowAsync(CommandSender sender, Faction faction) {
        super(true, sender);
        this.faction = faction;
        this.idPriorityLiness = new HashMap<String, PriorityLines>();
    }
}

