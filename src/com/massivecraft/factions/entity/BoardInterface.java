/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.mson.Mson
 *  com.massivecraft.massivecore.ps.PS
 *  org.bukkit.entity.Player
 */
package com.massivecraft.factions.entity;

import com.massivecraft.factions.RelationParticipator;
import com.massivecraft.factions.TerritoryAccess;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.mson.Mson;
import com.massivecraft.massivecore.ps.PS;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.entity.Player;

public interface BoardInterface {
    public TerritoryAccess getTerritoryAccessAt(PS var1);

    public Faction getFactionAt(PS var1);

    public void setTerritoryAccessAt(PS var1, TerritoryAccess var2);

    public void setFactionAt(PS var1, Faction var2);

    public void removeAt(PS var1);

    public void removeAll(Faction var1);

    public Set<PS> getChunks(Faction var1);

    public Set<PS> getChunks(String var1);

    public Map<Faction, Set<PS>> getFactionToChunks();

    public int getCount(Faction var1);

    public int getCount(String var1);

    public Map<Faction, Integer> getFactionToCount();

    public boolean hasClaimed(Faction var1);

    public boolean hasClaimed(String var1);

    public boolean isBorderPs(PS var1);

    public boolean isAnyBorderPs(Set<PS> var1);

    public boolean isConnectedPs(PS var1, Faction var2);

    public boolean isAnyConnectedPs(Set<PS> var1, Faction var2);

    public List<Mson> getMap(Player var1, RelationParticipator var2, PS var3, double var4, int var6, int var7);
}

