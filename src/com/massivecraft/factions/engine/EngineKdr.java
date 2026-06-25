/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.Engine
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.entity.PlayerDeathEvent
 */
package com.massivecraft.factions.engine;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.Engine;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class EngineKdr
extends Engine {
    private static EngineKdr i = new EngineKdr();

    public static EngineKdr get() {
        return i;
    }

    public static int getPlayerKills(MPlayer mp) {
        double kills = mp.getKills();
        return (int)kills;
    }

    public static int getPlayerDeaths(MPlayer mp) {
        double deaths = mp.getDeaths();
        return (int)deaths;
    }

    public static String getPlayerKdr(MPlayer mp) {
        Double kdr = mp.getKdr();
        String kdr2f = String.format("%.2f", kdr);
        if (kdr2f == null) {
            return "0.00";
        }
        return kdr2f;
    }

    public static int getFacKills(Faction f) {
        int fackills = 0;
        List<MPlayer> mps = f.getMPlayers();
        int i = 0;
        while (i < mps.size()) {
            MPlayer mp = mps.get(i);
            fackills = (int)((double)fackills + mp.getKills());
            ++i;
        }
        return fackills;
    }

    public static int getFacDeaths(Faction f) {
        int facdeaths = 0;
        List<MPlayer> mps = f.getMPlayers();
        int i = 0;
        while (i < mps.size()) {
            MPlayer mp = mps.get(i);
            facdeaths = (int)((double)facdeaths + mp.getDeaths());
            ++i;
        }
        return facdeaths;
    }

    public static double getFacKdr(Faction f) {
        double fdeaths = EngineKdr.getFacDeaths(f);
        int fkills = EngineKdr.getFacKills(f);
        if (fdeaths == 0.0) {
            return fkills;
        }
        double fkdr = (double)fkills / fdeaths;
        return fkdr;
    }

    @EventHandler
    public void aoMorrer(PlayerDeathEvent e) {
        Player p = e.getEntity();
        Player en = p.getKiller();
        if (en instanceof Player) {
            MPlayer matador = MPlayer.get(p.getKiller());
            MPlayer difunto = MPlayer.get(p);
            matador.setKills(matador.getKills() + 1.0);
            difunto.setDeaths(difunto.getDeaths() + 1.0);
        }
    }
}

