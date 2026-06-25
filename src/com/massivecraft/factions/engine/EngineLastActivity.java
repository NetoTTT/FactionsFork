/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.Engine
 *  com.massivecraft.massivecore.event.EventMassiveCorePlayerLeave
 *  com.massivecraft.massivecore.util.MUtil
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandSender
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.plugin.Plugin
 */
package com.massivecraft.factions.engine;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.event.EventMassiveCorePlayerLeave;
import com.massivecraft.massivecore.util.MUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class EngineLastActivity
extends Engine {
    private static EngineLastActivity i = new EngineLastActivity();

    public static EngineLastActivity get() {
        return i;
    }

    public static void updateLastActivity(CommandSender sender) {
        if (sender == null) {
            throw new RuntimeException("sender");
        }
        if (MUtil.isntSender((Object)sender)) {
            return;
        }
        MPlayer mplayer = MPlayer.get(sender);
        mplayer.setLastActivityMillis();
    }

    public static void updateLastActivitySoon(final CommandSender sender) {
        if (sender == null) {
            throw new RuntimeException("sender");
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)Factions.get(), new Runnable(){

            @Override
            public void run() {
                EngineLastActivity.updateLastActivity(sender);
            }
        });
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void updateLastActivity(PlayerJoinEvent event) {
        EngineLastActivity.updateLastActivitySoon((CommandSender)event.getPlayer());
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void updateLastActivity(EventMassiveCorePlayerLeave event) {
        EngineLastActivity.updateLastActivity((CommandSender)event.getPlayer());
    }
}

