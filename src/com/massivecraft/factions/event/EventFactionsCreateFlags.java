/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.HandlerList
 */
package com.massivecraft.factions.event;

import com.massivecraft.factions.event.EventFactionsAbstract;
import org.bukkit.event.HandlerList;

public class EventFactionsCreateFlags
extends EventFactionsAbstract {
    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public EventFactionsCreateFlags() {
    }

    public EventFactionsCreateFlags(boolean isAsync) {
        super(isAsync);
    }
}

