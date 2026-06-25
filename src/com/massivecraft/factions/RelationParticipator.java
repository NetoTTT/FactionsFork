/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 */
package com.massivecraft.factions;

import com.massivecraft.factions.Rel;
import org.bukkit.ChatColor;

public interface RelationParticipator {
    public String describeTo(RelationParticipator var1);

    public String describeTo(RelationParticipator var1, boolean var2);

    public Rel getRelationTo(RelationParticipator var1);

    public Rel getRelationTo(RelationParticipator var1, boolean var2);

    public ChatColor getColorTo(RelationParticipator var1);
}

