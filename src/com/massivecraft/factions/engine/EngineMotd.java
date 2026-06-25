/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.Engine
 *  com.massivecraft.massivecore.mixin.MixinActual
 *  com.massivecraft.massivecore.mixin.MixinMessage
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.player.PlayerJoinEvent
 */
package com.massivecraft.factions.engine;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.mixin.MixinActual;
import com.massivecraft.massivecore.mixin.MixinMessage;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

public class EngineMotd
extends Engine {
    private static EngineMotd i = new EngineMotd();

    public static EngineMotd get() {
        return i;
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void motdMonitor(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        MPlayer mplayer = MPlayer.get(player);
        Faction faction = mplayer.getFaction();
        if (!faction.hasMotd()) {
            return;
        }
        if (!MixinActual.get().isActualJoin(event)) {
            return;
        }
        List<Object> messages = faction.getMotdMessages();
        MixinMessage.get().messageOne((Object)player, messages);
    }
}

