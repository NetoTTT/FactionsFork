/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.collections.MassiveSet
 */
package com.massivecraft.factions;

import com.massivecraft.factions.AccessStatus;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.collections.MassiveSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class TerritoryAccess {
    private final String hostFactionId;
    private final boolean hostFactionAllowed;
    private final Set<String> factionIds;
    private final Set<String> playerIds;

    public String getHostFactionId() {
        return this.hostFactionId;
    }

    public boolean isHostFactionAllowed() {
        return this.hostFactionAllowed;
    }

    public Set<String> getFactionIds() {
        return this.factionIds;
    }

    public Set<String> getPlayerIds() {
        return this.playerIds;
    }

    public TerritoryAccess withHostFactionId(String hostFactionId) {
        return TerritoryAccess.valueOf(hostFactionId, this.hostFactionAllowed, this.factionIds, this.playerIds);
    }

    public TerritoryAccess withHostFactionAllowed(Boolean hostFactionAllowed) {
        return TerritoryAccess.valueOf(this.hostFactionId, hostFactionAllowed, this.factionIds, this.playerIds);
    }

    public TerritoryAccess withFactionIds(Collection<String> factionIds) {
        return TerritoryAccess.valueOf(this.hostFactionId, this.hostFactionAllowed, factionIds, this.playerIds);
    }

    public TerritoryAccess withPlayerIds(Collection<String> playerIds) {
        return TerritoryAccess.valueOf(this.hostFactionId, this.hostFactionAllowed, this.factionIds, playerIds);
    }

    public TerritoryAccess withFactionId(String factionId, boolean with) {
        if (this.getHostFactionId().equals(factionId)) {
            return TerritoryAccess.valueOf(this.hostFactionId, with, this.factionIds, this.playerIds);
        }
        MassiveSet factionIds = new MassiveSet(this.getFactionIds());
        if (with) {
            factionIds.add(factionId);
        } else {
            factionIds.remove(factionId);
        }
        return TerritoryAccess.valueOf(this.hostFactionId, this.hostFactionAllowed, (Collection<String>)factionIds, this.playerIds);
    }

    public TerritoryAccess withPlayerId(String playerId, boolean with) {
        playerId = playerId.toLowerCase();
        MassiveSet playerIds = new MassiveSet(this.getPlayerIds());
        if (with) {
            playerIds.add(playerId);
        } else {
            playerIds.remove(playerId);
        }
        return TerritoryAccess.valueOf(this.hostFactionId, this.hostFactionAllowed, this.factionIds, (Collection<String>)playerIds);
    }

    public Faction getHostFaction() {
        return Faction.get(this.getHostFactionId());
    }

    public Set<MPlayer> getGrantedMPlayers() {
        MassiveSet ret = new MassiveSet();
        for (String playerId : this.getPlayerIds()) {
            ret.add(MPlayer.get(playerId));
        }
        return ret;
    }

    public Set<Faction> getGrantedFactions() {
        MassiveSet ret = new MassiveSet();
        for (String factionId : this.getFactionIds()) {
            Faction faction = Faction.get(factionId);
            if (faction == null) continue;
            ret.add(faction);
        }
        return ret;
    }

    private TerritoryAccess(String hostFactionId, Boolean hostFactionAllowed, Collection<String> factionIds, Collection<String> playerIds) {
        if (hostFactionId == null) {
            throw new IllegalArgumentException("O ID da faccao que esta protegendo este territorio eh nulo!");
        }
        this.hostFactionId = hostFactionId;
        MassiveSet factionIdsInner = new MassiveSet();
        if (factionIds != null) {
            factionIdsInner.addAll(factionIds);
            if (factionIdsInner.remove(hostFactionId)) {
                hostFactionAllowed = true;
            }
        }
        this.factionIds = Collections.unmodifiableSet(factionIdsInner);
        MassiveSet playerIdsInner = new MassiveSet();
        if (playerIds != null) {
            for (String playerId : playerIds) {
                playerIdsInner.add(playerId.toLowerCase());
            }
        }
        this.playerIds = Collections.unmodifiableSet(playerIdsInner);
        this.hostFactionAllowed = hostFactionAllowed == null || hostFactionAllowed != false;
    }

    public static TerritoryAccess valueOf(String hostFactionId, Boolean hostFactionAllowed, Collection<String> factionIds, Collection<String> playerIds) {
        return new TerritoryAccess(hostFactionId, hostFactionAllowed, factionIds, playerIds);
    }

    public static TerritoryAccess valueOf(String hostFactionId) {
        return TerritoryAccess.valueOf(hostFactionId, null, null, null);
    }

    public boolean isFactionGranted(Faction faction) {
        String factionId = faction.getId();
        if (this.getHostFactionId().equals(factionId)) {
            return this.isHostFactionAllowed();
        }
        return this.getFactionIds().contains(factionId);
    }

    public boolean isMPlayerGranted(MPlayer mplayer) {
        String mplayerId = mplayer.getId();
        return this.getPlayerIds().contains(mplayerId);
    }

    public boolean isDefault() {
        return this.isHostFactionAllowed() && this.getFactionIds().isEmpty() && this.getPlayerIds().isEmpty();
    }

    public AccessStatus getTerritoryAccess(MPlayer mplayer) {
        if (this.isMPlayerGranted(mplayer)) {
            return AccessStatus.ELEVATED;
        }
        String factionId = mplayer.getFaction().getId();
        if (this.getFactionIds().contains(factionId)) {
            return AccessStatus.ELEVATED;
        }
        if (this.getHostFactionId().equals(factionId) && !this.isHostFactionAllowed()) {
            return AccessStatus.DECREASED;
        }
        return AccessStatus.STANDARD;
    }

    @Deprecated
    public Boolean hasTerritoryAccess(MPlayer mplayer) {
        return this.getTerritoryAccess(mplayer).hasAccess();
    }
}

