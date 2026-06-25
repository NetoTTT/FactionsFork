/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.collections.MassiveMap
 *  com.massivecraft.massivecore.collections.MassiveSet
 *  com.massivecraft.massivecore.mson.Mson
 *  com.massivecraft.massivecore.ps.PS
 *  com.massivecraft.massivecore.store.Entity
 *  com.massivecraft.massivecore.xlib.gson.reflect.TypeToken
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Chunk
 *  org.bukkit.entity.Player
 */
package com.massivecraft.factions.entity;

import com.massivecraft.factions.RelationParticipator;
import com.massivecraft.factions.TerritoryAccess;
import com.massivecraft.factions.engine.EngineSobAtaque;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.BoardInterface;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.util.AsciiCompass;
import com.massivecraft.massivecore.collections.MassiveMap;
import com.massivecraft.massivecore.collections.MassiveSet;
import com.massivecraft.massivecore.mson.Mson;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.store.Entity;
import com.massivecraft.massivecore.xlib.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class Board
extends Entity<Board>
implements BoardInterface {
    public static final transient Type MAP_TYPE = new TypeToken<Map<PS, TerritoryAccess>>(){}.getType();
    private ConcurrentSkipListMap<PS, TerritoryAccess> map;

    public static Board get(Object oid) {
        return (Board)BoardColl.get().get(oid);
    }

    public Board load(Board that) {
        this.map = that.map;
        return this;
    }

    public boolean isDefault() {
        if (this.map == null) {
            return true;
        }
        return this.map.isEmpty();
    }

    public Map<PS, TerritoryAccess> getMap() {
        return Collections.unmodifiableMap(this.map);
    }

    public Map<PS, TerritoryAccess> getMapRaw() {
        return this.map;
    }

    public Board() {
        this.map = new ConcurrentSkipListMap();
    }

    public Board(Map<PS, TerritoryAccess> map) {
        this.map = new ConcurrentSkipListMap<PS, TerritoryAccess>(map);
    }

    @Override
    public TerritoryAccess getTerritoryAccessAt(PS ps) {
        if (ps == null) {
            return null;
        }
        TerritoryAccess ret = this.map.get(ps = ps.getChunkCoords(true));
        if (ret == null || ret.getHostFaction() == null) {
            ret = TerritoryAccess.valueOf("none");
        }
        return ret;
    }

    @Override
    public Faction getFactionAt(PS ps) {
        if (ps == null) {
            return null;
        }
        TerritoryAccess ta = this.getTerritoryAccessAt(ps);
        return ta.getHostFaction();
    }

    @Override
    public void setTerritoryAccessAt(PS ps, TerritoryAccess territoryAccess) {
        ps = ps.getChunkCoords(true);
        if (territoryAccess == null || territoryAccess.getHostFactionId().equals("none") && territoryAccess.isDefault()) {
            this.map.remove(ps);
        } else {
            this.map.put(ps, territoryAccess);
        }
        this.changed();
    }

    @Override
    public void setFactionAt(PS ps, Faction faction) {
        TerritoryAccess territoryAccess = null;
        if (faction != null) {
            territoryAccess = TerritoryAccess.valueOf(faction.getId());
        }
        this.setTerritoryAccessAt(ps, territoryAccess);
    }

    @Override
    public void removeAt(PS ps) {
        this.setTerritoryAccessAt(ps, null);
    }

    @Override
    public void removeAll(Faction faction) {
        String factionId = faction.getId();
        for (Map.Entry<PS, TerritoryAccess> entry : this.map.entrySet()) {
            TerritoryAccess territoryAccess = entry.getValue();
            if (!territoryAccess.getHostFactionId().equals(factionId)) continue;
            PS ps = entry.getKey();
            this.removeAt(ps);
        }
    }

    @Override
    public Set<PS> getChunks(Faction faction) {
        return this.getChunks(faction.getId());
    }

    @Override
    public Set<PS> getChunks(String factionId) {
        HashSet<PS> ret = new HashSet<PS>();
        for (Map.Entry<PS, TerritoryAccess> entry : this.map.entrySet()) {
            TerritoryAccess ta = entry.getValue();
            if (!ta.getHostFactionId().equals(factionId)) continue;
            PS ps = entry.getKey();
            ps = ps.withWorld(this.getId());
            ret.add(ps);
        }
        return ret;
    }

    @Override
    public Map<Faction, Set<PS>> getFactionToChunks() {
        MassiveMap ret = new MassiveMap();
        for (Map.Entry<PS, TerritoryAccess> entry : this.map.entrySet()) {
            TerritoryAccess ta = entry.getValue();
            Faction faction = ta.getHostFaction();
            if (faction == null) continue;
            Set chunks = (Set)ret.get(faction);
            if (chunks == null) {
                chunks = new MassiveSet();
                ret.put(faction, chunks);
            }
            PS chunk = entry.getKey();
            chunk = chunk.withWorld(this.getId());
            chunks.add(chunk);
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
        for (TerritoryAccess ta : this.map.values()) {
            if (!ta.getHostFactionId().equals(factionId)) continue;
            ++ret;
        }
        return ret;
    }

    @Override
    public Map<Faction, Integer> getFactionToCount() {
        MassiveMap ret = new MassiveMap();
        for (Map.Entry<PS, TerritoryAccess> entry : this.map.entrySet()) {
            TerritoryAccess ta = entry.getValue();
            Faction faction = ta.getHostFaction();
            if (faction == null) continue;
            Integer count = (Integer)ret.get(faction);
            if (count == null) {
                count = 0;
            }
            ret.put(faction, count + 1);
        }
        return ret;
    }

    @Override
    public boolean hasClaimed(Faction faction) {
        return this.hasClaimed(faction.getId());
    }

    @Override
    public boolean hasClaimed(String factionId) {
        for (TerritoryAccess ta : this.map.values()) {
            if (!ta.getHostFactionId().equals(factionId)) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean isBorderPs(PS ps) {
        ps = ps.getChunk(true);
        PS nearby = null;
        Faction faction = this.getFactionAt(ps);
        if (faction != this.getFactionAt(nearby = ps.withChunkX(Integer.valueOf(ps.getChunkX() + 1)))) {
            return true;
        }
        nearby = ps.withChunkX(Integer.valueOf(ps.getChunkX() - 1));
        if (faction != this.getFactionAt(nearby)) {
            return true;
        }
        nearby = ps.withChunkZ(Integer.valueOf(ps.getChunkZ() + 1));
        if (faction != this.getFactionAt(nearby)) {
            return true;
        }
        nearby = ps.withChunkZ(Integer.valueOf(ps.getChunkZ() - 1));
        return faction != this.getFactionAt(nearby);
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
        ps = ps.getChunk(true);
        PS nearby = null;
        nearby = ps.withChunkX(Integer.valueOf(ps.getChunkX() + 1));
        if (faction == this.getFactionAt(nearby)) {
            return true;
        }
        nearby = ps.withChunkX(Integer.valueOf(ps.getChunkX() - 1));
        if (faction == this.getFactionAt(nearby)) {
            return true;
        }
        nearby = ps.withChunkZ(Integer.valueOf(ps.getChunkZ() + 1));
        if (faction == this.getFactionAt(nearby)) {
            return true;
        }
        nearby = ps.withChunkZ(Integer.valueOf(ps.getChunkZ() - 1));
        return faction == this.getFactionAt(nearby);
    }

    @Override
    public boolean isAnyConnectedPs(Set<PS> pss, Faction faction) {
        for (PS ps : pss) {
            if (!this.isConnectedPs(ps, faction)) continue;
            return true;
        }
        return false;
    }

    @Override
    public List<Mson> getMap(Player p, RelationParticipator observer, PS centerPs, double inDegrees, int width, int height) {
        centerPs = centerPs.getChunkCoords(true);
        ArrayList<Mson> ret = new ArrayList<Mson>();
        ret.add(Mson.mson((String)"   "));
        String blacklargesquare = "\u2588";
        int halfWidth = width / 2;
        int halfHeight = height / 2;
        width = halfWidth * 2 + 1;
        height = halfHeight * 2 + 1;
        PS topLeftPs = centerPs.plusChunkCoords(-halfWidth, -halfHeight);
        List<String> asciiCompass = AsciiCompass.getAsciiCompass(inDegrees);
        int dz = 0;
        while (dz < height) {
            ArrayList<Mson> row = new ArrayList<Mson>();
            int dx = 0;
            while (dx < width) {
                if (dx == halfWidth && dz == halfHeight) {
                    row.add(Mson.mson((String)(ChatColor.YELLOW + blacklargesquare)));
                } else {
                    PS herePs = topLeftPs.plusChunkCoords(dx, dz);
                    Faction hereFaction = this.getFactionAt(herePs);
                    PS borda = herePs;
                    if (borda.getChunkCoords().getChunkX() > MConf.get().bordaXpositivo / 16) {
                        row.add(Mson.mson((String)(ChatColor.BLACK + blacklargesquare)));
                    } else if (borda.getChunkCoords().getChunkZ() > MConf.get().bordaZpositivo / 16) {
                        row.add(Mson.mson((String)(ChatColor.BLACK + blacklargesquare)));
                    } else if (borda.getChunkCoords().getChunkZ() < MConf.get().bordaZnegativo / 16) {
                        row.add(Mson.mson((String)(ChatColor.BLACK + blacklargesquare)));
                    } else if (borda.getChunkCoords().getChunkX() < MConf.get().bordaXnegativo / 16) {
                        row.add(Mson.mson((String)(ChatColor.BLACK + blacklargesquare)));
                    } else if (hereFaction.isNone()) {
                        row.add(Mson.mson((String)(ChatColor.GRAY + blacklargesquare)));
                    } else if (Board.getSobAtaque(p, herePs, p.getWorld().getName())) {
                        row.add(Mson.mson((String)(ChatColor.LIGHT_PURPLE + blacklargesquare)).tooltip(String.valueOf(hereFaction.getColorTo(observer).toString()) + hereFaction.getName()));
                    } else {
                        row.add(Mson.mson((String)(String.valueOf(hereFaction.getColorTo(observer).toString()) + blacklargesquare)).tooltip(String.valueOf(hereFaction.getColorTo(observer).toString()) + hereFaction.getName()));
                    }
                }
                ++dx;
            }
            if (dz == 5) {
                row.add(Mson.mson((String)(" " + asciiCompass.get(0))).bold(Boolean.valueOf(true)));
            }
            if (dz == 6) {
                row.add(Mson.mson((String)(" " + asciiCompass.get(1))).bold(Boolean.valueOf(true)));
            }
            if (dz == 7) {
                row.add(Mson.mson((String)(" " + asciiCompass.get(2))).bold(Boolean.valueOf(true)));
            }
            if (dz == 9) {
                row.add(Mson.mson((String)(" " + MConf.get().colorAlly + blacklargesquare + " " + ChatColor.WHITE + "Aliada")));
            }
            if (dz == 10) {
                row.add(Mson.mson((String)(" " + MConf.get().colorNeutral + blacklargesquare + " " + ChatColor.WHITE + "Neutra")));
            }
            if (dz == 11) {
                row.add(Mson.mson((String)(" " + MConf.get().colorEnemy + blacklargesquare + " " + ChatColor.WHITE + "Inimiga")));
            }
            if (dz == 12) {
                row.add(Mson.mson((String)(" " + ChatColor.GRAY + blacklargesquare + " " + ChatColor.WHITE + "Zona Livre")));
            }
            if (dz == 13) {
                row.add(Mson.mson((String)(" " + MConf.get().colorNoPVP + blacklargesquare + " " + ChatColor.WHITE + "Zona Protegida")));
            }
            if (dz == 14) {
                row.add(Mson.mson((String)(" " + MConf.get().colorFriendlyFire + blacklargesquare + " " + ChatColor.WHITE + "Zona de Guerra")));
            }
            if (dz == 15) {
                row.add(Mson.mson((String)(" " + ChatColor.YELLOW + blacklargesquare + " " + ChatColor.WHITE + "Sua posi\u00e7\u00e3o")));
            }
            if (dz == 16) {
                row.add(Mson.mson((String)(" " + MConf.get().colorMember + blacklargesquare + " " + ChatColor.WHITE + "Sua fac\u00e7\u00e3o")));
            }
            if (dz == 17) {
                row.add(Mson.mson((String)(" " + ChatColor.LIGHT_PURPLE + blacklargesquare + " " + ChatColor.WHITE + "Sob ataque")));
            }
            ret.add(Mson.mson(row));
            ++dz;
        }
        return ret;
    }

    public static boolean getSobAtaque(Player p, PS ps, String world) {
        Chunk chunk = Bukkit.getWorld((String)p.getWorld().getName()).getChunkAt(ps.getChunkX().intValue(), ps.getChunkZ().intValue());
        return EngineSobAtaque.underattack.containsKey(chunk);
    }
}

