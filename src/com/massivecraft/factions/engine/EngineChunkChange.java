/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.Engine
 *  com.massivecraft.massivecore.collections.MassiveList
 *  com.massivecraft.massivecore.collections.MassiveSet
 *  com.massivecraft.massivecore.mixin.MixinWorld
 *  com.massivecraft.massivecore.ps.PS
 *  com.massivecraft.massivecore.util.Txt
 *  org.bukkit.ChatColor
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 */
package com.massivecraft.factions.engine;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsChunksChange;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.collections.MassiveSet;
import com.massivecraft.massivecore.mixin.MixinWorld;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.Txt;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class EngineChunkChange
extends Engine {
    private static EngineChunkChange i = new EngineChunkChange();

    public static EngineChunkChange get() {
        return i;
    }

    @EventHandler(priority=EventPriority.LOW, ignoreCancelled=true)
    public void onChunksChange(EventFactionsChunksChange event) {
        try {
            this.onChunksChangeInner(event);
        }
        catch (Throwable throwable) {
            event.setCancelled(true);
            throwable.printStackTrace();
        }
    }

    public void onChunksChangeInner(EventFactionsChunksChange event) {
        MPlayer mplayer = event.getMPlayer();
        if (mplayer.isOverriding()) {
            return;
        }
        Map<Faction, Set<PS>> currentFactionChunks = event.getOldFactionChunks();
        Set<Faction> currentFactions = currentFactionChunks.keySet();
        boolean currentFactionsContainsAtLeastOneNormal = false;
        for (Faction currentFaction : currentFactions) {
            if (!currentFaction.isNormal()) continue;
            currentFactionsContainsAtLeastOneNormal = true;
            break;
        }
        Set<PS> chunks = event.getChunks();
        Faction newFaction = event.getNewFaction();
        if (newFaction.isNormal()) {
            for (PS chunk : chunks) {
                String worldId = chunk.getWorld();
                if (MConf.get().worldsClaimingEnabled.contains((Object)worldId)) continue;
                String worldName = MixinWorld.get().getWorldDisplayName(worldId);
                mplayer.msg("\u00a7cA compra de territ\u00f3rios esta desabilitada neste mundo.", new Object[]{worldName});
                event.setCancelled(true);
                return;
            }
            if (!MPerm.getPermTerritory().has(mplayer, newFaction, true)) {
                event.setCancelled(true);
                return;
            }
            if (newFaction.getMPlayers().size() < MConf.get().claimsRequireMinFactionMembers) {
                mplayer.msg("\u00a7cA sua fac\u00e7\u00e3o precisa ter no minimo %s membro para poder conquistar territ\u00f3rios.", new Object[]{MConf.get().claimsRequireMinFactionMembers});
                event.setCancelled(true);
                return;
            }
            int claimedLandCount = newFaction.getLandCount();
            if (!newFaction.getFlag(MFlag.getFlagInfpower())) {
                if (MConf.get().claimedLandsMax != 0 && claimedLandCount + chunks.size() > MConf.get().claimedLandsMax) {
                    mplayer.msg("\u00a7cLimite m\u00e1ximo de terras atingido (" + MConf.get().claimedLandsMax + "\u00a7c)! Voc\u00ea n\u00e3o pode mais conquistar territ\u00f3rios.");
                    event.setCancelled(true);
                    return;
                }
                if (MConf.get().claimedWorldsMax >= 0) {
                    Set<String> oldWorlds = newFaction.getClaimedWorlds();
                    Set newWorlds = PS.getDistinctWorlds(chunks);
                    MassiveSet worlds = new MassiveSet();
                    worlds.addAll(oldWorlds);
                    worlds.addAll(newWorlds);
                    if (!oldWorlds.containsAll(newWorlds) && worlds.size() > MConf.get().claimedWorldsMax) {
                        MassiveList worldNames = new MassiveList();
                        Iterator iterator = oldWorlds.iterator();
                        while (iterator.hasNext()) {
                            String world = (String)iterator.next();
                            worldNames.add(MixinWorld.get().getWorldDisplayName(world));
                        }
                        String worldsMax = MConf.get().claimedWorldsMax == 1 ? "world" : "worlds";
                        String worldsAlready = oldWorlds.size() == 1 ? "world" : "worlds";
                        mplayer.msg("\u00a7cVoc\u00ea s\u00f3 pode conquistar terras em \u00a7c%d\u00a7c %s diferrentes.", new Object[]{MConf.get().claimedWorldsMax, worldsMax});
                        mplayer.msg("%s\u00a7e j\u00e1 est\u00e1 presente em \u00a7d%d\u00a7e %s:", new Object[]{newFaction.describeTo(mplayer), oldWorlds.size(), worldsAlready});
                        mplayer.message(Txt.implodeCommaAndDot((Collection)worldNames, (String)ChatColor.YELLOW.toString()));
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            if (claimedLandCount + chunks.size() > newFaction.getPowerRounded()) {
                mplayer.msg("\u00a7cA sua fac\u00e7\u00e3o n\u00e3o tem poder suficiente para poder conquistar mais territ\u00f3rios.");
                event.setCancelled(true);
                return;
            }
            if (MConf.get().claimsMustBeConnected && newFaction.getLandCountInWorld(chunks.iterator().next().getWorld()) > 0 && !BoardColl.get().isAnyConnectedPs(chunks, newFaction) && (!MConf.get().claimsCanBeUnconnectedIfOwnedByOtherFaction || currentFactionsContainsAtLeastOneNormal)) {
                if (MConf.get().claimsCanBeUnconnectedIfOwnedByOtherFaction) {
                    mplayer.msg("\u00a7cVoc\u00ea s\u00f3 poder conquistar terras que estejam conectadas \u00e0s suas ou que sejam pertencentes a outras fac\u00e7\u00f5es.");
                } else {
                    mplayer.msg("\u00a7cVoc\u00ea s\u00f3 poder conquistar terras que estejam conectadas \u00e0s suas.");
                }
                event.setCancelled(true);
                return;
            }
        }
        for (Map.Entry<Faction, Set<PS>> entry : currentFactionChunks.entrySet()) {
            Faction oldFaction = entry.getKey();
            Set<PS> oldChunks = entry.getValue();
            if (oldFaction.isNone() || MPerm.getPermTerritory().has(mplayer, oldFaction, false)) continue;
            if (oldFaction.getRelationTo(newFaction).isAtLeast(Rel.TRUCE)) {
                mplayer.msg("\u00a7cVoc\u00ea n\u00e3o pode conquistar esta terra devido a sua rela\u00e7\u00e3o com o atual dono do territ\u00f3rio. ");
                event.setCancelled(true);
                return;
            }
            if (oldFaction.getPowerRounded() > oldFaction.getLandCount() - oldChunks.size()) {
                mplayer.msg("\u00a7eA fac\u00e7\u00e3o \u00a7e%s\u00a7e \u00e9 dona deste territ\u00f3rio e \u00e9 forte o bastante para mant\u00ea-lo.", new Object[]{oldFaction.getName(mplayer)});
                event.setCancelled(true);
                return;
            }
            if (BoardColl.get().isAnyBorderPs(chunks)) continue;
            mplayer.msg("\u00a7cVoc\u00ea deve come\u00e7ar a conquistar as terras pelas bordas n\u00e3o pelo meio.");
            event.setCancelled(true);
            return;
        }
        Set<PS> nearbyChunks = BoardColl.getNearbyChunks(chunks, MConf.get().claimMinimumChunksDistanceToOthers);
        nearbyChunks.removeAll(chunks);
        Set<Faction> nearbyFactions = BoardColl.getDistinctFactions(nearbyChunks);
        nearbyFactions.remove(FactionColl.get().getNone());
        nearbyFactions.remove(newFaction);
        MPerm claimnear = MPerm.getPermClaimnear();
        for (Map.Entry<Faction, Set<PS>> entry : currentFactionChunks.entrySet()) {
            Faction oldFaction = entry.getKey();
            for (Faction nearbyFaction : nearbyFactions) {
                if (!oldFaction.isNone()) {
                    return;
                }
                if (claimnear.has(newFaction, nearbyFaction)) continue;
                mplayer.message(claimnear.createDeniedMessage(mplayer, nearbyFaction));
                event.setCancelled(true);
                return;
            }
        }
    }
}

