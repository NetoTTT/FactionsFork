/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.ps.PS
 *  org.bukkit.command.CommandSender
 *  org.bukkit.event.HandlerList
 */
package com.massivecraft.factions.event;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.event.EventFactionsAbstractSender;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;

public class EventFactionsHomeChange
extends EventFactionsAbstractSender {
    private static final HandlerList handlers = new HandlerList();
    private final Faction faction;
    private PS newHome;

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Faction getFaction() {
        return this.faction;
    }

    public PS getNewHome() {
        return this.newHome;
    }

    public void setNewHome(PS newHome) {
        this.newHome = newHome;
    }

    public EventFactionsHomeChange(CommandSender sender, Faction faction, PS newHome) {
        super(sender);
        this.faction = faction;
        this.newHome = newHome;
    }
}

