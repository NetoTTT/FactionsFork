/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 */
package com.massivecraft.factions.util;

import org.bukkit.ChatColor;

public enum AsciiCompassDirection {
    N('N'),
    NE('/'),
    E('E'),
    SE('\\'),
    S('S'),
    SW('/'),
    W('W'),
    NW('\\'),
    NONE('+');

    private final char asciiChar;
    public static final ChatColor ACTIVE;
    public static final ChatColor INACTIVE;

    static {
        ACTIVE = ChatColor.RED;
        INACTIVE = ChatColor.YELLOW;
    }

    public char getAsciiChar() {
        return this.asciiChar;
    }

    private AsciiCompassDirection(char asciiChar) {
        this.asciiChar = asciiChar;
    }

    public String visualize(AsciiCompassDirection directionFacing) {
        boolean isFacing = this.isFacing(directionFacing);
        ChatColor color = this.getColor(isFacing);
        return String.valueOf(color.toString()) + this.getAsciiChar();
    }

    private boolean isFacing(AsciiCompassDirection directionFacing) {
        return this == directionFacing;
    }

    private ChatColor getColor(boolean active) {
        return active ? ACTIVE : INACTIVE;
    }

    public static AsciiCompassDirection getByDegrees(double degrees) {
        if ((degrees = (degrees - 157.0) % 360.0) < 0.0) {
            degrees += 360.0;
        }
        int ordinal = (int)Math.floor(degrees / 45.0);
        return AsciiCompassDirection.values()[ordinal];
    }
}

