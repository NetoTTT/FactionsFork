/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.Engine
 *  com.massivecraft.massivecore.mixin.MixinTitle
 *  com.massivecraft.massivecore.ps.PS
 *  com.massivecraft.massivecore.util.MUtil
 *  com.massivecraft.massivecore.util.Txt
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.player.PlayerMoveEvent
 */
package com.massivecraft.factions.engine;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.mixin.MixinTitle;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.Txt;
import java.util.Collections;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

public class EngineMoveChunk
extends Engine {
    private static EngineMoveChunk i = new EngineMoveChunk();

    public static EngineMoveChunk get() {
        return i;
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void moveChunkDetect(PlayerMoveEvent event) {
        if (MUtil.isSameChunk((PlayerMoveEvent)event)) {
            return;
        }
        Player player = event.getPlayer();
        MPlayer mplayer = MPlayer.get(player);
        PS chunkFrom = PS.valueOf((Location)event.getFrom()).getChunk(true);
        PS chunkTo = PS.valueOf((Location)event.getTo()).getChunk(true);
        EngineMoveChunk.sendChunkInfo(mplayer, player, chunkFrom, chunkTo);
        EngineMoveChunk.tryAutoClaim(mplayer, chunkTo);
    }

    private static void sendChunkInfo(MPlayer mplayer, Player player, PS chunkFrom, PS chunkTo) {
        EngineMoveChunk.sendAutoMapUpdate(mplayer, player);
        EngineMoveChunk.sendFactionTerritoryInfo(mplayer, player, chunkFrom, chunkTo);
    }

    private static void sendAutoMapUpdate(MPlayer mplayer, Player player) {
        if (!mplayer.isMapAutoUpdating()) {
            return;
        }
        player.performCommand("f mapa");
    }

    private static void sendFactionTerritoryInfo(MPlayer mplayer, Player player, PS chunkFrom, PS chunkTo) {
        Faction factionTo;
        Faction factionFrom = BoardColl.get().getFactionAt(chunkFrom);
        if (factionFrom == (factionTo = BoardColl.get().getFactionAt(chunkTo))) {
            return;
        }
        if (mplayer.isTerritoryInfoTitles()) {
            String titleMain = EngineMoveChunk.parseTerritoryInfo(MConf.get().territoryInfoTitlesMain, mplayer, factionTo);
            String titleSub = EngineMoveChunk.parseTerritoryInfo(MConf.get().territoryInfoTitlesSub, mplayer, factionTo);
            int ticksIn = MConf.get().territoryInfoTitlesTicksIn;
            int ticksStay = MConf.get().territoryInfoTitlesTicksStay;
            int ticksOut = MConf.get().territoryInfoTitleTicksOut;
            MixinTitle.get().sendTitleMessage((Object)player, ticksIn, ticksStay, ticksOut, titleMain, titleSub);
        } else {
            String message = EngineMoveChunk.parseTerritoryInfo(MConf.get().territoryInfoChat, mplayer, factionTo);
            player.sendMessage(message);
        }
    }

    private static String parseTerritoryInfo(String string, MPlayer mplayer, Faction faction) {
        if (string == null) {
            throw new NullPointerException("string");
        }
        if (faction == null) {
            throw new NullPointerException("faction");
        }
        string = Txt.parse((String)string);
        string = string.replace("{name}", faction.getName());
        string = string.replace("{relcolor}", faction.getColorTo(mplayer).toString());
        string = string.replace("{desc}", faction.getDescriptionDesc());
        return string;
    }

    private static void tryAutoClaim(MPlayer mplayer, PS chunkTo) {
        Faction autoClaimFaction = mplayer.getAutoClaimFaction();
        if (autoClaimFaction == null) {
            return;
        }
        mplayer.tryClaim(autoClaimFaction, Collections.singletonList(chunkTo));
    }
}

