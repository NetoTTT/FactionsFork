/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.collections.MassiveSet
 */
package com.massivecraft.factions;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.entity.MPlayerColl;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class FactionsIndex {
    private static FactionsIndex i = new FactionsIndex();
    private final Map<MPlayer, Faction> mplayer2faction = new WeakHashMap<MPlayer, Faction>();
    private final Map<Faction, Set<MPlayer>> faction2mplayers = new WeakHashMapCreativeImpl();

    public static FactionsIndex get() {
        return i;
    }

    private FactionsIndex() {
    }

    private boolean isConnected(MPlayer mplayer, Faction faction) {
        if (mplayer == null) {
            throw new NullPointerException("mplayer");
        }
        if (faction == null) {
            throw new NullPointerException("faction");
        }
        return mplayer.getFaction() == faction;
    }

    public synchronized Faction getFaction(MPlayer mplayer) {
        return this.mplayer2faction.get(mplayer);
    }

    public synchronized Set<MPlayer> getMPlayers(Faction faction) {
        return new HashSet<MPlayer>((Collection<MPlayer>)this.faction2mplayers.get(faction));
    }

    public synchronized void updateAll() {
        if (!MPlayerColl.get().isActive()) {
            throw new IllegalStateException("O MPlayerColl ainda nao esta totalmente ativado.");
        }
        for (MPlayer mplayer : MPlayerColl.get().getAll()) {
            this.update(mplayer);
        }
    }

    public synchronized void update(MPlayer mplayer) {
        if (mplayer == null) {
            throw new NullPointerException("mplayer");
        }
        if (!FactionColl.get().isActive()) {
            throw new IllegalStateException("O FactionColl ainda nao esta totalmente ativado.");
        }
        if (!mplayer.attached()) {
            return;
        }
        Faction factionActual = mplayer.getFaction();
        Faction factionIndexed = this.getFaction(mplayer);
        Set<Faction> factions = new HashSet<>();
        if (factionActual != null) {
            factions.add(factionActual);
        }
        if (factionIndexed != null) {
            factions.add(factionIndexed);
        }
        for (Faction faction : factions) {
            boolean connected = this.isConnected(mplayer, faction);
            if (connected) {
                this.faction2mplayers.get(faction).add(mplayer);
                continue;
            }
            this.faction2mplayers.get(faction).remove(mplayer);
        }
        this.mplayer2faction.put(mplayer, factionActual);
    }

    public synchronized void update(Faction faction) {
        if (faction == null) {
            throw new NullPointerException("faction");
        }
        for (MPlayer mplayer : this.getMPlayers(faction)) {
            this.update(mplayer);
        }
    }

    private static abstract class WeakHashMapCreative<K, V>
    extends WeakHashMap<K, V> {
        private WeakHashMapCreative() {
        }

        @Override
        @SuppressWarnings("unchecked")
        public V get(Object key) {
            V ret = (V)super.get(key);
            if (ret == null) {
                ret = this.createValue();
                this.put((K)key, ret);
            }
            return ret;
        }

        public abstract V createValue();
    }

    private static class WeakHashMapCreativeImpl
    extends WeakHashMapCreative<Faction, Set<MPlayer>> {
        private WeakHashMapCreativeImpl() {
        }

        @Override
        public Set<MPlayer> createValue() {
            return Collections.newSetFromMap(new WeakHashMap());
        }
    }
}

