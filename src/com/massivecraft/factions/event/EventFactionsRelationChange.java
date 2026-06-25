/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.event.HandlerList
 */
package com.massivecraft.factions.event;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.event.EventFactionsAbstractSender;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;

public class EventFactionsRelationChange
extends EventFactionsAbstractSender {
    private static final HandlerList handlers = new HandlerList();
    private final Faction faction;
    private final Faction otherFaction;
    private Rel newRelation;

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Faction getFaction() {
        return this.faction;
    }

    public Faction getOtherFaction() {
        return this.otherFaction;
    }

    public Rel getNewRelation() {
        return this.newRelation;
    }

    public void setNewRelation(Rel newRelation) {
        this.newRelation = newRelation;
    }

    public EventFactionsRelationChange(CommandSender sender, Faction faction, Faction otherFaction, Rel newRelation) {
        super(sender);
        this.faction = faction;
        this.otherFaction = otherFaction;
        this.newRelation = newRelation;
    }
}

