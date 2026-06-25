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

