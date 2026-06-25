/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.event.HandlerList
 */
package com.massivecraft.factions.event;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.event.EventFactionsAbstractSender;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;

public class EventFactionsDisband
extends EventFactionsAbstractSender {
    private static final HandlerList handlers = new HandlerList();
    private final Faction faction;
    private final String factionId;

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Faction getFaction() {
        return this.faction;
    }

    public String getFactionId() {
        return this.factionId;
    }

    public EventFactionsDisband(CommandSender sender, Faction faction) {
        super(sender);
        this.faction = faction;
        this.factionId = faction.getId();
    }
}

