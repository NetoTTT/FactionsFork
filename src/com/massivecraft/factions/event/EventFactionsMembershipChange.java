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

public class EventFactionsMembershipChange
extends EventFactionsAbstractSender {
    private static final HandlerList handlers = new HandlerList();
    private final MPlayer mplayer;
    private final Faction newFaction;
    private final MembershipChangeReason reason;

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public void setCancelled(boolean cancelled) {
        if (!this.reason.isCancellable()) {
            cancelled = false;
        }
        super.setCancelled(cancelled);
    }

    @Override
    public MPlayer getMPlayer() {
        return this.mplayer;
    }

    public Faction getNewFaction() {
        return this.newFaction;
    }

    public MembershipChangeReason getReason() {
        return this.reason;
    }

    public EventFactionsMembershipChange(CommandSender sender, MPlayer mplayer, Faction newFaction, MembershipChangeReason reason) {
        super(sender);
        this.mplayer = mplayer;
        this.newFaction = newFaction;
        this.reason = reason;
    }

    public static enum MembershipChangeReason {
        JOIN(true),
        CREATE(false),
        LEADER(true),
        RANK(true),
        LEAVE(true),
        KICK(true),
        DISBAND(false);

        private final boolean cancellable;

        public boolean isCancellable() {
            return this.cancellable;
        }

        private MembershipChangeReason(boolean cancellable) {
            this.cancellable = cancellable;
        }
    }
}

