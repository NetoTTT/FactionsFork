/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.Engine
 *  com.massivecraft.massivecore.ps.PS
 *  com.massivecraft.massivecore.util.MUtil
 *  com.massivecraft.massivecore.util.Txt
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 */
package com.massivecraft.factions.engine;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.Txt;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class EngineDenyCommands
extends Engine {
    private static EngineDenyCommands i = new EngineDenyCommands();

    public static EngineDenyCommands get() {
        return i;
    }

    @EventHandler(priority=EventPriority.LOW, ignoreCancelled=true)
    public void denyCommands(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        MPlayer mplayer = MPlayer.get(player);
        if (mplayer.isOverriding()) {
            return;
        }
        PS ps = PS.valueOf((Location)player.getLocation()).getChunk(true);
        Faction factionAtPs = BoardColl.get().getFactionAt(ps);
        Rel factionAtRel = null;
        if (factionAtPs != null && !factionAtPs.isNone()) {
            factionAtRel = factionAtPs.getRelationTo(mplayer);
        }
        if (factionAtRel == null) {
            return;
        }
        List<String> deniedCommands = MConf.get().denyCommandsTerritoryRelation.get((Object)factionAtRel);
        if (deniedCommands == null) {
            return;
        }
        String command = event.getMessage();
        command = Txt.removeLeadingCommandDust((String)command);
        command = command.toLowerCase();
        if (!MUtil.containsCommand((String)(command = command.trim()), deniedCommands)) {
            return;
        }
        mplayer.msg("\u00a7cVoc\u00ea n\u00e3o pode usar '\u00a7c%s\u00a7c' em territ\u00f3rios inimigos.", new Object[]{command});
        event.setCancelled(true);
    }
}

