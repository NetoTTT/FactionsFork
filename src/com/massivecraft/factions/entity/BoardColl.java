/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.collections.MassiveMap
 *  com.massivecraft.massivecore.collections.MassiveSet
 *  com.massivecraft.massivecore.mson.Mson
 *  com.massivecraft.massivecore.ps.PS
 *  com.massivecraft.massivecore.store.Coll
 *  com.massivecraft.massivecore.util.MUtil
 *  org.bukkit.entity.Player
 */
package com.massivecraft.factions.entity;

import com.massivecraft.factions.RelationParticipator;
import com.massivecraft.factions.TerritoryAccess;
import com.massivecraft.factions.entity.Board;
import com.massivecraft.factions.entity.BoardInterface;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.massivecore.collections.MassiveMap;
import com.massivecraft.massivecore.collections.MassiveSet;
import com.massivecraft.massivecore.mson.Mson;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.store.Coll;
import com.massivecraft.massivecore.util.MUtil;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.entity.Player;

public class BoardColl
extends Coll<Board>
implements BoardInterface {
    private static BoardColl i = new BoardColl();

    public static BoardColl get() {
        return i;
    }

    private BoardColl() {
        this.setCreative(true);
        this.setLowercasing(true);
    }

    public void onTick() {
        super.onTick();
    }

    public String fixId(Object oid) {
        if (oid == null) {
            return null;
        }
        if (oid instanceof String) {
            return (String)oid;
        }
        if (oid instanceof Board) {
            return ((Board)oid).getId();
        }
        return (String)MUtil.extract(String.class, (String)"worldName", (Object)oid);
    }

    @Override
    public TerritoryAccess getTerritoryAccessAt(PS ps) {
        if (ps == null) {
            return null;
        }
        Board board = (Board)this.get(ps.getWorld());
        if (board == null) {
            return null;
        }
        return board.getTerritoryAccessAt(ps);
    }

    @Override
    public Faction getFactionAt(PS ps) {
        if (ps == null) {
            return null;
        }
        Board board = (Board)this.get(ps.getWorld());
        if (board == null) {
            return null;
        }
        return board.getFactionAt(ps);
    }

    @Override
    public void setTerritoryAccessAt(PS ps, TerritoryAccess territoryAccess) {
        if (ps == null) {
            return;
        }
        Board board = (Board)this.get(ps.getWorld());
        if (board == null) {
            return;
        }
        board.setTerritoryAccessAt(ps, territoryAccess);
    }

    @Override
    public void setFactionAt(PS ps, Faction faction) {
        if (ps == null) {
            return;
        }
        Board board = (Board)this.get(ps.getWorld());
        if (board == null) {
            return;
        }
        board.setFactionAt(ps, faction);
    }

    @Override
    public void removeAt(PS ps) {
        if (ps == null) {
            return;
        }
        Board board = (Board)this.get(ps.getWorld());
        if (board == null) {
            return;
        }
        board.removeAt(ps);
    }

    @Override
    public void removeAll(Faction faction) {
        for (Board board : this.getAll()) {
            board.removeAll(faction);
        }
    }

    @Override
    public Set<PS> getChunks(Faction faction) {
        HashSet<PS> ret = new HashSet<PS>();
        for (Board board : this.getAll()) {
            ret.addAll(board.getChunks(faction));
        }
        return ret;
    }

    @Override
    public Set<PS> getChunks(String factionId) {
        HashSet<PS> ret = new HashSet<PS>();
        for (Board board : this.getAll()) {
            ret.addAll(board.getChunks(factionId));
        }
        return ret;
    }

    @Override
    public List<Mson> getMap(Player p, RelationParticipator observer, PS centerPs, double inDegrees, int width, int height) {
        if (centerPs == null) {
            return null;
        }
        Board board = (Board)this.get(centerPs.getWorld());
        if (board == null) {
            return null;
        }
        return board.getMap(p, observer, centerPs, inDegrees, width, height);
    }

    @Override
    public Map<Faction, Set<PS>> getFactionToChunks() {
        Map<Faction, Set<PS>> ret = null;
        for (Board board : this.getAll()) {
            Map<Faction, Set<PS>> factionToChunks = board.getFactionToChunks();
            if (ret == null) {
                ret = factionToChunks;
                continue;
            }
            for (Map.Entry<Faction, Set<PS>> entry : factionToChunks.entrySet()) {
                Faction faction = entry.getKey();
                @SuppressWarnings("unchecked") Set<PS> chunks = (Set<PS>)ret.get(faction);
                if (chunks == null) {
                    ret.put(faction, entry.getValue());
                    continue;
                }
                chunks.addAll(entry.getValue());
            }
        }
        if (ret == null) {
            ret = new MassiveMap<Faction, Set<PS>>();
        }
        return ret;
    }

    @Override
    public int getCount(Faction faction) {
        return this.getCount(faction.getId());
    }

    @Override
    public int getCount(String factionId) {
        int ret = 0;
        for (Board board : this.getAll()) {
            ret += board.getCount(factionId);
        }
        return ret;
    }

    @Override
    public Map<Faction, Integer> getFactionToCount() {
        Map<Faction, Integer> ret = null;
        for (Board board : this.getAll()) {
            Map<Faction, Integer> factionToCount = board.getFactionToCount();
            if (ret == null) {
                ret = factionToCount;
                continue;
            }
            for (Map.Entry<Faction, Integer> entry : factionToCount.entrySet()) {
                Faction faction = entry.getKey();
                Integer count = ret.get(faction);
                if (count == null) {
                    ret.put(faction, entry.getValue());
                    continue;
                }
                ret.put(faction, count + entry.getValue());
            }
        }
        if (ret == null) {
            ret = new MassiveMap<Faction, Integer>();
        }
        return ret;
    }

    @Override
    public boolean hasClaimed(Faction faction) {
        return this.hasClaimed(faction.getId());
    }

    @Override
    public boolean hasClaimed(String factionId) {
        for (Board board : this.getAll()) {
            if (!board.hasClaimed(factionId)) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean isBorderPs(PS ps) {
        if (ps == null) {
            return false;
        }
        Board board = (Board)this.get(ps.getWorld());
        if (board == null) {
            return false;
        }
        return board.isBorderPs(ps);
    }

    @Override
    public boolean isAnyBorderPs(Set<PS> pss) {
        for (PS ps : pss) {
            if (!this.isBorderPs(ps)) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean isConnectedPs(PS ps, Faction faction) {
        if (ps == null) {
            return false;
        }
        Board board = (Board)this.get(ps.getWorld());
        if (board == null) {
            return false;
        }
        return board.isConnectedPs(ps, faction);
    }

    @Override
    public boolean isAnyConnectedPs(Set<PS> pss, Faction faction) {
        for (PS ps : pss) {
            if (!this.isConnectedPs(ps, faction)) continue;
            return true;
        }
        return false;
    }

    public Set<String> getClaimedWorlds(Faction faction) {
        return this.getClaimedWorlds(faction.getId());
    }

    public Set<String> getClaimedWorlds(String factionId) {
        MassiveSet ret = new MassiveSet();
        for (Board board : this.getAll()) {
            if (!board.hasClaimed(factionId)) continue;
            ret.add(board.getId());
        }
        return ret;
    }

    public static Set<PS> getNearbyChunks(PS psChunk, int distance) {
        if (psChunk == null) {
            throw new NullPointerException("psChunk");
        }
        psChunk = psChunk.getChunk(true);
        LinkedHashSet<PS> ret = new LinkedHashSet<PS>();
        if (distance < 0) {
            return ret;
        }
        int chunkX = psChunk.getChunkX();
        int xmin = chunkX - distance;
        int xmax = chunkX + distance;
        int chunkZ = psChunk.getChunkZ();
        int zmin = chunkZ - distance;
        int zmax = chunkZ + distance;
        int x = xmin;
        while (x <= xmax) {
            PS psChunkX = psChunk.withChunkX(Integer.valueOf(x));
            int z = zmin;
            while (z <= zmax) {
                ret.add(psChunkX.withChunkZ(Integer.valueOf(z)));
                ++z;
            }
            ++x;
        }
        return ret;
    }

    public static Set<PS> getNearbyChunks(Collection<PS> chunks, int distance) {
        if (chunks == null) {
            throw new NullPointerException("chunks");
        }
        LinkedHashSet<PS> ret = new LinkedHashSet<PS>();
        if (distance < 0) {
            return ret;
        }
        for (PS chunk : chunks) {
            ret.addAll(BoardColl.getNearbyChunks(chunk, distance));
        }
        return ret;
    }

    public static Set<Faction> getDistinctFactions(Collection<PS> chunks) {
        if (chunks == null) {
            throw new NullPointerException("chunks");
        }
        LinkedHashSet<Faction> ret = new LinkedHashSet<Faction>();
        for (PS chunk : chunks) {
            Faction faction = BoardColl.get().getFactionAt(chunk);
            if (faction == null) continue;
            ret.add(faction);
        }
        return ret;
    }

    public static Map<PS, Faction> getChunkFaction(Collection<PS> chunks) {
        LinkedHashMap<PS, Faction> ret = new LinkedHashMap<PS, Faction>();
        Faction none = FactionColl.get().getNone();
        for (PS chunk : chunks) {
            chunk = chunk.getChunk(true);
            Faction faction = BoardColl.get().getFactionAt(chunk);
            if (faction == null) {
                faction = none;
            }
            ret.put(chunk, faction);
        }
        return ret;
    }
}

