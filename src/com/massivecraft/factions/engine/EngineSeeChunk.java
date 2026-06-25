/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.Engine
 *  com.massivecraft.massivecore.event.EventMassiveCorePlayerLeave
 *  com.massivecraft.massivecore.particleeffect.ParticleEffect
 *  com.massivecraft.massivecore.ps.PS
 *  com.massivecraft.massivecore.util.MUtil
 *  com.massivecraft.massivecore.util.PeriodUtil
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.player.PlayerChangedWorldEvent
 */
package com.massivecraft.factions.engine;

import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.event.EventMassiveCorePlayerLeave;
import com.massivecraft.massivecore.particleeffect.ParticleEffect;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.PeriodUtil;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class EngineSeeChunk
extends Engine {
    private static EngineSeeChunk i = new EngineSeeChunk();

    public static EngineSeeChunk get() {
        return i;
    }

    public EngineSeeChunk() {
        this.setPeriod(1L);
    }

    public static void leaveAndWorldChangeRemoval(Player player) {
        if (MUtil.isntPlayer((Object)player)) {
            return;
        }
        MPlayer mplayer = MPlayer.get(player);
        mplayer.setSeeingChunk(false);
    }

    @EventHandler(priority=EventPriority.NORMAL)
    public void leaveAndWorldChangeRemoval(EventMassiveCorePlayerLeave event) {
        EngineSeeChunk.leaveAndWorldChangeRemoval(event.getPlayer());
    }

    @EventHandler(priority=EventPriority.NORMAL)
    public void leaveAndWorldChangeRemoval(PlayerChangedWorldEvent event) {
        EngineSeeChunk.leaveAndWorldChangeRemoval(event.getPlayer());
    }

    public void run() {
        long length = MConf.get().seeChunkPeriodMillis;
        long now = System.currentTimeMillis();
        if (!PeriodUtil.isNewPeriod((Object)((Object)this), (long)length, (long)now)) {
            return;
        }
        long period = PeriodUtil.getPeriod((long)length, (long)now);
        int steps = MConf.get().seeChunkSteps;
        int step = (int)(period % (long)steps);
        float offsetX = 0.0f;
        float offsetY = MConf.get().seeChunkParticleOffsetY;
        float offsetZ = 0.0f;
        float speed = 0.0f;
        int amount = MConf.get().seeChunkParticleAmount;
        for (Player player : MUtil.getOnlinePlayers()) {
            MPlayer mplayer;
            if (player.isDead() || !(mplayer = MPlayer.get(player)).isSeeingChunk()) continue;
            List<Location> locations = EngineSeeChunk.getLocations(player, steps, step);
            for (Location location : locations) {
                ParticleEffect.EXPLOSION_NORMAL.display(location, 0.0f, offsetY, 0.0f, 0.0f, amount, new Player[]{player});
            }
        }
    }

    public static List<Location> getLocations(Player player, int steps, int step) {
        int skipEvery;
        if (player == null) {
            throw new NullPointerException("player");
        }
        if (steps < 1) {
            throw new InvalidParameterException("steps devem ser maiores do que 0");
        }
        if (step < 0) {
            throw new InvalidParameterException("step deve pelo menos ser 0");
        }
        if (step >= steps) {
            throw new InvalidParameterException("step deve ser inferior a steps");
        }
        ArrayList<Location> ret = new ArrayList<Location>();
        Location location = player.getLocation();
        World world = location.getWorld();
        PS chunk = PS.valueOf((Location)location).getChunk(true);
        int xmin = chunk.getChunkX() * 16;
        int xmax = xmin + 15;
        double y = (float)location.getBlockY() + MConf.get().seeChunkParticleDeltaY;
        int zmin = chunk.getChunkZ() * 16;
        int zmax = zmin + 15;
        int keepEvery = MConf.get().seeChunkKeepEvery;
        if (keepEvery <= 0) {
            keepEvery = Integer.MAX_VALUE;
        }
        if ((skipEvery = MConf.get().seeChunkSkipEvery) <= 0) {
            skipEvery = Integer.MAX_VALUE;
        }
        int x = xmin;
        int z = zmin;
        int i = 0;
        while (x + 1 <= xmax) {
            ++x;
            if (++i % steps != step || i % keepEvery != 0 || i % skipEvery == 0) continue;
            ret.add(new Location(world, (double)x + 0.5, y + 0.5, (double)z + 0.5));
        }
        while (z + 1 <= zmax) {
            ++z;
            if (++i % steps != step || i % keepEvery != 0 || i % skipEvery == 0) continue;
            ret.add(new Location(world, (double)x + 0.5, y + 0.5, (double)z + 0.5));
        }
        while (x - 1 >= xmin) {
            --x;
            if (++i % steps != step || i % keepEvery != 0 || i % skipEvery == 0) continue;
            ret.add(new Location(world, (double)x + 0.5, y + 0.5, (double)z + 0.5));
        }
        while (z - 1 >= zmin) {
            --z;
            if (++i % steps != step || i % keepEvery != 0 || i % skipEvery == 0) continue;
            ret.add(new Location(world, (double)x + 0.5, y + 0.5, (double)z + 0.5));
        }
        return ret;
    }
}

