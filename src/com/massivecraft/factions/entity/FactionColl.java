/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.store.Coll
 *  com.massivecraft.massivecore.util.Txt
 */
package com.massivecraft.factions.entity;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.util.MiscUtil;
import com.massivecraft.massivecore.store.Coll;
import com.massivecraft.massivecore.util.Txt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FactionColl
extends Coll<Faction> {
    private static FactionColl i = new FactionColl();

    public static FactionColl get() {
        return i;
    }

    public void onTick() {
        super.onTick();
    }

    public void setActive(boolean active) {
        super.setActive(active);
        if (!active) {
            return;
        }
        this.createSpecialFactions();
    }

    public void createSpecialFactions() {
        this.getNone();
        this.getSafezone();
        this.getWarzone();
    }

    public Faction getNone() {
        String id = "none";
        Faction faction = (Faction)this.get(id);
        if (faction != null) {
            return faction;
        }
        faction = (Faction)this.create(id);
        faction.setName(Factions.NAME_NONE_DEFAULT);
        faction.setDescription("\u00c9 perigoso sair s\u00f3zinho!");
        faction.setFlag(MFlag.getFlagPermanent(), true);
        faction.setFlag(MFlag.getFlagInfpower(), true);
        faction.setFlag(MFlag.getFlagPeaceful(), false);
        faction.setFlag(MFlag.getFlagPowerloss(), true);
        faction.setFlag(MFlag.getFlagPvp(), true);
        faction.setFlag(MFlag.getFlagFriendlyire(), false);
        faction.setPermittedRelations(MPerm.getPermBuild(), Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.RECRUIT, Rel.ALLY, Rel.TRUCE, Rel.NEUTRAL, Rel.ENEMY);
        faction.setPermittedRelations(MPerm.getPermDoor(), Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.RECRUIT, Rel.ALLY, Rel.TRUCE, Rel.NEUTRAL, Rel.ENEMY);
        faction.setPermittedRelations(MPerm.getPermContainer(), Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.RECRUIT, Rel.ALLY, Rel.TRUCE, Rel.NEUTRAL, Rel.ENEMY);
        faction.setPermittedRelations(MPerm.getPermButton(), Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.RECRUIT, Rel.ALLY, Rel.TRUCE, Rel.NEUTRAL, Rel.ENEMY);
        faction.setPermittedRelations(MPerm.getPermLever(), Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.RECRUIT, Rel.ALLY, Rel.TRUCE, Rel.NEUTRAL, Rel.ENEMY);
        return faction;
    }

    public Faction getSafezone() {
        String id = "safezone";
        Faction faction = (Faction)this.get(id);
        if (faction != null) {
            return faction;
        }
        faction = (Faction)this.create(id);
        faction.setName(Factions.NAME_SAFEZONE_DEFAULT);
        faction.setDescription("Livre de monstros e PvP!");
        faction.setFlag(MFlag.getFlagPermanent(), true);
        faction.setFlag(MFlag.getFlagInfpower(), true);
        faction.setFlag(MFlag.getFlagPeaceful(), true);
        faction.setFlag(MFlag.getFlagPowerloss(), false);
        faction.setFlag(MFlag.getFlagPvp(), false);
        faction.setFlag(MFlag.getFlagFriendlyire(), false);
        faction.setPermittedRelations(MPerm.getPermBuild(), Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.RECRUIT);
        faction.setPermittedRelations(MPerm.getPermDoor(), Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.RECRUIT, Rel.ALLY, Rel.TRUCE, Rel.NEUTRAL, Rel.ENEMY);
        faction.setPermittedRelations(MPerm.getPermContainer(), Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.RECRUIT, Rel.ALLY, Rel.TRUCE, Rel.NEUTRAL, Rel.ENEMY);
        faction.setPermittedRelations(MPerm.getPermButton(), Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.RECRUIT);
        faction.setPermittedRelations(MPerm.getPermLever(), Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.RECRUIT);
        return faction;
    }

    public Faction getWarzone() {
        String id = "warzone";
        Faction faction = (Faction)this.get(id);
        if (faction != null) {
            return faction;
        }
        faction = (Faction)this.create(id);
        faction.setName(Factions.NAME_WARZONE_DEFAULT);
        faction.setDescription("N\u00e3o \u00e9 um lugar seguro para se estar!");
        faction.setFlag(MFlag.getFlagPermanent(), true);
        faction.setFlag(MFlag.getFlagInfpower(), true);
        faction.setFlag(MFlag.getFlagPeaceful(), true);
        faction.setFlag(MFlag.getFlagPowerloss(), true);
        faction.setFlag(MFlag.getFlagPvp(), true);
        faction.setFlag(MFlag.getFlagFriendlyire(), true);
        faction.setPermittedRelations(MPerm.getPermBuild(), Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.RECRUIT);
        faction.setPermittedRelations(MPerm.getPermDoor(), Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.RECRUIT, Rel.ALLY, Rel.TRUCE, Rel.NEUTRAL, Rel.ENEMY);
        faction.setPermittedRelations(MPerm.getPermContainer(), Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.RECRUIT, Rel.ALLY, Rel.TRUCE, Rel.NEUTRAL, Rel.ENEMY);
        faction.setPermittedRelations(MPerm.getPermButton(), Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.RECRUIT);
        faction.setPermittedRelations(MPerm.getPermLever(), Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.RECRUIT);
        return faction;
    }

    public Faction getByName(String name) {
        String compStr = MiscUtil.getComparisonString(name);
        for (Faction faction : this.getAll()) {
            if (!faction.getComparisonName().equals(compStr)) continue;
            return faction;
        }
        return null;
    }

    public boolean isNameTaken(String name) {
        return this.getByName(name) != null;
    }

    public boolean isTagTaken(String tag) {
        if (tag == null || tag.isEmpty()) return false;
        String upper = tag.toUpperCase();
        for (Faction faction : this.getAll()) {
            if (upper.equals(faction.getTag())) return true;
        }
        return false;
    }

    private final java.util.Map<String, Integer> rankCache = new java.util.HashMap<String, Integer>();
    private long rankCacheTime = 0;
    private static final long RANK_CACHE_TTL = 30_000L; // 30 seconds

    private void refreshRankCacheIfNeeded() {
        long now = System.currentTimeMillis();
        if (now - rankCacheTime < RANK_CACHE_TTL) return;
        rankCacheTime = now;
        rankCache.clear();

        List<Faction> all = new ArrayList<Faction>();
        for (Faction f : this.getAll()) {
            if (f.isNormal()) all.add(f);
        }

        final java.util.Map<String, Double> balances = new java.util.HashMap<String, Double>();
        for (Faction f : all) {
            balances.put(f.getId(), getFactionBalance(f));
        }

        List<Faction> byPower = new ArrayList<Faction>(all);
        Collections.sort(byPower, new java.util.Comparator<Faction>() {
            public int compare(Faction a, Faction b) { return Double.compare(b.getPower(), a.getPower()); }
        });

        List<Faction> byCoins = new ArrayList<Faction>(all);
        Collections.sort(byCoins, new java.util.Comparator<Faction>() {
            public int compare(Faction a, Faction b) {
                double ba = balances.containsKey(a.getId()) ? balances.get(a.getId()) : 0;
                double bb = balances.containsKey(b.getId()) ? balances.get(b.getId()) : 0;
                return Double.compare(bb, ba);
            }
        });

        for (Faction f : all) {
            int rp = rankIn(f, byPower);
            int rc = rankIn(f, byCoins);
            rankCache.put(f.getId(), Math.min(rp, rc));
        }
    }

    private List<Faction> getNormalFactions() {
        List<Faction> list = new ArrayList<Faction>();
        for (Faction f : this.getAll()) {
            if (f.isNormal()) list.add(f);
        }
        return list;
    }

    private int rankIn(Faction target, List<Faction> sorted) {
        for (int i = 0; i < sorted.size(); i++) {
            if (sorted.get(i).getId().equals(target.getId())) return i + 1;
        }
        return Integer.MAX_VALUE;
    }

    private double getFactionBalance(Faction faction) {
        net.milkbowl.vault.economy.Economy eco = getEconomy();
        if (eco == null) return 0;
        double total = 0;
        for (com.massivecraft.factions.entity.MPlayer mp : faction.getMPlayers()) {
            try {
                org.bukkit.OfflinePlayer op = org.bukkit.Bukkit.getOfflinePlayer(mp.getName());
                total += eco.getBalance(op);
            } catch (Exception ignored) {}
        }
        return total;
    }

    private net.milkbowl.vault.economy.Economy getEconomy() {
        try {
            org.bukkit.plugin.RegisteredServiceProvider<net.milkbowl.vault.economy.Economy> rsp =
                org.bukkit.Bukkit.getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
            return rsp == null ? null : rsp.getProvider();
        } catch (Exception e) {
            return null;
        }
    }

    public int getBestRank(Faction target) {
        if (target == null || !target.isNormal()) return Integer.MAX_VALUE;
        refreshRankCacheIfNeeded();
        Integer cached = rankCache.get(target.getId());
        return cached != null ? cached : Integer.MAX_VALUE;
    }

    public String getRankColor(Faction faction) {
        int rank = this.getBestRank(faction);
        if (rank == 1) return MConf.get().colorTagTop1;
        if (rank == 2) return MConf.get().colorTagTop2;
        if (rank == 3) return MConf.get().colorTagTop3;
        return "§7";
    }

    public List<Faction> getTopFactions(int limit) {
        List<Faction> sorted = getNormalFactions();
        Collections.sort(sorted, new java.util.Comparator<Faction>() {
            public int compare(Faction a, Faction b) {
                return Double.compare(b.getPower(), a.getPower());
            }
        });
        return sorted.subList(0, Math.min(limit, sorted.size()));
    }

    public Faction getByTag(String tag) {
        if (tag == null || tag.isEmpty()) return null;
        String upper = tag.toUpperCase();
        for (Faction faction : this.getAll()) {
            if (upper.equals(faction.getTag())) return faction;
        }
        return null;
    }

    public Map<Rel, List<String>> getRelationNames(Faction faction, Set<Rel> rels) {
        LinkedHashMap<Rel, List<String>> ret = new LinkedHashMap<Rel, List<String>>();
        MFlag flagPeaceful = MFlag.getFlagPeaceful();
        boolean peaceful = faction.getFlag(flagPeaceful);
        for (Rel rel : rels) {
            ret.put(rel, new ArrayList());
        }
        for (Faction fac : FactionColl.get().getAll()) {
            Rel rel;
            List names;
            if (fac.getFlag(flagPeaceful) || (names = (List)ret.get((Object)(rel = fac.getRelationTo(faction)))) == null) continue;
            String name = fac.describeTo(faction, true);
            names.add(name);
        }
        if (!peaceful) {
            return ret;
        }
        List names = (List)ret.get((Object)Rel.TRUCE);
        if (names == null) {
            return ret;
        }
        ret.put(Rel.TRUCE, Collections.singletonList(String.valueOf(MConf.get().colorTruce.toString()) + Txt.parse((String)"<italic>*todos*")));
        return ret;
    }
}

