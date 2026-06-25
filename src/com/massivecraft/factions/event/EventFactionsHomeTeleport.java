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

public class EventFactionsHomeTeleport
extends EventFactionsAbstractSender {
    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public EventFactionsHomeTeleport(CommandSender sender) {
        super(sender);
    }
}

