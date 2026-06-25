/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.Colorized
 *  org.bukkit.ChatColor
 */
package com.massivecraft.factions.event;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.Colorized;
import org.bukkit.ChatColor;

public enum EventFactionsChunkChangeType implements Colorized
{
    NONE("nenhum", "nenhum", ChatColor.WHITE),
    BUY("conquistar", "conquistou", ChatColor.GREEN),
    SELL("abandonar", "abandonou", ChatColor.GREEN),
    CONQUER("conquistar", "conquistou", ChatColor.DARK_GREEN),
    PILLAGE("saquear", "saqueou", ChatColor.RED);

    public final String now;
    public final String past;
    public final ChatColor color;

    private EventFactionsChunkChangeType(String now, String past, ChatColor color) {
        this.now = now;
        this.past = past;
        this.color = color;
    }

    public ChatColor getColor() {
        return this.color;
    }

    public static EventFactionsChunkChangeType get(Faction oldFaction, Faction newFaction, Faction self) {
        if (newFaction == oldFaction) {
            return NONE;
        }
        if (oldFaction.isNone()) {
            return BUY;
        }
        if (newFaction.isNormal()) {
            return CONQUER;
        }
        if (oldFaction == self) {
            return SELL;
        }
        return PILLAGE;
    }
}

