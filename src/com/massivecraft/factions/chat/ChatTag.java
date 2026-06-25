/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.collections.MassiveMap
 *  org.bukkit.command.CommandSender
 */
package com.massivecraft.factions.chat;

import com.massivecraft.factions.chat.ChatActive;
import com.massivecraft.massivecore.collections.MassiveMap;
import java.util.Map;
import org.bukkit.command.CommandSender;

public abstract class ChatTag
extends ChatActive {
    private static final Map<String, ChatTag> idToTag = new MassiveMap();

    public static ChatTag getTag(String tagId) {
        return idToTag.get(tagId);
    }

    public ChatTag(String id) {
        super(id);
    }

    public boolean isActive() {
        return idToTag.containsKey(this.getId());
    }

    public void setActive(boolean active) {
        if (active) {
            idToTag.put(this.getId(), this);
        } else {
            idToTag.remove(this.getId());
        }
    }

    public abstract String getReplacement(CommandSender var1, CommandSender var2);
}

