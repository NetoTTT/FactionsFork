/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Chunk
 *  org.bukkit.event.HandlerList
 */
package com.massivecraft.factions.event;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.event.EventFactionsAbstract;
import org.bukkit.Chunk;
import org.bukkit.event.HandlerList;

public class EventFactionsFinishAttack
extends EventFactionsAbstract {
    private static final HandlerList handlers = new HandlerList();
    private final Faction target;
    private final Chunk chunk;

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Faction getFaction() {
        return this.target;
    }

    public Chunk getChunk() {
        return this.chunk;
    }

    public EventFactionsFinishAttack(Chunk chunk, Faction target) {
        this.chunk = chunk;
        this.target = target;
    }
}

