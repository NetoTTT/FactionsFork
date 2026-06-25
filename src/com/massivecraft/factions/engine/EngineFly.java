/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.Engine
 *  com.massivecraft.massivecore.ps.PS
 *  com.massivecraft.massivecore.util.MUtil
 *  org.bukkit.Location
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.player.PlayerMoveEvent
 */
package com.massivecraft.factions.engine;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.MUtil;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class EngineFly
extends Engine {
    private static EngineFly i = new EngineFly();

    public static EngineFly get() {
        return i;
    }

    @EventHandler(ignoreCancelled=true)
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (p.isFlying() && !p.hasPermission("factions.voar.bypass")) {
            MPlayer mplayer = MPlayer.get(p);
            if (!mplayer.hasFaction()) {
                p.setFlying(false);
                p.setAllowFlight(false);
                p.sendMessage("\u00a7cVoc\u00ea saiu dos territ\u00f3rios das fac\u00e7\u00e3o portanto seu modo voar foi automaticamente desabilitado.");
            } else {
                PS ps = PS.valueOf((Location)p.getLocation());
                if (!BoardColl.get().getFactionAt(ps).equals(mplayer.getFaction())) {
                    p.setFlying(false);
                    p.setAllowFlight(false);
                    p.sendMessage("\u00a7cVoc\u00ea saiu dos territ\u00f3rios das fac\u00e7\u00e3o portanto seu modo voar foi automaticamente desabilitado.");
                }
            }
        }
    }

    public static void disableFly(EntityDamageByEntityEvent e) {
        Entity edefender = e.getEntity();
        if (MUtil.isntPlayer((Object)edefender)) {
            return;
        }
        Player defender = (Player)edefender;
        if (defender.getAllowFlight()) {
            defender.sendMessage("\u00a7cVoc\u00ea foi atingido por um inimigo portanto seu modo voar foi automaticamente desabilitado.");
            defender.setAllowFlight(false);
        }
    }
}

