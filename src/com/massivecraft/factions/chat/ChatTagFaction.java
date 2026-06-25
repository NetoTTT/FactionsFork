/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 */
package com.massivecraft.factions.chat;

import com.massivecraft.factions.chat.ChatTag;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.command.CommandSender;

public class ChatTagFaction
extends ChatTag {
    private static ChatTagFaction i = new ChatTagFaction();

    private ChatTagFaction() {
        super("faction");
    }

    public static ChatTagFaction get() {
        return i;
    }

    @Override
    public String getReplacement(CommandSender sender, CommandSender recipient) {
        MPlayer usender = MPlayer.get(sender);
        Faction faction = usender.getFaction();
        if (faction.isNone()) {
            return "";
        }
        String display = faction.hasTag() ? faction.getTag() : faction.getName();
        String color = FactionColl.get().getRankColor(faction);
        return color + "[" + usender.getRole().getPrefix() + display + color + "] \u00a7f";
    }
}

