/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.Engine
 *  com.massivecraft.massivecore.ps.PS
 *  com.massivecraft.massivecore.util.MUtil
 *  org.bukkit.Location
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.ThrownPotion
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.entity.EntityCombustByEntityEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.PotionSplashEvent
 *  org.bukkit.projectiles.ProjectileSource
 */
package com.massivecraft.factions.engine;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.engine.EngineFly;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.MUtil;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.projectiles.ProjectileSource;

public class EngineCanCombatHappen
extends Engine {
    private static EngineCanCombatHappen i = new EngineCanCombatHappen();

    public static EngineCanCombatHappen get() {
        return i;
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void canCombatDamageHappen(EntityDamageByEntityEvent event) {
        if (this.canCombatDamageHappen(event, true)) {
            EngineFly.disableFly(event);
            return;
        }
        event.setCancelled(true);
        Entity damager = event.getDamager();
        if (!(damager instanceof Arrow)) {
            return;
        }
        damager.remove();
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void canCombatDamageHappen(EntityCombustByEntityEvent event) {
        EntityDamageByEntityEvent sub = new EntityDamageByEntityEvent(event.getCombuster(), event.getEntity(), EntityDamageEvent.DamageCause.FIRE, 0.0);
        if (this.canCombatDamageHappen(sub, false)) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void canCombatDamageHappen(PotionSplashEvent event) {
        if (!MUtil.isHarmfulPotion((ThrownPotion)event.getPotion())) {
            return;
        }
        ProjectileSource projectileSource = event.getPotion().getShooter();
        if (!(projectileSource instanceof Entity)) {
            return;
        }
        Entity thrower = (Entity)projectileSource;
        for (LivingEntity affectedEntity : event.getAffectedEntities()) {
            EntityDamageByEntityEvent sub = new EntityDamageByEntityEvent(thrower, (Entity)affectedEntity, EntityDamageEvent.DamageCause.CUSTOM, 0.0);
            if (this.canCombatDamageHappen(sub, true)) continue;
            event.setIntensity(affectedEntity, 0.0);
        }
    }

    public boolean canCombatDamageHappen(EntityDamageByEntityEvent event, boolean notify) {
        Entity edefender = event.getEntity();
        if (MUtil.isntPlayer((Object)edefender)) {
            return true;
        }
        Player defender = (Player)edefender;
        MPlayer mdefender = MPlayer.get(edefender);
        Entity eattacker = MUtil.getLiableDamager((EntityDamageEvent)event);
        if (eattacker != null && eattacker.equals(edefender)) {
            return true;
        }
        if (MUtil.isntPlayer((Object)eattacker)) {
            return true;
        }
        Player attacker = (Player)eattacker;
        MPlayer mattacker = MPlayer.get(attacker);
        PS defenderPs = PS.valueOf((Location)defender.getLocation());
        Faction defenderPsFaction = BoardColl.get().getFactionAt(defenderPs);
        if (mattacker != null && mattacker.isOverriding()) {
            return true;
        }
        if (defenderPsFaction.getFlag(MFlag.getFlagFriendlyire())) {
            return true;
        }
        if (!defenderPsFaction.getFlag(MFlag.getFlagPvp())) {
            return false;
        }
        if (!mattacker.hasFaction() || !mdefender.hasFaction()) {
            return true;
        }
        if (mattacker.getFaction().getRelationTo(mdefender.getFaction()).equals((Object)Rel.ALLY)) {
            return false;
        }
        return !mattacker.getFaction().getMPlayers().contains(mdefender);
    }
}

