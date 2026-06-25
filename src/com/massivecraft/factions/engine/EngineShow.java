/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.Engine
 *  com.massivecraft.massivecore.PriorityLines
 *  com.massivecraft.massivecore.util.TimeDiffUtil
 *  com.massivecraft.massivecore.util.TimeUnit
 *  com.massivecraft.massivecore.util.Txt
 *  org.bukkit.command.CommandSender
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 */
package com.massivecraft.factions.engine;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.comparator.ComparatorMPlayerRole;
import com.massivecraft.factions.engine.EngineKdr;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsFactionShowAsync;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.PriorityLines;
import com.massivecraft.massivecore.util.TimeDiffUtil;
import com.massivecraft.massivecore.util.TimeUnit;
import com.massivecraft.massivecore.util.Txt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class EngineShow
extends Engine {
    public static final String BASENAME = "factions";
    public static final String BASENAME_ = "factions_";
    public static final String SHOW_ID_FACTION_ID = "factions_id";
    public static final String SHOW_ID_FACTION_DESCRIPTION = "factions_description";
    public static final String SHOW_ID_FACTION_AGE = "factions_age";
    public static final String SHOW_ID_FACTION_POWER = "factions_power";
    public static final String SHOW_ID_FACTION_STATS = "factions_stats";
    public static final String SHOW_ID_FACTION_FOLLOWERS = "factions_followers";
    public static final String SHOW_ID_FACTION_ALIADOS = "factions_allys";
    public static final String SHOW_ID_FACTION_INIMIGOS = "factions_enemies";
    public static final int SHOW_PRIORITY_FACTION_ID = 1000;
    public static final int SHOW_PRIORITY_FACTION_DESCRIPTION = 2000;
    public static final int SHOW_PRIORITY_FACTION_AGE = 3000;
    public static final int SHOW_PRIORITY_FACTION_POWER = 5000;
    public static final int SHOW_PRIORITY_FACTION_STATS = 6000;
    public static final int SHOW_PRIORITY_FACTION_FOLLOWERS = 9000;
    public static final int SHOW_PRIORITY_FACTION_ALIADOS = 10000;
    public static final int SHOW_PRIORITY_FACTION_INIMIGOS = 11000;
    private static EngineShow i = new EngineShow();

    public static EngineShow get() {
        return i;
    }

    @EventHandler(priority=EventPriority.LOW, ignoreCancelled=true)
    public void onFactionShow(EventFactionsFactionShowAsync event) {
        int tableCols = 4;
        CommandSender sender = event.getSender();
        MPlayer mplayer = event.getMPlayer();
        Faction faction = event.getFaction();
        boolean normal = faction.isNormal();
        Map<String, PriorityLines> idPriorityLiness = event.getIdPriorityLiness();
        String none = Txt.parse((String)"\u00a77\u00a7oNingu\u00e9m");
        if (mplayer.isOverriding()) {
            EngineShow.show(idPriorityLiness, SHOW_ID_FACTION_ID, 1000, "ID", faction.getId());
        }
        EngineShow.show(idPriorityLiness, SHOW_ID_FACTION_DESCRIPTION, 2000, "Descri\u00e7\u00e3o", faction.getDescriptionDesc());
        if (normal) {
            long ageMillis = faction.getCreatedAtMillis() - System.currentTimeMillis();
            LinkedHashMap ageUnitcounts = TimeDiffUtil.limit((LinkedHashMap)TimeDiffUtil.unitcounts((long)ageMillis, (TreeSet)TimeUnit.getAllButMillis()), (int)3);
            String ageDesc = TimeDiffUtil.formatedMinimal((Map)ageUnitcounts, (String)"\u00a7e");
            EngineShow.show(idPriorityLiness, SHOW_ID_FACTION_AGE, 3000, "\u00a76Criada h\u00e1", String.valueOf(ageDesc) + "\u00a7e atr\u00e1s");
            double powerBoost = faction.getPowerBoost();
            String boost = powerBoost == 0.0 ? "" : String.valueOf(powerBoost > 0.0 ? " (b\u00f4nus: " : " (penalidade: ") + powerBoost + ")";
            String powerDesc = Txt.parse((String)"%d/%d/%d%s", (Object[])new Object[]{faction.getLandCount(), faction.getPowerRounded(), faction.getPowerMaxRounded(), boost});
            EngineShow.show(idPriorityLiness, SHOW_ID_FACTION_POWER, 5000, "Terras / Poder / Poder M\u00e1ximo", powerDesc);
            double kdr = EngineKdr.getFacKdr(faction);
            int kills = EngineKdr.getFacKills(faction);
            int deaths = EngineKdr.getFacDeaths(faction);
            String statsDesc = Txt.parse((String)"%d/%d/%.2f", (Object[])new Object[]{kills, deaths, kdr});
            EngineShow.show(idPriorityLiness, SHOW_ID_FACTION_STATS, 6000, "Abates / Mortes / Kdr", statsDesc);
            String aliados = "";
            String rival = "";
            Collection<Faction> facs = FactionColl.get().getAll();
            Set<String> relations = faction.getRelationWishes().keySet();
            for (String id : relations) {
                Faction fac = Faction.get(id);
                if (fac == null || !fac.getRelationTo(faction).equals((Object)Rel.ALLY)) continue;
                aliados = String.valueOf(aliados) + "\u00a78, " + fac.getName(faction);
            }
            for (Faction f : facs) {
                if (!faction.getRelationTo(f).equals((Object)Rel.ENEMY)) continue;
                rival = String.valueOf(rival) + "\u00a78, " + f.getName(faction);
            }
            if (aliados.equals("")) {
                aliados = "....\u00a77\u00a7oNenhum";
            }
            if (rival.equals("")) {
                rival = "....\u00a77\u00a7oNenhum";
            }
            if (rival.length() > 250) {
                rival = "\u00a77\u00a7oMuitos inimigos! Use /f rela\u00e7\u00e3o listar " + faction.getName();
            }
            EngineShow.show(idPriorityLiness, SHOW_ID_FACTION_ALIADOS, 10000, "Aliados", aliados.substring(4, aliados.length()));
            EngineShow.show(idPriorityLiness, SHOW_ID_FACTION_INIMIGOS, 11000, "Inimigos", rival.substring(4, rival.length()));
        }
        ArrayList<String> followerLines = new ArrayList<String>();
        ArrayList<String> followerNamesOnline = new ArrayList<String>();
        ArrayList<String> followerNamesOffline = new ArrayList<String>();
        List<MPlayer> followers = faction.getMPlayers();
        Collections.sort(followers, ComparatorMPlayerRole.get());
        for (MPlayer follower : followers) {
            if (follower.isOnline(sender)) {
                if (normal) {
                    followerNamesOnline.add("\u00a7f" + follower.getRole().getPrefix() + "\u00a7f" + follower.getDisplayName(mplayer));
                    continue;
                }
                followerNamesOnline.add("\u00a7f" + follower.getDisplayName(mplayer));
                continue;
            }
            if (!normal) continue;
            followerNamesOffline.add(String.valueOf(follower.getRole().getPrefix()) + follower.getDisplayName(mplayer));
        }
        String headerOnline = Txt.parse((String)"\u00a76Membros Online (%s):", (Object[])new Object[]{followerNamesOnline.size()});
        followerLines.add(headerOnline);
        if (followerNamesOnline.isEmpty()) {
            followerLines.add(none);
        } else {
            followerLines.addAll(EngineShow.table(followerNamesOnline, 4));
        }
        if (normal) {
            String headerOffline = Txt.parse((String)"\u00a76Membros Offline (%s):", (Object[])new Object[]{followerNamesOffline.size()});
            followerLines.add(headerOffline);
            if (followerNamesOffline.isEmpty()) {
                followerLines.add(none);
            } else {
                followerLines.addAll(EngineShow.table(followerNamesOffline, 4));
            }
        }
        idPriorityLiness.put(SHOW_ID_FACTION_FOLLOWERS, new PriorityLines(9000, new Object[]{followerLines}));
    }

    public static String show(String key, String value) {
        return Txt.parse((String)"\u00a76%s: \u00a7e%s", (Object[])new Object[]{key, value});
    }

    public static PriorityLines show(int priority, String key, String value) {
        return new PriorityLines(priority, new Object[]{EngineShow.show(key, value)});
    }

    public static void show(Map<String, PriorityLines> idPriorityLiness, String id, int priority, String key, String value) {
        idPriorityLiness.put(id, EngineShow.show(priority, key, value));
    }

    public static List<String> table(List<String> strings, int cols) {
        ArrayList<String> ret = new ArrayList<String>();
        StringBuilder row = new StringBuilder();
        int count = 0;
        Iterator<String> iter = strings.iterator();
        while (iter.hasNext()) {
            String string = iter.next();
            row.append(string);
            if (iter.hasNext() && ++count != cols) {
                row.append(Txt.parse((String)" \u00a7e| "));
                continue;
            }
            ret.add(row.toString());
            row = new StringBuilder();
            count = 0;
        }
        return ret;
    }
}

