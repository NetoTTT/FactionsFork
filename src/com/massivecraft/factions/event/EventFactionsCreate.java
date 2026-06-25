/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.event.HandlerList
 */
package com.massivecraft.factions.event;

import com.massivecraft.factions.event.EventFactionsAbstractSender;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;

public class EventFactionsCreate
extends EventFactionsAbstractSender {
    private static final HandlerList handlers = new HandlerList();
    private final String factionId;
    private final String factionName;

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public final String getFactionId() {
        return this.factionId;
    }

    public final String getFactionName() {
        return this.factionName;
    }

    public EventFactionsCreate(CommandSender sender, String factionId, String factionName) {
        super(sender);
        this.factionId = factionId;
        this.factionName = factionName;
    }
}

