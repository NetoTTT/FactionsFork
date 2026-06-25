/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.event.HandlerList
 *  org.bukkit.event.entity.EntityExplodeEvent
 */
package com.massivecraft.factions.event;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.event.EventFactionsAbstract;
import org.bukkit.Location;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EventFactionsEnteredInAttack
extends EventFactionsAbstract {
    private static final HandlerList handlers = new HandlerList();
    private final Faction target;
    private final Location location;
    private final boolean already;
    private final EntityExplodeEvent event;

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Faction getFaction() {
        return this.target;
    }

    public Location getLocation() {
        return this.location;
    }

    public boolean factionAlreadyInAtack() {
        return this.already;
    }

    public EntityExplodeEvent getEvent() {
        return this.event;
    }

    public EventFactionsEnteredInAttack(Faction target, Location location, boolean already, EntityExplodeEvent event) {
        this.target = target;
        this.location = location;
        this.already = already;
        this.event = event;
    }
}

