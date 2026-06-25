/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.Colorized
 *  org.bukkit.ChatColor
 */
package com.massivecraft.factions;

import com.massivecraft.massivecore.Colorized;
import org.bukkit.ChatColor;

public enum AccessStatus implements Colorized
{
    STANDARD(ChatColor.YELLOW, null),
    ELEVATED(ChatColor.GREEN, true),
    DECREASED(ChatColor.RED, false);

    private final ChatColor color;
    private final Boolean access;

    public ChatColor getColor() {
        return this.color;
    }

    public Boolean hasAccess() {
        return this.access;
    }

    private AccessStatus(ChatColor color, Boolean access) {
        this.color = color;
        this.access = access;
    }
}

