/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.command.requirement.Requirement
 *  com.massivecraft.massivecore.command.requirement.RequirementIsPlayer
 *  com.massivecraft.massivecore.mixin.MixinTeleport
 *  com.massivecraft.massivecore.mixin.TeleporterException
 *  com.massivecraft.massivecore.ps.PS
 *  com.massivecraft.massivecore.teleport.Destination
 *  com.massivecraft.massivecore.teleport.DestinationSimple
 *  org.bukkit.Chunk
 *  org.bukkit.Location
 *  org.bukkit.World
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.mixin.MixinTeleport;
import com.massivecraft.massivecore.mixin.TeleporterException;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.teleport.Destination;
import com.massivecraft.massivecore.teleport.DestinationSimple;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

public class CmdFactionsEscapar
extends FactionsCommand {
    public CmdFactionsEscapar() {
        this.addAliases(new String[]{"desprender", "fugir"});
        this.addRequirements(new Requirement[]{RequirementIsPlayer.get()});
        this.setDesc("\u00a76 escapar \u00a78-\u00a77 Teleporta para a zona livre mais pr\u00f3xima.");
    }

    public void perform() throws MassiveException {
        PS center = PS.valueOf((Chunk)this.me.getLocation().getChunk());
        if (CmdFactionsEscapar.isFree(this.msender, center)) {
            this.msg("\u00a7cVoc\u00ea n\u00e3o parece estar preso.");
            return;
        }
        int x1 = MConf.get().bordaXpositivo;
        int x2 = MConf.get().bordaXnegativo;
        int z1 = MConf.get().bordaZpositivo;
        int z2 = MConf.get().bordaZnegativo;
        Location location = CmdFactionsEscapar.getNearestFreeTopLocation(this.msender, center);
        if (location == null) {
            this.msg("\u00a7cN\u00e3o h\u00e1 nenhuma chunk livre pr\u00f3xima de voc\u00ea! Voc\u00ea est\u00e1 realmente preso.");
            return;
        }
        if (location.getBlockX() > x1 || location.getBlockX() < x2 || location.getBlockZ() > z1 || location.getBlockZ() < z2) {
            this.msg("\u00a7cInfelizmente o local de destino estava fora do mundo, portanto o teleporte foi cancelado.");
            return;
        }
        String desc = "\u00a7f" + location.getBlockX() + ".5\u00a77, \u00a7f" + location.getBlockY() + ".0\u00a77, \u00a7f" + location.getBlockZ() + ".5\u00a7e";
        DestinationSimple destination = new DestinationSimple(PS.valueOf((Location)location), desc);
        try {
            MixinTeleport.get().teleport((Object)this.me, (Destination)destination, MConf.get().unstuckSeconds);
        }
        catch (TeleporterException e) {
            this.msg("\u00a7c%s", new Object[]{e.getMessage()});
        }
    }

    public static Location getNearestFreeTopLocation(MPlayer mplayer, PS ps) {
        List<PS> chunks = CmdFactionsEscapar.getChunkSpiral(ps);
        for (PS chunk : chunks) {
            Location ret;
            if (!CmdFactionsEscapar.isFree(mplayer, chunk) || (ret = CmdFactionsEscapar.getTopLocation(chunk)) == null) continue;
            return ret;
        }
        return null;
    }

    public static Location getTopLocation(PS ps) {
        int blockY;
        int blockZ;
        int blockX;
        World world;
        Location ret;
        block3: {
            ret = null;
            world = ps.asBukkitWorld();
            blockX = ps.getBlockX(true);
            blockZ = ps.getBlockZ(true);
            blockY = world.getHighestBlockYAt(blockX, blockZ);
            if (blockY > 1) break block3;
            return null;
        }
        try {
            double locationX = (double)blockX + 0.5;
            double locationY = (double)blockY + 0.5;
            double locationZ = (double)blockZ + 0.5;
            ret = new Location(world, locationX, locationY, locationZ);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static boolean isFree(MPlayer mplayer, PS ps) {
        Faction faction = BoardColl.get().getFactionAt(ps);
        if (faction.isNone()) {
            return true;
        }
        return MPerm.getPermBuild().has(mplayer, ps, false);
    }

    public static List<PS> getChunkSpiral(PS center) {
        ArrayList<PS> ret = new ArrayList<PS>();
        center = center.getChunk(true);
        int centerX = center.getChunkX();
        int centerZ = center.getChunkZ();
        int delta = 1;
        while (delta <= MConf.get().unstuckChunkRadius) {
            int minX = centerX - delta;
            int maxX = centerX + delta;
            int minZ = centerZ - delta;
            int maxZ = centerZ + delta;
            int x = minX;
            while (x <= maxX) {
                ret.add(center.withChunkX(Integer.valueOf(x)).withChunkZ(Integer.valueOf(minZ)));
                ret.add(center.withChunkX(Integer.valueOf(x)).withChunkZ(Integer.valueOf(maxZ)));
                ++x;
            }
            int z = minZ + 1;
            while (z <= maxZ - 1) {
                ret.add(center.withChunkX(Integer.valueOf(minX)).withChunkZ(Integer.valueOf(z)));
                ret.add(center.withChunkX(Integer.valueOf(maxX)).withChunkZ(Integer.valueOf(z)));
                ++z;
            }
            ++delta;
        }
        return ret;
    }
}

