/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.Engine
 *  com.massivecraft.massivecore.ps.PS
 *  com.massivecraft.massivecore.util.MUtil
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Cancellable
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.block.BlockPistonExtendEvent
 *  org.bukkit.event.block.BlockPistonRetractEvent
 *  org.bukkit.event.player.PlayerInteractAtEntityEvent
 *  org.bukkit.event.player.PlayerInteractEntityEvent
 */
package com.massivecraft.factions.integration.spigot;

import com.massivecraft.factions.engine.EnginePermBuild;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.MUtil;
import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class EngineSpigot
extends Engine {
    private static EngineSpigot i = new EngineSpigot();

    public static EngineSpigot get() {
        return i;
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        if (EngineSpigot.isOffHand((PlayerInteractEntityEvent)event)) {
            return;
        }
        Player player = event.getPlayer();
        if (MUtil.isntPlayer((Object)player)) {
            return;
        }
        Entity entity = event.getRightClicked();
        boolean verboose = true;
        if (entity.getType() != EntityType.ARMOR_STAND) {
            return;
        }
        EnginePermBuild.useEntity((Entity)player, entity, true, (Cancellable)event);
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void blockBuild(BlockPistonExtendEvent event) {
        if (!MConf.get().handlePistonProtectionThroughDenyBuild) {
            return;
        }
        Faction pistonFaction = BoardColl.get().getFactionAt(PS.valueOf((Block)event.getBlock()));
        @SuppressWarnings("unchecked") List<Block> blocks = event.getBlocks();
        for (Block block : blocks) {
            Block targetBlock = block.getRelative(event.getDirection());
            Faction targetFaction = BoardColl.get().getFactionAt(PS.valueOf((Block)targetBlock));
            if (targetFaction == pistonFaction || MPerm.getPermBuild().has(pistonFaction, targetFaction)) continue;
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void blockBuild(BlockPistonRetractEvent event) {
        if (!MConf.get().handlePistonProtectionThroughDenyBuild) {
            return;
        }
        Faction pistonFaction = BoardColl.get().getFactionAt(PS.valueOf((Block)event.getBlock()));
        @SuppressWarnings("unchecked") List<Block> blocks = event.getBlocks();
        for (Block block : blocks) {
            if (block.isEmpty() || block.isLiquid()) {
                return;
            }
            Faction targetFaction = BoardColl.get().getFactionAt(PS.valueOf((Block)block));
            if (targetFaction == pistonFaction || MPerm.getPermBuild().has(pistonFaction, targetFaction)) continue;
            event.setCancelled(true);
            return;
        }
    }
}

