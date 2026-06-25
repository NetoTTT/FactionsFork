/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.Engine
 *  com.massivecraft.massivecore.ps.PS
 *  com.massivecraft.massivecore.util.MUtil
 *  com.massivecraft.massivecore.util.PlayerUtil
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.entity.PlayerDeathEvent
 */
package com.massivecraft.factions.engine;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsPowerChange;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.PlayerUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;

public class EnginePower
extends Engine {
    private static EnginePower i = new EnginePower();

    public static EnginePower get() {
        return i;
    }

    @EventHandler(priority=EventPriority.NORMAL)
    public void powerLossOnDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (MUtil.isntPlayer((Object)player)) {
            return;
        }
        if (PlayerUtil.isDuplicateDeathEvent((PlayerDeathEvent)event)) {
            return;
        }
        MPlayer mplayer = MPlayer.get(player);
        Faction faction = BoardColl.get().getFactionAt(PS.valueOf((Location)player.getLocation()));
        if (!faction.getFlag(MFlag.getFlagPowerloss())) {
            mplayer.msg("\u00a7eVoc\u00ea n\u00e3o perdeu poder ao morrer pois a perda de poder est\u00e1 desabilitada naquele territ\u00f3rio.");
            return;
        }
        double newPower = mplayer.getPower() + mplayer.getPowerPerDeath();
        EventFactionsPowerChange powerChangeEvent = new EventFactionsPowerChange(null, mplayer, EventFactionsPowerChange.PowerChangeReason.DEATH, newPower);
        powerChangeEvent.run();
        if (powerChangeEvent.isCancelled()) {
            return;
        }
        newPower = powerChangeEvent.getNewPower();
        mplayer.setPower(newPower);
        mplayer.msg("\u00a7eSeu poder agora \u00e9 \u00a7d%.2f / \u00a7d%.2f", new Object[]{newPower, mplayer.getPowerMax()});
    }
}

