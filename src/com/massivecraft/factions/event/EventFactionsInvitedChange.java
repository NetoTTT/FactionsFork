/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.event.HandlerList
 */
package com.massivecraft.factions.event;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsAbstractSender;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;

public class EventFactionsInvitedChange
extends EventFactionsAbstractSender {
    private static final HandlerList handlers = new HandlerList();
    private final MPlayer mplayer;
    private final Faction faction;
    private boolean newInvited;

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public MPlayer getMPlayer() {
        return this.mplayer;
    }

    public Faction getFaction() {
        return this.faction;
    }

    public boolean isNewInvited() {
        return this.newInvited;
    }

    public void setNewInvited(boolean newInvited) {
        this.newInvited = newInvited;
    }

    public EventFactionsInvitedChange(CommandSender sender, MPlayer mplayer, Faction faction, boolean newInvited) {
        super(sender);
        this.mplayer = mplayer;
        this.faction = faction;
        this.newInvited = newInvited;
    }
}

