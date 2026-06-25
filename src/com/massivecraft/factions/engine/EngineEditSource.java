/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.Engine
 *  com.massivecraft.massivecore.ps.PS
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.entity.CreatureSpawnEvent
 *  org.bukkit.event.entity.CreatureSpawnEvent$SpawnReason
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package com.massivecraft.factions.engine;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.util.MiscUtil;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.ps.PS;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class EngineEditSource
extends Engine {
    private static EngineEditSource i = new EngineEditSource();
    private List<String> lista = new ArrayList<String>();

    public static EngineEditSource get() {
        return i;
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void comandoClaim(PlayerCommandPreprocessEvent e) {
        String cmd = e.getMessage().toLowerCase();
        if (cmd.equalsIgnoreCase("/f claim") || cmd.equalsIgnoreCase("/f proteger") || cmd.equalsIgnoreCase("/f conquistar") || cmd.equalsIgnoreCase("/f dominar")) {
            e.setMessage("/f claim one");
        } else if (cmd.startsWith("/f entrar zona de guerra") || cmd.startsWith("/f join zona de guerra")) {
            e.setMessage("/f join warzone");
        } else if (cmd.startsWith("/f join zona protegida") || cmd.startsWith("/f entrar zona protegida")) {
            e.setMessage("/f join safezone");
        }
    }

    @EventHandler
    public void aoMorrerAnunciarMorte(PlayerDeathEvent e) {
        Player p;
        Player en;
        if (MConf.get().anunciarMorteAoMorrer && (en = (p = e.getEntity()).getKiller()) instanceof Player) {
            Player k = p.getKiller().getPlayer();
            MPlayer mp = MPlayer.get(p);
            MPlayer mk = MPlayer.get(k);
            String facp = mp.getFaction().isNone() ? "" : "\u00a73[" + mp.getRole().getPrefix() + mp.getFaction().getName() + "\u00a73] ";
            String fack = mk.getFaction().isNone() ? "" : "\u00a73[" + mk.getRole().getPrefix() + mk.getFaction().getName() + "\u00a73] ";
            for (Player target : this.getPlayersNearby(p)) {
                target.sendMessage("\u00a73" + facp + p.getName() + "\u00a7c foi morto por \u00a73" + fack + k.getName());
            }
        }
    }

    private List<Player> getPlayersNearby(Player player) {
        ArrayList<Player> players = new ArrayList<Player>();
        int d2 = MConf.get().distanciaDoAnuncioEmBlocos * MConf.get().distanciaDoAnuncioEmBlocos;
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (p.getWorld() != player.getWorld() || !(p.getLocation().distanceSquared(player.getLocation()) <= (double)d2)) continue;
            players.add(p);
        }
        return players;
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void aoColocarSpawner(BlockPlaceEvent e) {
        if (MConf.get().bloquearSpawnersForaDoClaim && e.getBlockPlaced().getType() == Material.MOB_SPAWNER) {
            PS ps = PS.valueOf((Block)e.getBlockPlaced());
            Faction f = BoardColl.get().getFactionAt(ps);
            if (f.isNone()) {
                e.setCancelled(true);
                e.getPlayer().sendMessage("\u00a7cHey! Os geradores s\u00f3 podem ser usados em locais protegidos por sua fac\u00e7\u00e3o.");
            }
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void naoSpawnar(CreatureSpawnEvent e) {
        if (MConf.get().bloquearSpawnersForaDoClaim && e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER) {
            PS ps = PS.valueOf((Location)e.getLocation());
            Faction f = BoardColl.get().getFactionAt(ps);
            if (f.isNone()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void aoLogar(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        MPlayer mp = MPlayer.get(p);
        Faction f = mp.getFaction();
        if (!f.isNone()) {
            f.msg("\u00a7a" + mp.getRole().getPrefix() + mp.getName() + "\u00a7a entrou no servidor.");
        }
    }

    @EventHandler
    public void aoDeslogar(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        MPlayer mp = MPlayer.get(p);
        Faction f = mp.getFaction();
        if (!f.isNone()) {
            f.msg("\u00a7c" + mp.getRole().getPrefix() + mp.getName() + "\u00a7c saiu do servidor.");
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onCommandEvent(PlayerCommandPreprocessEvent e) {
        final Player p = e.getPlayer();
        String cmd = e.getMessage().toLowerCase();
        String[] args = cmd.split(" ");
        if (args[0].contains("sethome") || args[0].contains("setcasa") || args[0].contains("createhome")) {
            MPlayer mp = MPlayer.get(p);
            BoardColl coll = BoardColl.get();
            Faction faction = coll.getFactionAt(PS.valueOf((Location)e.getPlayer().getLocation()));
            if (!(faction.isNone() || faction.getMPlayers().contains(mp) || faction.getRelationTo(mp.getFaction()).equals((Object)Rel.ALLY))) {
                e.setCancelled(true);
                p.sendMessage("\u00a7cVoc\u00ea n\u00e3o tem permiss\u00e3o para definir uma home neste local.");
            }
        }
        if (args[0].contains("voltar") || args[0].contains("casa") || args[0].contains("return") || args[0].contains("home") || args[0].contains("back")) {
            this.lista.add(p.getName());
            this.lista.add(p.getName());
            new BukkitRunnable(){

                public void run() {
                    if (EngineEditSource.this.lista.contains(p.getName())) {
                        EngineEditSource.this.lista.remove(p.getName());
                        if (EngineEditSource.this.lista.contains(p.getName())) {
                            EngineEditSource.this.lista.remove(p.getName());
                        }
                    }
                }
            }.runTaskLater((Plugin)Factions.get(), 160L);
            return;
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void aoTeleportarComComandoBackOuHome(PlayerTeleportEvent e) {
        BoardColl coll;
        Faction faction;
        Player p = e.getPlayer();
        MPlayer mp = MPlayer.get(p);
        if (!(e.getCause() != PlayerTeleportEvent.TeleportCause.COMMAND && e.getCause() != PlayerTeleportEvent.TeleportCause.UNKNOWN && e.getCause() != PlayerTeleportEvent.TeleportCause.PLUGIN || (faction = (coll = BoardColl.get()).getFactionAt(PS.valueOf((Location)e.getTo()))).getMPlayers().contains(mp) || faction.getRelationTo(mp.getFaction()).equals((Object)Rel.ALLY) || !this.lista.contains(p.getName()))) {
            if (faction.getId().equals("none") || faction.getId().equals("warzone") || faction.getId().equals("safezone")) {
                return;
            }
            e.setCancelled(true);
            this.lista.remove(p.getName());
            p.sendMessage("\u00a7cVoc\u00ea n\u00e3o pode se teleportar para este local pois ele esta protegido pela fac\u00e7\u00e3o \u00a7f" + faction.getName() + "\u00a7c.");
        }
    }

    public static List<Faction> getAliadosPendentesEnviados(Faction f) {
        ArrayList<Faction> aliadosPendentesEnviados = new ArrayList<Faction>();
        Map<String, Rel> relations = f.getRelationWishes();
        for (Map.Entry<String, Rel> relation : relations.entrySet()) {
            Faction ally = Faction.get(relation.getKey());
            if (ally == null || relation.getValue() != Rel.ALLY || ally.getRelationWish(f) == Rel.ALLY) continue;
            aliadosPendentesEnviados.add(ally);
        }
        return aliadosPendentesEnviados;
    }

    public static List<Faction> getAliadosPendentesRecebidos(Faction f) {
        ArrayList<Faction> aliadosPendentesRecebidos = new ArrayList<Faction>();
        Collection<Faction> factions = FactionColl.get().getAll();
        for (Faction faction : factions) {
            Map<String, Rel> relations = faction.getRelationWishes();
            for (Map.Entry<String, Rel> relation : relations.entrySet()) {
                Faction ally;
                if (relation.getValue() != Rel.ALLY || (ally = Faction.get(relation.getKey())) != f || f.getRelationWishes().containsKey(faction.getId())) continue;
                aliadosPendentesRecebidos.add(faction);
            }
        }
        return aliadosPendentesRecebidos;
    }

    public static List<Faction> getAliados(Faction f) {
        ArrayList<Faction> aliados = new ArrayList<Faction>();
        Set<String> relations = f.getRelationWishes().keySet();
        for (String id : relations) {
            Faction fac = Faction.get(id);
            if (fac == null || !fac.getRelationTo(f).equals((Object)Rel.ALLY)) continue;
            aliados.add(fac);
        }
        return aliados;
    }

    public static List<String> fplayers(Faction f) {
        ArrayList<String> players = new ArrayList<String>();
        String pon = "";
        int i = 0;
        while (i < f.getOnlinePlayers().size()) {
            Player p = f.getOnlinePlayers().get(i);
            MPlayer mp = MPlayer.get(p);
            pon = String.valueOf(pon) + "\u00a77 " + mp.getRole().getPrefix() + mp.getName();
            ++i;
        }
        int tamanho = pon.length();
        if (tamanho < 40) {
            players.add("\u00a77" + pon.substring(0, tamanho));
            return players;
        }
        if (tamanho < 80) {
            players.add("\u00a77" + pon.substring(0, 40));
            players.add("\u00a77" + pon.substring(40, tamanho));
            return players;
        }
        if (tamanho < 120) {
            players.add("\u00a77" + pon.substring(0, 40));
            players.add("\u00a77" + pon.substring(40, 80));
            players.add("\u00a77" + pon.substring(80, tamanho));
            return players;
        }
        if (tamanho < 160) {
            players.add("\u00a77" + pon.substring(0, 40));
            players.add("\u00a77" + pon.substring(40, 80));
            players.add("\u00a77" + pon.substring(80, 120));
            players.add("\u00a77" + pon.substring(120, tamanho));
            return players;
        }
        if (tamanho < 200) {
            players.add("\u00a77" + pon.substring(0, 40));
            players.add("\u00a77" + pon.substring(40, 80));
            players.add("\u00a77" + pon.substring(80, 120));
            players.add("\u00a77" + pon.substring(120, 160));
            players.add("\u00a77" + pon.substring(160, tamanho));
            return players;
        }
        if (tamanho < 240) {
            players.add("\u00a77" + pon.substring(0, 40));
            players.add("\u00a77" + pon.substring(40, 80));
            players.add("\u00a77" + pon.substring(80, 120));
            players.add("\u00a77" + pon.substring(120, 160));
            players.add("\u00a77" + pon.substring(160, 200));
            players.add("\u00a77" + pon.substring(200, tamanho));
            return players;
        }
        players.add("\u00a77\u00a7oMuitos players online.");
        return players;
    }

    public static List<String> fmotd(Faction f) {
        String factionmotd = f.getMotdDesc();
        int factionmotdtamanho = factionmotd.length();
        ArrayList<String> motd = new ArrayList<String>();
        if (!f.hasMotd()) {
            motd.add("\u00a77\u00a7o'Mensagem do dia indefinida.'");
            return motd;
        }
        if (f.hasMotd() && factionmotdtamanho < 40) {
            motd.add("\u00a77'\u00a7b" + factionmotd.substring(0, factionmotdtamanho) + "\u00a77'");
            return motd;
        }
        if (f.hasMotd() && factionmotdtamanho < 110) {
            motd.add("\u00a77'\u00a7b" + factionmotd.substring(0, 40));
            motd.add("\u00a7b" + factionmotd.substring(50, factionmotdtamanho) + "\u00a77'");
            return motd;
        }
        motd.add("\u00a77A frase da motd \u00e9 muito grande!");
        motd.add("\u00a77Para visualiza-la use '\u00a7f/f motd\u00a77'");
        return motd;
    }

    public static Faction getFactionByName(String name) {
        String compStr = MiscUtil.getComparisonString(name);
        for (Faction faction : FactionColl.get().getAll()) {
            if (!faction.getComparisonName().equals(compStr)) continue;
            return faction;
        }
        return null;
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void enderPearlClipping(PlayerTeleportEvent event) {
        if (event.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            return;
        }
        Location target = event.getTo();
        Location from = event.getFrom();
        Material mat = event.getTo().getBlock().getType();
        if ((mat == Material.THIN_GLASS || mat == Material.IRON_FENCE) && EngineEditSource.clippingThrough(target, from, 0.65) || (mat == Material.FENCE || mat == Material.NETHER_FENCE) && EngineEditSource.clippingThrough(target, from, 0.45)) {
            event.setTo(from);
            return;
        }
        target.setX((double)target.getBlockX() + 0.5);
        target.setZ((double)target.getBlockZ() + 0.5);
        event.setTo(target);
    }

    public static boolean clippingThrough(Location target, Location from, double thickness) {
        return from.getX() > target.getX() && from.getX() - target.getX() < thickness || target.getX() > from.getX() && target.getX() - from.getX() < thickness || from.getZ() > target.getZ() && from.getZ() - target.getZ() < thickness || target.getZ() > from.getZ() && target.getZ() - from.getZ() < thickness;
    }
}

