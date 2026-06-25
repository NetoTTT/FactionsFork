/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.Engine
 *  com.massivecraft.massivecore.ps.PS
 *  com.massivecraft.massivecore.util.MUtil
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Cancellable
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.BlockDamageEvent
 *  org.bukkit.event.block.BlockPistonExtendEvent
 *  org.bukkit.event.block.BlockPistonRetractEvent
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.block.SignChangeEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.hanging.HangingBreakByEntityEvent
 *  org.bukkit.event.hanging.HangingPlaceEvent
 *  org.bukkit.event.player.PlayerBucketEmptyEvent
 *  org.bukkit.event.player.PlayerBucketFillEvent
 *  org.bukkit.event.player.PlayerInteractEntityEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 */
package com.massivecraft.factions.engine;

import com.massivecraft.factions.engine.ProtectCase;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.integration.spigot.IntegrationSpigot;
import com.massivecraft.factions.util.EnumerationUtil;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.MUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class EnginePermBuild
extends Engine {
    private static EnginePermBuild i = new EnginePermBuild();

    public static EnginePermBuild get() {
        return i;
    }

    public static Boolean isProtected(ProtectCase protectCase, boolean verboose, MPlayer mplayer, PS ps, Object object) {
        if (mplayer == null) {
            return null;
        }
        if (protectCase == null) {
            return null;
        }
        if (mplayer.isOverriding()) {
            return false;
        }
        MPerm perm = protectCase.getPerm(object);
        if (perm == null) {
            return null;
        }
        if (protectCase != ProtectCase.BUILD) {
            return !perm.has(mplayer, ps, verboose);
        }
        return !perm.has(mplayer, ps, verboose);
    }

    public static Boolean protect(ProtectCase protectCase, boolean verboose, Object senderObject, PS ps, Object object, Cancellable cancellable) {
        Boolean ret = EnginePermBuild.isProtected(protectCase, verboose, MPlayer.get(senderObject), ps, object);
        if (Boolean.TRUE.equals(ret) && cancellable != null) {
            cancellable.setCancelled(true);
        }
        return ret;
    }

    public static Boolean build(Entity entity, Block block, Event event) {
        if (!(event instanceof Cancellable)) {
            return true;
        }
        boolean verboose = !EnginePermBuild.isFake((Event)event);
        return EnginePermBuild.protect(ProtectCase.BUILD, verboose, entity, PS.valueOf((Block)block), block, (Cancellable)event);
    }

    public static Boolean useItem(Entity entity, Block block, Material material, Cancellable cancellable) {
        return EnginePermBuild.protect(ProtectCase.USE_ITEM, true, entity, PS.valueOf((Block)block), material, cancellable);
    }

    public static Boolean useEntity(Entity player, Entity entity, boolean verboose, Cancellable cancellable) {
        return EnginePermBuild.protect(ProtectCase.USE_ENTITY, verboose, player, PS.valueOf((Entity)entity), entity, cancellable);
    }

    public static Boolean useBlock(Player player, Block block, boolean verboose, Cancellable cancellable) {
        return EnginePermBuild.protect(ProtectCase.USE_BLOCK, verboose, player, PS.valueOf((Block)block), block.getType(), cancellable);
    }

    public static boolean canPlayerBuildAt(Object senderObject, PS ps, boolean verboose) {
        MPlayer mplayer = MPlayer.get(senderObject);
        if (mplayer == null) {
            return false;
        }
        Boolean ret = EnginePermBuild.isProtected(ProtectCase.BUILD, verboose, mplayer, ps, null);
        return !Boolean.TRUE.equals(ret);
    }

    @EventHandler(priority=EventPriority.NORMAL)
    public void build(BlockPlaceEvent event) {
        EnginePermBuild.build((Entity)event.getPlayer(), event.getBlock(), (Event)event);
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void build(BlockBreakEvent event) {
        EnginePermBuild.build((Entity)event.getPlayer(), event.getBlock(), (Event)event);
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void build(BlockDamageEvent event) {
        EnginePermBuild.build((Entity)event.getPlayer(), event.getBlock(), (Event)event);
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void build(SignChangeEvent event) {
        EnginePermBuild.build((Entity)event.getPlayer(), event.getBlock(), (Event)event);
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void build(HangingPlaceEvent event) {
        EnginePermBuild.build((Entity)event.getPlayer(), event.getBlock(), (Event)event);
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void build(HangingBreakByEntityEvent event) {
        EnginePermBuild.build(event.getRemover(), event.getEntity().getLocation().getBlock(), (Event)event);
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void useBlockItem(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.PHYSICAL) {
            return;
        }
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();
        if (block == null) {
            return;
        }
        Boolean ret = EnginePermBuild.useBlock(player, block, true, (Cancellable)event);
        if (Boolean.TRUE.equals(ret)) {
            return;
        }
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        EnginePermBuild.useItem((Entity)player, block, event.getMaterial(), (Cancellable)event);
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void useItem(PlayerBucketEmptyEvent event) {
        EnginePermBuild.useItem((Entity)event.getPlayer(), event.getBlockClicked().getRelative(event.getBlockFace()), event.getBucket(), (Cancellable)event);
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void useItem(PlayerBucketFillEvent event) {
        EnginePermBuild.useItem((Entity)event.getPlayer(), event.getBlockClicked(), event.getBucket(), (Cancellable)event);
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void useEntity(PlayerInteractEntityEvent event) {
        if (EnginePermBuild.isOffHand((PlayerInteractEntityEvent)event)) {
            return;
        }
        EnginePermBuild.useEntity((Entity)event.getPlayer(), event.getRightClicked(), true, (Cancellable)event);
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void buildEntity(EntityDamageByEntityEvent event) {
        Entity damager = MUtil.getLiableDamager((EntityDamageEvent)event);
        if (MUtil.isntPlayer((Object)damager)) {
            return;
        }
        Player player = (Player)damager;
        Entity entity = event.getEntity();
        if (entity == null || !EnumerationUtil.isEntityTypeEditOnDamage(entity.getType())) {
            return;
        }
        EnginePermBuild.build((Entity)player, entity.getLocation().getBlock(), (Event)event);
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void buildPiston(BlockPistonExtendEvent event) {
        if (IntegrationSpigot.get().isIntegrationActive() || !MConf.get().handlePistonProtectionThroughDenyBuild) {
            return;
        }
        Block block = event.getBlock();
        Block targetBlock = block.getRelative(event.getDirection(), event.getLength() + 1);
        Faction pistonFaction = BoardColl.get().getFactionAt(PS.valueOf((Block)block));
        Faction targetFaction = BoardColl.get().getFactionAt(PS.valueOf((Block)targetBlock));
        if (targetFaction == pistonFaction) {
            return;
        }
        if (!targetBlock.isEmpty() && !targetBlock.isLiquid()) {
            return;
        }
        if (MPerm.getPermBuild().has(pistonFaction, targetFaction)) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void buildPiston(BlockPistonRetractEvent event) {
        if (IntegrationSpigot.get().isIntegrationActive() || !MConf.get().handlePistonProtectionThroughDenyBuild) {
            return;
        }
        if (!event.isSticky()) {
            return;
        }
        Block retractBlock = event.getRetractLocation().getBlock();
        if (retractBlock.isEmpty() || retractBlock.isLiquid()) {
            return;
        }
        PS retractPs = PS.valueOf((Block)retractBlock);
        Faction pistonFaction = BoardColl.get().getFactionAt(PS.valueOf((Block)event.getBlock()));
        Faction targetFaction = BoardColl.get().getFactionAt(retractPs);
        if (targetFaction == pistonFaction) {
            return;
        }
        if (MPerm.getPermBuild().has(pistonFaction, targetFaction)) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler(priority=EventPriority.NORMAL)
    public void buildFire(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_BLOCK || event.getClickedBlock() == null) {
            return;
        }
        Block potentialBlock = event.getClickedBlock().getRelative(BlockFace.UP, 1);
        if (potentialBlock == null) {
            return;
        }
        Material blockType = potentialBlock.getType();
        if (blockType != Material.FIRE) {
            return;
        }
        if (!Boolean.FALSE.equals(EnginePermBuild.build((Entity)event.getPlayer(), potentialBlock, (Event)event))) {
            return;
        }
        event.getPlayer().sendBlockChange(potentialBlock.getLocation(), blockType, potentialBlock.getState().getRawData());
    }
}

