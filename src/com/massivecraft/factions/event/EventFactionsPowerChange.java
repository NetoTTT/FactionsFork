/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.event.HandlerList
 */
package com.massivecraft.factions.event;

import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsAbstractSender;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;

public class EventFactionsPowerChange
extends EventFactionsAbstractSender {
    private static final HandlerList handlers = new HandlerList();
    private final MPlayer mplayer;
    private final PowerChangeReason reason;
    private double newPower;

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

    public PowerChangeReason getReason() {
        return this.reason;
    }

    public double getNewPower() {
        return this.newPower;
    }

    public void setNewPower(double newPower) {
        this.newPower = newPower;
    }

    public EventFactionsPowerChange(CommandSender sender, MPlayer mplayer, PowerChangeReason reason, double newPower) {
        super(sender);
        this.mplayer = mplayer;
        this.reason = reason;
        this.newPower = mplayer.getLimitedPower(newPower);
    }

    public static enum PowerChangeReason {
        TIME,
        DEATH,
        COMMAND,
        UNDEFINED;

    }
}

