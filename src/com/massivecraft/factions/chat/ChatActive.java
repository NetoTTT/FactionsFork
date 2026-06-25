/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.Active
 *  com.massivecraft.massivecore.Identified
 *  com.massivecraft.massivecore.MassivePlugin
 */
package com.massivecraft.factions.chat;

import com.massivecraft.massivecore.Active;
import com.massivecraft.massivecore.Identified;
import com.massivecraft.massivecore.MassivePlugin;

public abstract class ChatActive
implements Active,
Identified {
    private MassivePlugin activePlugin;
    private final String id;

    public ChatActive(String id) {
        this.id = id.toLowerCase();
    }

    public String getId() {
        return this.id;
    }

    public MassivePlugin setActivePlugin(MassivePlugin plugin) {
        this.activePlugin = plugin;
        return plugin;
    }

    public MassivePlugin getActivePlugin() {
        return this.activePlugin;
    }

    public void setActive(MassivePlugin plugin) {
        this.setActive(plugin != null);
        this.setActivePlugin(plugin);
    }
}

