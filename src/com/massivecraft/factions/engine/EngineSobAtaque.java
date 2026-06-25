/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.Engine
 *  com.massivecraft.massivecore.ps.PS
 *  org.bukkit.Chunk
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.entity.EntityExplodeEvent
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 */
package com.massivecraft.factions.engine;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsChunksChange;
import com.massivecraft.factions.event.EventFactionsDisband;
import com.massivecraft.factions.event.EventFactionsEnteredInAttack;
import com.massivecraft.factions.event.EventFactionsFinishAttack;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.ps.PS;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class EngineSobAtaque
extends Engine {
    private static EngineSobAtaque i = new EngineSobAtaque();
    public static ConcurrentHashMap<Chunk, Long> underattack = new ConcurrentHashMap();
    public static ConcurrentHashMap<String, Faction> factionattack = new ConcurrentHashMap();
    public static ConcurrentHashMap<Chunk, Location> infoattack = new ConcurrentHashMap();

    public static EngineSobAtaque get() {
        return i;
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent e) {
        Faction faction = BoardColl.get().getFactionAt(PS.valueOf((Location)e.getLocation()));
        Chunk c = e.getLocation().getChunk();
        if (underattack.containsKey(c)) {
            return;
        }
        if (faction.getId().equals("safezone")) {
            return;
        }
        if (faction.getId().equals("warzone")) {
            return;
        }
        if (faction.getId().equals("none")) {
            return;
        }
        boolean already = factionattack.containsKey(faction.getName());
        EventFactionsEnteredInAttack event = new EventFactionsEnteredInAttack(faction, e.getLocation(), already, e);
        event.run();
        if (event.isCancelled()) {
            return;
        }
        underattack.put(c, System.currentTimeMillis());
        factionattack.put(faction.getName(), faction);
        infoattack.put(c, e.getLocation());
        this.desligarFlyDosPlayers(faction);
    }

    private void desligarFlyDosPlayers(Faction f) {
        for (Player p : f.getOnlinePlayers()) {
            if (p.hasPermission("factions.voar.bypass") || !p.getAllowFlight()) continue;
            p.sendMessage("\u00a7cSua fac\u00e7\u00e3o entrou em ataque portanto seu modo voar foi automaticamente desabilitado.");
            p.setAllowFlight(false);
            p.setFlying(false);
        }
    }

    public void aumentarSegundos(Chunk c) {
        underattack.replace(c, underattack.get(c) + 1L);
    }

    public void remover(Chunk c, Faction f) {
        underattack.remove(c);
        infoattack.remove(c);
        for (Chunk chunk : infoattack.keySet()) {
            Faction at = BoardColl.get().getFactionAt(PS.valueOf((Chunk)chunk));
            if (!at.getName().equalsIgnoreCase(f.getName())) continue;
            return;
        }
        factionattack.remove(f.getName());
        EventFactionsFinishAttack event = new EventFactionsFinishAttack(c, f);
        event.run();
    }

    public long getTime(Chunk c) {
        return underattack.get(c);
    }

    @EventHandler(priority=EventPriority.LOW, ignoreCancelled=true)
    public void terras(EventFactionsChunksChange e) {
        MPlayer mp = e.getMPlayer();
        if (factionattack.containsKey(mp.getFaction().getName())) {
            e.getSender().sendMessage("\u00a7cVoc\u00ea n\u00e3o pode controlar territ\u00f3rios enquanto estiver sobre ataque!");
            e.setCancelled(true);
            return;
        }
        for (PS ps : e.getChunks()) {
            Chunk c = PS.asBukkitChunk((PS)ps);
            if (!underattack.containsKey(c)) continue;
            underattack.remove(c);
            infoattack.remove(c);
            Faction atual = BoardColl.get().getFactionAt(ps);
            for (Chunk chunk : infoattack.keySet()) {
                Faction at = BoardColl.get().getFactionAt(PS.valueOf((Chunk)chunk));
                if (!at.equals(atual)) continue;
                return;
            }
            factionattack.remove(atual.getName());
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void desfazer(EventFactionsDisband e) {
        if (factionattack.containsKey(e.getFaction().getName())) {
            e.setCancelled(true);
            e.getSender().sendMessage("\u00a7cVoc\u00ea n\u00e3o pode desfazer a sua fac\u00e7\u00e3o enquanto ela estiver sobre ataque!");
            return;
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void aoExecutarComando(PlayerCommandPreprocessEvent e) {
        String cmd;
        Player p = e.getPlayer();
        MPlayer mp = MPlayer.get(p);
        Faction fac = mp.getFaction();
        if (factionattack.containsKey(fac.getName()) && ((cmd = e.getMessage().toLowerCase()).startsWith("/f unclaim") || cmd.startsWith("/f desproteger") || cmd.startsWith("/f abandonar"))) {
            e.setCancelled(true);
            p.sendMessage("\u00a7cVoc\u00ea n\u00e3o pode controlar territ\u00f3rios enquanto estiver sobre ataque!");
            return;
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onBreakSpawner(BlockBreakEvent e) {
        Faction fac;
        MPlayer mp;
        Player p;
        if (e.getBlock().getType() == Material.MOB_SPAWNER) {
            p = e.getPlayer();
            mp = MPlayer.get(p);
            if (!mp.hasFaction()) {
                return;
            }
            fac = BoardColl.get().getFactionAt(PS.valueOf((Block)e.getBlock()));
            if (factionattack.containsKey(fac.getName())) {
                p.sendMessage("\u00a7cVoc\u00ea n\u00e3o pode remover \u00a7lgeradores \u00a7cenquanto estiver sob ataque!");
                e.setCancelled(true);
                return;
            }
        }
        if (e.getBlock().getType() == Material.BEACON) {
            p = e.getPlayer();
            mp = MPlayer.get(p);
            if (!mp.hasFaction()) {
                return;
            }
            fac = BoardColl.get().getFactionAt(PS.valueOf((Block)e.getBlock()));
            if (factionattack.containsKey(fac.getName())) {
                p.sendMessage("\u00a7cVoc\u00ea n\u00e3o pode remover \u00a7lsinalizadores \u00a7cenquanto estiver sob ataque!");
                e.setCancelled(true);
                return;
            }
        }
    }
}

