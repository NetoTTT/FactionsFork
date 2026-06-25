/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.Engine
 *  com.massivecraft.massivecore.ps.PS
 *  org.bukkit.Bukkit
 *  org.bukkit.Chunk
 *  org.bukkit.Color
 *  org.bukkit.DyeColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.event.inventory.InventoryType$SlotType
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.inventory.ItemStack
 */
package com.massivecraft.factions.engine;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.engine.EngineEditSource;
import com.massivecraft.factions.engine.EngineKdr;
import com.massivecraft.factions.engine.EngineSobAtaque;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.util.Heads;
import com.massivecraft.factions.util.ItemBuilder;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.ps.PS;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class EngineMenuGui
extends Engine {
    private static EngineMenuGui i = new EngineMenuGui();

    public static EngineMenuGui get() {
        return i;
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void aoExecutarComando(PlayerCommandPreprocessEvent e) {
        String cmd = e.getMessage().toLowerCase();
        if (cmd.equalsIgnoreCase("/f")) {
            Player p = e.getPlayer();
            MPlayer mplayer = MPlayer.get(p);
            if (p instanceof Player) {
                if (mplayer.hasFaction()) {
                    e.setCancelled(true);
                    this.abrirMenuPlayerComFaccao(p);
                    return;
                }
                e.setCancelled(true);
                this.abrirMenuPlayerSemFaccao(p);
                return;
            }
        } else if (cmd.startsWith("/f desfazer") || cmd.startsWith("/f disband") || cmd.startsWith("/f deletar")) {
            Player p = e.getPlayer();
            MPlayer mplayer = MPlayer.get(p);
            if (p instanceof Player) {
                if (mplayer.hasFaction()) {
                    if (mplayer.getRole() == Rel.LEADER || mplayer.isOverriding()) {
                        e.setCancelled(true);
                        this.abrirMenuDesfazerFaccao(p);
                        return;
                    }
                    e.setCancelled(true);
                    p.sendMessage("\u00a7cApenas o l\u00edder pode desfazer a fac\u00e7\u00e3o.");
                    return;
                }
                return;
            }
        } else if (cmd.equalsIgnoreCase("/f convite") || cmd.equalsIgnoreCase("/f invite") || cmd.equalsIgnoreCase("/f convidar") || cmd.equalsIgnoreCase("/f adicionar") || cmd.equalsIgnoreCase("/f i")) {
            Player p = e.getPlayer();
            MPlayer mplayer = MPlayer.get(p);
            if (p instanceof Player) {
                if (mplayer.hasFaction()) {
                    if (mplayer.getRole() == Rel.OFFICER || mplayer.getRole() == Rel.LEADER || mplayer.isOverriding()) {
                        e.setCancelled(true);
                        this.abrirMenuConvite(p);
                        return;
                    }
                    e.setCancelled(true);
                    p.sendMessage("\u00a7cVoc\u00ea precisar ser capit\u00e3o ou superior para poder gerenciar os convites da fac\u00e7\u00e3o.");
                    return;
                }
                return;
            }
        } else if (cmd.startsWith("/f desproteger todas") || cmd.startsWith("/f abandonar todas") || cmd.startsWith("/f unclaim all")) {
            Player p = e.getPlayer();
            MPlayer mplayer = MPlayer.get(p);
            if (p instanceof Player) {
                if (mplayer.hasFaction()) {
                    if (mplayer.getRole() == Rel.LEADER || mplayer.getRole() == Rel.OFFICER || mplayer.isOverriding()) {
                        int terras = mplayer.getFaction().getLandCount();
                        if (terras >= 1) {
                            if (EngineSobAtaque.factionattack.containsKey(mplayer.getFaction().getName())) {
                                p.sendMessage("\u00a7cVoc\u00ea n\u00e3o pode controlar territ\u00f3rios enquanto estiver sobre ataque!");
                                return;
                            }
                            this.abrirMenuAbandonarTerras(p);
                            e.setCancelled(true);
                            return;
                        }
                        p.sendMessage("\u00a7cA sua fac\u00e7\u00e3o n\u00e3o possui terras para abandonar.");
                        e.setCancelled(true);
                        return;
                    }
                    p.sendMessage("\u00a7cSua fac\u00e7\u00e3o n\u00e3o deixa voc\u00ea administrar os territ\u00f3rios.");
                    e.setCancelled(true);
                    return;
                }
                return;
            }
        } else if (cmd.equalsIgnoreCase("/f top") || cmd.equalsIgnoreCase("/f rank") || cmd.equalsIgnoreCase("/f ranking")) {
            Player p = e.getPlayer();
            if (p instanceof Player) {
                e.setCancelled(true);
                this.abrirMenuRanking(p);
                return;
            }
        } else if (cmd.equalsIgnoreCase("/f relacao") || cmd.equalsIgnoreCase("/f rela\u00e7\u00e3o") || cmd.equalsIgnoreCase("/f relation") || cmd.equalsIgnoreCase("/f rel")) {
            Player p = e.getPlayer();
            MPlayer mplayer = MPlayer.get(p);
            if (p instanceof Player) {
                if (mplayer.hasFaction()) {
                    if (mplayer.getRole() == Rel.OFFICER || mplayer.getRole() == Rel.LEADER || mplayer.isOverriding()) {
                        e.setCancelled(true);
                        this.abrirMenuRelacoes(p);
                        return;
                    }
                    e.setCancelled(true);
                    p.sendMessage("\u00a7cVoc\u00ea precisar ser capit\u00e3o ou superior para poder gerenciar as rela\u00e7\u00f5es da fac\u00e7\u00e3o.");
                    return;
                }
                return;
            }
        }
    }

    public void abrirMenuPlayerComFaccao(Player p) {
        MPlayer mplayer = MPlayer.get(p);
        Rel cargo = mplayer.getRole();
        Faction factionclaim = BoardColl.get().getFactionAt(PS.valueOf((Location)p.getLocation()));
        Faction faction = mplayer.getFaction();
        String factionNome = faction.getName();
        String lider = faction.getLeader() == null ? "Indefinido" : faction.getLeader().getName();
        String factiondesc = faction.getDescriptionDesc();
        int fackills = EngineKdr.getFacKills(faction);
        int facmortes = EngineKdr.getFacDeaths(faction);
        int membrosonline = faction.getOnlinePlayers().size();
        int membrosnafac = faction.getMPlayers().size();
        int terrastotal = faction.getLandCount();
        int mortes = EngineKdr.getPlayerDeaths(mplayer);
        int kills = EngineKdr.getPlayerKills(mplayer);
        double fackdr = EngineKdr.getFacKdr(faction);
        double factionpodermaximo = faction.getPowerMax();
        double playerpodermaximo = mplayer.getPowerMax();
        double factionpoder = faction.getPower();
        double playerpoder = mplayer.getPower();
        String kdr2f = EngineKdr.getPlayerKdr(mplayer);
        String fackdr2f = String.format("%.2f", fackdr);
        String playerpoder1f = String.format("%.1f", playerpoder);
        String factionpoder1f = String.format("%.1f", factionpoder);
        Inventory inv = Bukkit.createInventory(null, (int)54, (String)("\u00a7r\u00a78Fac\u00e7\u00e3o - " + factionNome));
        inv.setItem(10, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setSkullOwner(p.getName()).setName("\u00a77" + p.getName()).setLore("\u00a7fPoder: \u00a77" + playerpoder1f + "/" + playerpodermaximo, "\u00a7fCargo: \u00a77" + cargo.getPrefix() + cargo.getName(), "\u00a7fAbates: \u00a77" + kills, "\u00a7fMortes: \u00a77" + mortes, "\u00a7fKdr: \u00a77" + kdr2f).toItemStack());
        inv.setItem(14, new ItemBuilder(Heads.ROXO.clone()).setName("\u00a7eRanking das Fac\u00e7\u00f5es").setLore("\u00a77Clique para ver os rankings com as", "\u00a77fac\u00e7\u00f5es mais poderosas do servidor.").toItemStack());
        inv.setItem(15, new ItemBuilder(Heads.LARANJA.clone()).setName("\u00a7eFac\u00e7\u00f5es Online").setLore("\u00a77Clique para ver a lista de fac\u00e7\u00f5es", "\u00a77online no servidor.").toItemStack());
        inv.setItem(16, new ItemBuilder(Heads.AMARELO.clone()).setName("\u00a7eAjuda").setLore("\u00a77Todas as a\u00e7\u00f5es dispon\u00edveis neste menu", "\u00a77tamb\u00e9m podem ser realizadas por", "\u00a77comando. Utilize o comando '\u00a7f/f ajuda\u00a77'", "\u00a77para ver todos os comandos dispon\u00edveis.").toItemStack());
        inv.setItem(30, new ItemBuilder(Material.PAPER).setName("\u00a7eGerenciar Convites").setLore("\u00a7fClique para gerenciar os", "\u00a7fconvites da sua fac\u00e7\u00e3o.").toItemStack());
        inv.setItem(31, new ItemBuilder(Material.BOOK_AND_QUILL).setName("\u00a7dGerenciar permiss\u00f5es").setLore("\u00a7fClique para gerenciar as", "\u00a7fpermiss\u00f5es da sua fac\u00e7\u00e3o.").toItemStack());
        inv.setItem(39, new ItemBuilder(Material.SKULL_ITEM, membrosnafac, 3).setName("\u00a7aMembros").setLore("\u00a7fA sua fac\u00e7\u00e3o possui \u00a73" + membrosnafac + "\u00a7f membros.", "\u00a7fClique para obter mais informa\u00e7\u00f5es.").toItemStack());
        inv.setItem(40, new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("\u00a7aRela\u00e7\u00f5es").setLore("\u00a7fClique para gerenciar todas", "\u00a7fas rela\u00e7\u00f5es da sua fac\u00e7\u00e3o.").setLeatherArmorColor(Color.LIME).toItemStack());
        if (!EngineSobAtaque.factionattack.containsKey(factionNome)) {
            inv.setItem(34, new ItemBuilder(Heads.BRANCO.clone()).setName("\u00a7e" + factionNome).setLore("\u00a7aA fac\u00e7\u00e3o n\u00e3o esta sob ataque.", "\u00a7fTerras: \u00a77" + terrastotal, "\u00a7fPoder: \u00a77" + factionpoder1f, "\u00a7fPoder m\u00e1ximo: \u00a77" + factionpodermaximo, "\u00a7fAbates: \u00a77" + fackills, "\u00a7fMortes: \u00a77" + facmortes, "\u00a7fKdr: \u00a77" + fackdr2f, "\u00a7fL\u00edder: \u00a77" + lider, "\u00a7fMembros: \u00a77" + membrosnafac + "/" + MConf.get().factionMemberLimit, "\u00a7fMembros online: \u00a77" + membrosonline, EngineEditSource.fplayers(faction), "\u00a77", "\u00a7fDescri\u00e7\u00e3o:", "\u00a77'" + factiondesc + "\u00a77'", "\u00a7f", "\u00a7fMotd: \u00a77", EngineEditSource.fmotd(faction)).toItemStack());
        } else {
            inv.setItem(34, new ItemBuilder(Heads.VERMELHO.clone()).setName("\u00a7e" + factionNome).setLore("\u00a7cFac\u00e7\u00e3o sob ataque! Clique para mais detalhes.", "\u00a7fTerras: \u00a77" + terrastotal, "\u00a7fPoder: \u00a77" + factionpoder1f, "\u00a7fPoder m\u00e1ximo: \u00a77" + factionpodermaximo, "\u00a7fAbates: \u00a77" + fackills, "\u00a7fMortes: \u00a77" + facmortes, "\u00a7fKdr: \u00a77" + fackdr2f, "\u00a7fL\u00edder: \u00a77" + lider, "\u00a7fMembros: \u00a77" + membrosnafac + "/" + MConf.get().factionMemberLimit, "\u00a7fMembros online: \u00a77" + membrosonline, EngineEditSource.fplayers(faction), "\u00a77", "\u00a7fDescri\u00e7\u00e3o:", "\u00a77'" + factiondesc + "\u00a77'", "\u00a7f", "\u00a7fMotd: \u00a77", EngineEditSource.fmotd(faction)).toItemStack());
        }
        if (mplayer.getRole() == Rel.LEADER) {
            inv.setItem(43, new ItemBuilder(Material.DARK_OAK_DOOR_ITEM).setName("\u00a7cDesfazer fac\u00e7\u00e3o").setLore("\u00a77Clique para desfazer a sua fac\u00e7\u00e3o.").toItemStack());
        } else {
            inv.setItem(43, new ItemBuilder(Material.DARK_OAK_DOOR_ITEM).setName("\u00a7cSair da fac\u00e7\u00e3o").setLore("\u00a77Clique para abandonar a sua fac\u00e7\u00e3o.").toItemStack());
        }
        if (mplayer.isTerritoryInfoTitles()) {
            inv.setItem(37, new ItemBuilder(Material.PAINTING).setName("\u00a7eTitulos dos Territ\u00f3rios").setLore("\u00a77Clique para alternar.", "", "\u00a7fStatus: \u00a7aAtivado").toItemStack());
        } else {
            inv.setItem(37, new ItemBuilder(Material.PAINTING).setName("\u00a7eTitulos dos Territ\u00f3rios").setLore("\u00a77Clique para alternar.", "", "\u00a7fStatus: \u00a7cDesativado").toItemStack());
        }
        if (mplayer.isMapAutoUpdating()) {
            inv.setItem(38, new ItemBuilder(Material.MAP).setName("\u00a7aMapa dos Territ\u00f3rios").setLore("\u00a77Voc\u00ea esta pisando em um territ\u00f3rio", "\u00a77protegido pela fac\u00e7\u00e3o \u00a7e" + factionclaim.getName(), "", "\u00a7fBot\u00e3o direito: \u00a77Mostra o mapa completo.", "\u00a7fBot\u00e3o esquerdo: \u00a77Desliga o mapa autom\u00e1tico.", "", "\u00a7fMapa autom\u00e1tico: \u00a7aLigado").addItemFlag(ItemFlag.HIDE_POTION_EFFECTS).toItemStack());
        } else {
            inv.setItem(38, new ItemBuilder(Material.EMPTY_MAP).setName("\u00a7aMapa dos Territ\u00f3rios").setLore("\u00a77Voc\u00ea esta pisando em um territ\u00f3rio", "\u00a77protegido pela fac\u00e7\u00e3o \u00a7e" + factionclaim.getName(), "", "\u00a7fBot\u00e3o direito: \u00a77Mostra o mapa completo.", "\u00a7fBot\u00e3o esquerdo: \u00a77Liga o mapa autom\u00e1tico.", "", "\u00a7fMapa autom\u00e1tico: \u00a7cDesligado").toItemStack());
        }
        if (MConf.get().colocarIconeDoFGeradoresNoMenuGUI) {
            inv.setItem(41, new ItemBuilder(Material.MOB_SPAWNER).setName("\u00a7aGeradores").setLore("\u00a7fClique para gerenciar os", "\u00a7fgeradores da sua fac\u00e7\u00e3o.").toItemStack());
        }
        if (MConf.get().colocarIconeDoFBauNoMenuGUI) {
            inv.setItem(41, new ItemBuilder(Material.CHEST).setName("\u00a7aBa\u00fa da fac\u00e7\u00e3o").setLore("\u00a7fClique para abir o ba\u00fa", "\u00a7fvirtual da sua fac\u00e7\u00e3o.").toItemStack());
        }
        if (mplayer.isSeeingChunk()) {
            inv.setItem(28, new ItemBuilder(Material.GRASS).setName("\u00a7aDelimita\u00e7\u00f5es das Terras").setLore("\u00a77Clique para alternar.", "", "\u00a7fStatus: \u00a7aAtivado").toItemStack());
        } else {
            inv.setItem(28, new ItemBuilder(Material.GRASS).setName("\u00a7aDelimita\u00e7\u00f5es das Terras").setLore("\u00a77Clique para alternar.", "", "\u00a7fStatus: \u00a7cDesativado").toItemStack());
        }
        if (p.getAllowFlight()) {
            inv.setItem(32, new ItemBuilder(Material.FEATHER).setName("\u00a7aModo voar").setLore("\u00a77Clique para alternar.", "", "\u00a7fStatus: \u00a7aAtivado").toItemStack());
        } else {
            inv.setItem(32, new ItemBuilder(Material.FEATHER).setName("\u00a7aModo voar").setLore("\u00a77Clique para alternar.", "", "\u00a7fStatus: \u00a7cDesativado").toItemStack());
        }
        if (faction.hasHome() && (cargo == Rel.LEADER || cargo == Rel.OFFICER)) {
            inv.setItem(29, new ItemBuilder(Material.BEDROCK).setName("\u00a7aBase da Fac\u00e7\u00e3o").setLore("\u00a77Sua fac\u00e7\u00e3o possui uma base!", "", "\u00a7fBot\u00e3o esquerdo: \u00a77Ir para base.", "\u00a7fBot\u00e3o direito: \u00a77Definir base.", "\u00a7fShift + Bot\u00e3o direito: \u00a77Remover base.").toItemStack());
        } else if (faction.hasHome() && cargo != Rel.LEADER && cargo != Rel.OFFICER) {
            inv.setItem(29, new ItemBuilder(Material.BEDROCK).setName("\u00a7aBase da Fac\u00e7\u00e3o").setLore("\u00a77Sua fac\u00e7\u00e3o possui uma base!", "", "\u00a7fBot\u00e3o esquerdo: \u00a77Ir para base.").toItemStack());
        } else if (!(faction.hasHome() || cargo != Rel.LEADER && cargo != Rel.OFFICER)) {
            inv.setItem(29, new ItemBuilder(Material.BEDROCK).setName("\u00a7aBase da Fac\u00e7\u00e3o").setLore("\u00a77Sua fac\u00e7\u00e3o ainda n\u00e3o definiu uma base.", "", "\u00a7fBot\u00e3o direito: \u00a77Definir base.").toItemStack());
        } else if (!faction.hasHome() && cargo != Rel.LEADER && cargo != Rel.OFFICER) {
            inv.setItem(29, new ItemBuilder(Material.BEDROCK).setName("\u00a7aBase da Fac\u00e7\u00e3o").setLore("\u00a77Sua fac\u00e7\u00e3o ainda n\u00e3o definiu uma base.").toItemStack());
        }
        p.openInventory(inv);
    }

    public void abrirMenuPlayerSemFaccao(Player p) {
        MPlayer mplayer = MPlayer.get(p);
        Faction factionclaim = BoardColl.get().getFactionAt(PS.valueOf((Location)p.getLocation()));
        int mortes = EngineKdr.getPlayerDeaths(mplayer);
        int kills = EngineKdr.getPlayerKills(mplayer);
        double playerpodermaximo = mplayer.getPowerMax();
        double playerpoder = mplayer.getPower();
        String kdr2f = EngineKdr.getPlayerKdr(mplayer);
        String playerpoder1f = String.format("%.1f", playerpoder);
        Inventory inv = Bukkit.createInventory(null, (int)45, (String)"\u00a78Sem fac\u00e7\u00e3o\u00a71\u00a72\u00a73");
        inv.setItem(10, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setSkullOwner(p.getName()).setName("\u00a77" + p.getName()).setLore("\u00a7fPoder: \u00a77" + playerpoder1f + "/" + playerpodermaximo, "\u00a7fCargo: \u00a77\u00a7oNenhum", "\u00a7fAbates: \u00a77" + kills, "\u00a7fMortes: \u00a77" + mortes, "\u00a7fKdr: \u00a77" + kdr2f).toItemStack());
        inv.setItem(14, new ItemBuilder(Heads.ROXO.clone()).setName("\u00a7eRanking das Fac\u00e7\u00f5es").setLore("\u00a77Clique para ver os rankings com as", "\u00a77fac\u00e7\u00f5es mais poderosas do servidor.").toItemStack());
        inv.setItem(15, new ItemBuilder(Heads.LARANJA.clone()).setName("\u00a7eFac\u00e7\u00f5es Online").setLore("\u00a77Clique para ver a lista de fac\u00e7\u00f5es", "\u00a77online no servidor.").toItemStack());
        inv.setItem(16, new ItemBuilder(Heads.AMARELO.clone()).setName("\u00a7eAjuda").setLore("\u00a77Todas as a\u00e7\u00f5es dispon\u00edveis neste menu", "\u00a77tamb\u00e9m podem ser realizadas por", "\u00a77comando. Utilize o comando '\u00a7f/f ajuda\u00a77'", "\u00a77para ver todos os comandos dispon\u00edveis.").toItemStack());
        inv.setItem(29, new ItemBuilder(Material.BANNER, 1, 15).setName("\u00a7aCriar fac\u00e7\u00e3o").setLore("\u00a77Crie j\u00e1 sua fac\u00e7\u00e3o usando", "\u00a77o comando '\u00a7f/f criar <tag> <nome>\u00a77'", "\u00a77Ex: \u00a7f/f criar MFC MinhaFaccao").toItemStack());
        inv.setItem(30, new ItemBuilder(Material.PAPER).setName("\u00a7eEntrar em uma Fac\u00e7\u00e3o").setLore("\u00a77Entre j\u00e1 em uma fac\u00e7\u00e3o usando", "\u00a77o comando '\u00a7f/f entrar <nome>\u00a77'", "", "\u00a77\u00a7oLembre-se, voc\u00ea precisa de", "\u00a77\u00a7oum convite para poder entrar.").toItemStack());
        if (mplayer.isMapAutoUpdating()) {
            inv.setItem(31, new ItemBuilder(Material.MAP).setName("\u00a7aMapa dos Territ\u00f3rios").setLore("\u00a77Voc\u00ea esta pisando em um territ\u00f3rio", "\u00a77protegido pela fac\u00e7\u00e3o \u00a7e" + factionclaim.getName(), "", "\u00a7fBot\u00e3o direito: \u00a77Mostra o mapa completo.", "\u00a7fBot\u00e3o esquerdo: \u00a77Desliga o mapa autom\u00e1tico.", "", "\u00a7fMapa autom\u00e1tico: \u00a7aLigado").addItemFlag(ItemFlag.HIDE_POTION_EFFECTS).toItemStack());
        } else {
            inv.setItem(31, new ItemBuilder(Material.EMPTY_MAP).setName("\u00a7aMapa dos Territ\u00f3rios").setLore("\u00a77Voc\u00ea esta pisando em um territ\u00f3rio", "\u00a77protegido pela fac\u00e7\u00e3o \u00a7e" + factionclaim.getName(), "", "\u00a7fBot\u00e3o direito: \u00a77Mostra o mapa completo.", "\u00a7fBot\u00e3o esquerdo: \u00a77Liga o mapa autom\u00e1tico.", "", "\u00a7fMapa autom\u00e1tico: \u00a7cDesligado").toItemStack());
        }
        if (mplayer.isTerritoryInfoTitles()) {
            inv.setItem(32, new ItemBuilder(Material.PAINTING).setName("\u00a7eTitulos dos Territ\u00f3rios").setLore("\u00a77Clique para alternar.", "", "\u00a7fStatus: \u00a7aAtivado").toItemStack());
        } else {
            inv.setItem(32, new ItemBuilder(Material.PAINTING).setName("\u00a7eTitulos dos Territ\u00f3rios").setLore("\u00a77Clique para alternar.", "", "\u00a7fStatus: \u00a7cDesativado").toItemStack());
        }
        if (mplayer.isSeeingChunk()) {
            inv.setItem(33, new ItemBuilder(Material.GRASS).setName("\u00a7aDelimita\u00e7\u00f5es das Terras").setLore("\u00a77Clique para alternar.", "", "\u00a7fStatus: \u00a7aAtivado").toItemStack());
        } else {
            inv.setItem(33, new ItemBuilder(Material.GRASS).setName("\u00a7aDelimita\u00e7\u00f5es das Terras").setLore("\u00a77Clique para alternar.", "", "\u00a7fStatus: \u00a7cDesativado").toItemStack());
        }
        p.openInventory(inv);
    }

    public static void abrirMenuFaccaoSobAtaque(Player p) {
        MPlayer mplayer = MPlayer.get(p);
        Faction faction = mplayer.getFaction();
        Inventory inv = Bukkit.createInventory(null, (int)54, (String)"\u00a78Terrenos sob ataque");
        Set chunks = EngineSobAtaque.underattack.keySet();
        int slot = 0;
        Iterator it = chunks.iterator();
        while (it.hasNext() && slot < 54) {
            Chunk c = (Chunk)it.next();
            Faction fac = BoardColl.get().getFactionAt(PS.valueOf((Chunk)c));
            if (fac.equals(faction)) {
                Location l = EngineSobAtaque.infoattack.get(c);
                inv.setItem(slot, new ItemBuilder(Material.GRASS).setName("\u00a7e\u00a7l#" + (slot + 1) + "\u00a7e Territ\u00f3rio sob ataque!").setLore("\u00a77Chunk: \u00a7fX:" + c.getX() + ", Z:" + c.getZ(), "\u00a77Mundo: \u00a7f" + c.getWorld().getName(), "\u00a77Coordenadas: \u00a7fX:" + l.getBlockX() + "\u00a78, \u00a7fY:" + l.getBlockY() + "\u00a78, \u00a7fZ:" + l.getBlockZ()).toItemStack());
            }
            ++slot;
        }
        p.openInventory(inv);
    }

    public void abrirMenuDesfazerFaccao(Player p) {
        MPlayer mplayer = MPlayer.get(p);
        Faction faction = mplayer.getFaction();
        String factionNome = faction.getName();
        Inventory inv = Bukkit.createInventory(null, (int)36, (String)"\u00a71\u00a72\u00a73\u00a78Desfazer fac\u00e7\u00e3o");
        inv.setItem(13, new ItemBuilder(Material.PAPER).setName("\u00a7fInforma\u00e7\u00f5es").setLore("\u00a77Voc\u00ea esta prestes a desfazer", "\u00a77totalmente a fac\u00e7\u00e3o \u00a7e" + factionNome + "\u00a77.").toItemStack());
        inv.setItem(20, new ItemBuilder(Material.WOOL, 1, DyeColor.LIME.getWoolData()).setName("\u00a7aConfirmar a\u00e7\u00e3o").setLore("\u00a77Clique para confirmar.").toItemStack());
        inv.setItem(24, new ItemBuilder(Material.WOOL, 1, DyeColor.RED.getWoolData()).setName("\u00a7cCancelar a\u00e7\u00e3o").setLore("\u00a77Clique para cancelar.").toItemStack());
        p.openInventory(inv);
    }

    public void abrirMenuAbandonarTerras(Player p) {
        MPlayer mplayer = MPlayer.get(p);
        Faction faction = mplayer.getFaction();
        int terras = faction.getLandCount();
        Inventory inv = Bukkit.createInventory(null, (int)36, (String)"\u00a78Abandonar todas as terras");
        inv.setItem(13, new ItemBuilder(Material.PAPER).setName("\u00a7fInforma\u00e7\u00f5es").setLore("\u00a77Voc\u00ea esta prestes a abandonar \u00a72" + terras + " \u00a77terras.").toItemStack());
        inv.setItem(20, new ItemBuilder(Material.WOOL, 1, DyeColor.LIME.getWoolData()).setName("\u00a7aConfirmar a\u00e7\u00e3o").setLore("\u00a77Clique para confirmar.").toItemStack());
        inv.setItem(24, new ItemBuilder(Material.WOOL, 1, DyeColor.RED.getWoolData()).setName("\u00a7cCancelar a\u00e7\u00e3o").setLore("\u00a77Clique para cancelar.").toItemStack());
        p.openInventory(inv);
    }

    public void abrirMenuConvite(Player p) {
        MPlayer mplayer = MPlayer.get(p);
        Faction faction = mplayer.getFaction();
        int nconvites = faction.getInvitations().size();
        Inventory inv = Bukkit.createInventory(null, (int)27, (String)"\u00a71\u00a72\u00a73\u00a78Gerenciar convites");
        inv.setItem(11, new ItemBuilder(Heads.AZURE.clone()).setName("\u00a7aEnviar convite").setLore("\u00a77Envie j\u00e1 um convite de fac\u00e7\u00e3o usando", "\u00a77o comando '\u00a7f/f convite enviar <nome>\u00a77'").toItemStack());
        if (nconvites == 0) {
            inv.setItem(15, new ItemBuilder(Material.PAPER).setName("\u00a7eGerenciar convites pendentes").setLore("\u00a7cSua fac\u00e7\u00e3o n\u00e3o possui convites pendentes.").setAmount(nconvites).toItemStack());
        } else {
            inv.setItem(15, new ItemBuilder(Material.PAPER).setName("\u00a7eGerenciar convites pendentes").setLore("\u00a7fSua fac\u00e7\u00e3o possui \u00a73" + nconvites + (nconvites == 1 ? " \u00a7fconvite pendente." : " \u00a7fconvites pendentes."), "\u00a7fClique para gerenciar" + (nconvites == 1 ? " \u00a7fo convite pendente." : " \u00a7fos convites pendentes.")).setAmount(nconvites).toItemStack());
        }
        p.openInventory(inv);
    }

    public void abrirMenuRelacoes(Player p) {
        MPlayer mplayer = MPlayer.get(p);
        Faction faction = mplayer.getFaction();
        int nrelations = faction.getRelationWishes().size() - EngineEditSource.getAliadosPendentesEnviados(faction).size();
        Inventory inv = Bukkit.createInventory(null, (int)27, (String)"\u00a78Gerenciar rela\u00e7\u00f5es");
        inv.setItem(11, new ItemBuilder(Heads.AZURE.clone()).setName("\u00a7aDefinir rela\u00e7\u00e3o").setLore("\u00a77Defina j\u00e1 uma rela\u00e7\u00e3o com alguma fac\u00e7\u00e3o usando", "\u00a77o comando '\u00a7f/f rela\u00e7\u00e3o definir <fac\u00e7\u00e3o>\u00a77'").toItemStack());
        inv.setItem(13, new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("\u00a7aAlian\u00e7as pendentes").setLore("\u00a7fClique para ver todos os convites", "\u00a7fde alian\u00e7a pendentes recebidos ou", "\u00a7fenviados pela sua fac\u00e7\u00e3o.").setLeatherArmorColor(Color.LIME).setAmount(EngineEditSource.getAliadosPendentesEnviados(faction).size() + EngineEditSource.getAliadosPendentesRecebidos(faction).size()).toItemStack());
        if (nrelations < 1) {
            inv.setItem(15, new ItemBuilder(Material.BOOK).setName("\u00a7eVer rela\u00e7\u00f5es").setLore("\u00a7cSua fac\u00e7\u00e3o n\u00e3o possui rela\u00e7\u00f5es definidas.").setAmount(nrelations).toItemStack());
        } else if (nrelations > 64) {
            inv.setItem(15, new ItemBuilder(Material.BOOK).setName("\u00a7eVer rela\u00e7\u00f5es").setLore("\u00a7cSua fac\u00e7\u00e3o n\u00e3o possui rela\u00e7\u00f5es definidas.").toItemStack());
        } else {
            inv.setItem(15, new ItemBuilder(Material.BOOK).setName("\u00a7eVer rela\u00e7\u00f5es").setLore("\u00a7fClique para ver a lista", "\u00a7fde rela\u00e7\u00f5es da sua fac\u00e7\u00e3o.").setAmount(nrelations).toItemStack());
        }
        p.openInventory(inv);
    }

    public void abrirMenuRelacoesPendentes(Player p) {
        MPlayer mplayer = MPlayer.get(p);
        Faction faction = mplayer.getFaction();
        int enviados = EngineEditSource.getAliadosPendentesEnviados(faction).size();
        int recebidos = EngineEditSource.getAliadosPendentesRecebidos(faction).size();
        int npendentes = enviados + recebidos;
        Inventory inv = Bukkit.createInventory(null, (int)36, (String)("\u00a78Alian\u00e7as pendentes (" + npendentes + ")"));
        if (recebidos > 0) {
            inv.setItem(11, new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("\u00a7bConvites de alian\u00e7a recebidos pendentes").setLore("\u00a7fSua fac\u00e7\u00e3o possui \u00a73" + recebidos + (recebidos == 1 ? " \u00a7fconvite" : " \u00a7fconvites") + (recebidos == 1 ? " recebido pendente." : " recebidos pendentes.")).setLeatherArmorColor(Color.AQUA).setAmount(recebidos).toItemStack());
        } else {
            inv.setItem(11, new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("\u00a7bConvites de alian\u00e7a recebidos pendentes").setLore("\u00a7cSua fac\u00e7\u00e3o n\u00e3o possui nenhum", "\u00a7cconvite recebido pendente.").setLeatherArmorColor(Color.AQUA).setAmount(0).toItemStack());
        }
        if (enviados > 0) {
            inv.setItem(15, new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("\u00a7aConvites de alian\u00e7a enviados pendentes").setLore("\u00a7fSua fac\u00e7\u00e3o possui \u00a73" + enviados + (enviados == 1 ? " \u00a7fconvite" : " \u00a7fconvites") + (enviados == 1 ? " enviado pendente." : "enviados pendentes.")).setLeatherArmorColor(Color.LIME).setAmount(enviados).toItemStack());
        } else {
            inv.setItem(15, new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("\u00a7aConvites de alian\u00e7a enviados pendentes").setLore("\u00a7cSua fac\u00e7\u00e3o n\u00e3o possui nenhum", "\u00a7cconvite enviado pendente.").setLeatherArmorColor(Color.LIME).setAmount(0).toItemStack());
        }
        p.openInventory(inv);
        inv.setItem(31, new ItemBuilder(Material.ARROW).setName("\u00a71\u00a72\u00a7cVoltar").toItemStack());
    }

    public void abrirMenuRelacoesPendentesEnviados(Player p) {
        MPlayer mplayer = MPlayer.get(p);
        Faction faction = mplayer.getFaction();
        int enviados = EngineEditSource.getAliadosPendentesEnviados(faction).size();
        int tamanho = 54;
        int flecha = 49;
        int slot = 11;
        if (enviados <= 5) {
            tamanho = 36;
            flecha = 31;
        } else if (enviados > 5 && enviados <= 10) {
            tamanho = 45;
            flecha = 40;
        }
        Inventory inv = Bukkit.createInventory(null, (int)tamanho, (String)"\u00a78Convites enviados pendentes");
        inv.setItem(flecha, new ItemBuilder(Material.ARROW).setName("\u00a71\u00a72\u00a7cVoltar").toItemStack());
        List<Faction> facs = EngineEditSource.getAliadosPendentesEnviados(faction);
        int i = 0;
        while (i < facs.size()) {
            Faction f = facs.get(i);
            String factionNome = f.getName();
            inv.setItem(slot, new ItemBuilder(Material.PAPER).setName("\u00a7eConvite para fac\u00e7\u00e3o " + factionNome).setLore("\u00a7fBot\u00e3o Direito: \u00a77Deletar convite", "\u00a7fShift + Bot\u00e3o direito: \u00a77Informa\u00e7\u00f5es da fac\u00e7\u00e3o").toItemStack());
            slot += slot == 15 || slot == 24 ? 5 : 1;
            ++i;
        }
        p.openInventory(inv);
    }

    public void abrirMenuRelacoesPendentesRecebidos(Player p) {
        MPlayer mplayer = MPlayer.get(p);
        Faction faction = mplayer.getFaction();
        int recebidos = EngineEditSource.getAliadosPendentesRecebidos(faction).size();
        int tamanho = 54;
        int flecha = 49;
        int slot = 11;
        if (recebidos <= 5) {
            tamanho = 36;
            flecha = 31;
        } else if (recebidos > 5 && recebidos <= 10) {
            tamanho = 45;
            flecha = 40;
        }
        Inventory inv = Bukkit.createInventory(null, (int)tamanho, (String)"\u00a78Convites recebidos pendentes");
        inv.setItem(flecha, new ItemBuilder(Material.ARROW).setName("\u00a71\u00a72\u00a7cVoltar").toItemStack());
        List<Faction> facs = EngineEditSource.getAliadosPendentesRecebidos(faction);
        int i = 0;
        while (i < facs.size()) {
            Faction f = facs.get(i);
            String factionNome = f.getName();
            inv.setItem(slot, new ItemBuilder(Material.PAPER).setName("\u00a7eConvite da fac\u00e7\u00e3o " + factionNome).setLore("\u00a7fBot\u00e3o Esquerdo: \u00a77Aceitar convite", "\u00a7fBot\u00e3o Direito: \u00a77Deletar convite", "\u00a7fShift + Bot\u00e3o direito: \u00a77Informa\u00e7\u00f5es da fac\u00e7\u00e3o").toItemStack());
            slot += slot == 15 || slot == 24 ? 5 : 1;
            ++i;
        }
        p.openInventory(inv);
    }

    public void abrirMenuRanking(Player p) {
        List<Faction> top = FactionColl.get().getTopFactions(45);
        int size = Math.max(9, ((top.size() / 9) + (top.size() % 9 == 0 ? 0 : 1)) * 9);
        size = Math.min(size, 54);
        Inventory inv = Bukkit.createInventory(null, size, "§8Ranking das Facções");
        String[] medals = {"§6#1", "§7#2", "§c#3"};
        for (int i = 0; i < top.size() && i < size; i++) {
            Faction f = top.get(i);
            String color = FactionColl.get().getRankColor(f);
            String display = f.hasTag() ? f.getTag() : f.getName();
            String rank = i < 3 ? medals[i] : ("§f#" + (i + 1));
            String leader = f.getLeader() == null ? "N/A" : f.getLeader().getName();
            double power = f.getPower();
            double powerMax = f.getPowerMax();
            int members = f.getMPlayers().size();
            int lands = f.getLandCount();
            ItemStack banner = new ItemStack(Material.BANNER, 1, (short)15);
            org.bukkit.inventory.meta.BannerMeta meta = (org.bukkit.inventory.meta.BannerMeta) banner.getItemMeta();
            if (i == 0) meta.setBaseColor(DyeColor.YELLOW);
            else if (i == 1) meta.setBaseColor(DyeColor.SILVER);
            else if (i == 2) meta.setBaseColor(DyeColor.ORANGE);
            else meta.setBaseColor(DyeColor.WHITE);
            meta.setDisplayName(rank + " " + color + "[" + display + "] §f" + f.getName());
            List<String> lore = new ArrayList<String>();
            lore.add("§7Líder: §f" + leader);
            lore.add("§7Poder: §f" + String.format("%.1f", power) + "§7/§f" + String.format("%.1f", powerMax));
            lore.add("§7Membros: §f" + members);
            lore.add("§7Terras: §f" + lands);
            meta.setLore(lore);
            banner.setItemMeta(meta);
            inv.setItem(i, banner);
        }
        p.openInventory(inv);
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void aoClickar(InventoryClickEvent e) {
        if (e.getSlotType() == InventoryType.SlotType.OUTSIDE || e.getCurrentItem() == null || e.getInventory().getType() != InventoryType.CHEST) {
            return;
        }
        Player p = (Player)e.getWhoClicked();
        String inventarioNome = e.getInventory().getName();
        int slot = e.getSlot();
        if (inventarioNome.equals("\u00a78Sem fac\u00e7\u00e3o\u00a71\u00a72\u00a73")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            MPlayer mp = MPlayer.get(p);
            if (slot == 14) {
                p.chat("/f top");
                return;
            }
            if (slot == 15) {
                p.performCommand("f listar");
                p.closeInventory();
                return;
            }
            if (slot == 16) {
                p.chat("/f ajuda");
                p.closeInventory();
                return;
            }
            if (slot == 29) {
                p.sendMessage("\u00a7f");
                p.sendMessage("\u00a7aCrie j\u00e1 a sua fac\u00e7\u00e3o usando o comando '\u00a7f/f criar <tag> <nome>\u00a7a'");
                p.sendMessage("\u00a7aExemplo: '\u00a7f/f criar MFC MinhaFaccao\u00a7a'");
                p.sendMessage("\u00a7f");
                p.closeInventory();
                return;
            }
            if (slot == 30) {
                p.sendMessage("\u00a7f");
                p.sendMessage("\u00a7aEntra j\u00e1 em uma fac\u00e7\u00e3o usando o comando '\u00a7f/f entrar <nome>\u00a7a'");
                p.sendMessage("\u00a7f");
                p.closeInventory();
                return;
            }
            if (slot == 31) {
                if (e.getClick().isRightClick()) {
                    p.performCommand("f mapa");
                    return;
                }
                if (e.getClick().isLeftClick()) {
                    if (mp.isMapAutoUpdating()) {
                        mp.setMapAutoUpdating(false);
                        this.abrirMenuPlayerSemFaccao(p);
                    } else {
                        mp.setMapAutoUpdating(true);
                        this.abrirMenuPlayerSemFaccao(p);
                    }
                    return;
                }
            } else {
                if (slot == 32) {
                    p.performCommand("f tt");
                    this.abrirMenuPlayerSemFaccao(p);
                    return;
                }
                if (slot == 33) {
                    p.performCommand("f sc");
                    this.abrirMenuPlayerSemFaccao(p);
                    return;
                }
            }
        } else if (inventarioNome.startsWith("\u00a7r\u00a78Fac\u00e7\u00e3o - ")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            MPlayer mp = MPlayer.get(p);
            Faction f = mp.getFaction();
            if (slot == 34) {
                if (EngineSobAtaque.factionattack.containsKey(f.getName())) {
                    EngineMenuGui.abrirMenuFaccaoSobAtaque(p);
                    return;
                }
            } else {
                if (slot == 14) {
                    p.chat("/f top");
                    return;
                }
                if (slot == 15) {
                    p.performCommand("f listar");
                    p.closeInventory();
                    return;
                }
                if (slot == 16) {
                    p.chat("/f ajuda");
                    p.closeInventory();
                    return;
                }
                if (slot == 28) {
                    p.performCommand("f sc");
                    this.abrirMenuPlayerComFaccao(p);
                    return;
                }
                if (slot == 29) {
                    if (!f.hasHome()) {
                        if ((mp.getRole() == Rel.LEADER || mp.getRole() == Rel.OFFICER || mp.isOverriding()) && e.getClick().isRightClick()) {
                            p.performCommand("f sethome");
                            this.abrirMenuPlayerComFaccao(p);
                            return;
                        }
                    } else {
                        if (e.getClick().isShiftClick() && (mp.getRole() == Rel.LEADER || mp.getRole() == Rel.OFFICER || mp.isOverriding())) {
                            p.performCommand("f delhome");
                            this.abrirMenuPlayerComFaccao(p);
                            return;
                        }
                        if (e.getClick().isRightClick() && (mp.getRole() == Rel.LEADER || mp.getRole() == Rel.OFFICER || mp.isOverriding())) {
                            p.performCommand("f sethome");
                            return;
                        }
                        if (e.getClick().isLeftClick()) {
                            p.performCommand("f home");
                            return;
                        }
                    }
                } else {
                    if (slot == 30) {
                        if (mp.getRole() == Rel.LEADER || mp.getRole() == Rel.OFFICER || mp.isOverriding()) {
                            p.chat("/f convite");
                        } else {
                            p.sendMessage("\u00a7cVoc\u00ea precisar ser capit\u00e3o ou superior para poder gerenciar os convites da fac\u00e7\u00e3o.");
                        }
                        return;
                    }
                    if (slot == 31) {
                        p.performCommand("f perm");
                        return;
                    }
                    if (slot == 32) {
                        p.performCommand("f voar");
                        this.abrirMenuPlayerComFaccao(p);
                        return;
                    }
                    if (slot == 37) {
                        p.performCommand("f tt");
                        this.abrirMenuPlayerComFaccao(p);
                        return;
                    }
                    if (slot == 38) {
                        if (e.getClick().isRightClick()) {
                            p.performCommand("f mapa");
                            return;
                        }
                        if (e.getClick().isLeftClick()) {
                            if (mp.isMapAutoUpdating()) {
                                mp.setMapAutoUpdating(false);
                                this.abrirMenuPlayerComFaccao(p);
                            } else {
                                mp.setMapAutoUpdating(true);
                                this.abrirMenuPlayerComFaccao(p);
                            }
                            return;
                        }
                    } else {
                        if (slot == 39) {
                            p.performCommand("f membros");
                            return;
                        }
                        if (slot == 40) {
                            if (mp.getRole() == Rel.LEADER || mp.getRole() == Rel.OFFICER || mp.isOverriding()) {
                                this.abrirMenuRelacoes(p);
                            } else {
                                p.sendMessage("\u00a7cVoc\u00ea precisar ser capit\u00e3o ou superior para poder gerenciar as rela\u00e7\u00f5es da fac\u00e7\u00e3o.");
                            }
                            return;
                        }
                        if (slot == 41) {
                            if (MConf.get().colocarIconeDoFBauNoMenuGUI) {
                                p.chat("/f bau");
                            } else if (MConf.get().colocarIconeDoFGeradoresNoMenuGUI) {
                                p.chat("/f geradores");
                            }
                            return;
                        }
                        if (slot == 43) {
                            if (mp.getRole() == Rel.LEADER || mp.isOverriding()) {
                                this.abrirMenuDesfazerFaccao(p);
                            } else {
                                p.performCommand("f sair");
                            }
                            return;
                        }
                    }
                }
            }
        } else if (inventarioNome.equals("\u00a71\u00a72\u00a73\u00a78Desfazer fac\u00e7\u00e3o")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            if (slot == 24) {
                p.sendMessage("\u00a7cA\u00e7\u00e3o cancelada com sucesso.");
                p.closeInventory();
                return;
            }
            if (slot == 20) {
                p.performCommand("f desfazer");
                p.closeInventory();
                return;
            }
        } else if (inventarioNome.equals("\u00a78Abandonar todas as terras")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            MPlayer mp = MPlayer.get(p);
            Faction f = mp.getFaction();
            String factionNome = f.getName();
            if (slot == 24) {
                p.sendMessage("\u00a7cA\u00e7\u00e3o cancelada com sucesso.");
                p.closeInventory();
                return;
            }
            if (slot == 20) {
                p.performCommand("f unclaim all all " + factionNome.replace(" ", ""));
                p.closeInventory();
                return;
            }
        } else if (inventarioNome.equals("\u00a71\u00a72\u00a73\u00a78Gerenciar convites")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            MPlayer mp = MPlayer.get(p);
            Faction f = mp.getFaction();
            if (slot == 11) {
                p.sendMessage("\u00a7f");
                p.sendMessage("\u00a7aEnvie j\u00e1 um convite de fac\u00e7\u00e3o para um player usando o comando '\u00a7f/f convite enviar <nome>\u00a7a'");
                p.sendMessage("\u00a7f");
                p.closeInventory();
                return;
            }
            if (slot == 15) {
                if (f.getInvitations().size() > 0) {
                    p.performCommand("f convite listar");
                } else {
                    p.chat("/f convite");
                }
                return;
            }
        } else if (inventarioNome.startsWith("\u00a71\u00a72\u00a73\u00a78Convites pendentes (")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            ItemStack item = e.getCurrentItem();
            if (item.getType() == Material.ARROW) {
                p.chat("/f convite");
                return;
            }
            if (item.getType() == Material.PAPER) {
                if (e.getClick().isRightClick()) {
                    int nomeTamanho = ((String)item.getItemMeta().getLore().get(0)).length();
                    String nome = ((String)item.getItemMeta().getLore().get(0)).substring(30, nomeTamanho);
                    p.performCommand("f convite del " + nome);
                    p.performCommand("f convite listar");
                } else if (e.getClick().isLeftClick()) {
                    int nomeTamanho = ((String)item.getItemMeta().getLore().get(0)).length();
                    String nome = ((String)item.getItemMeta().getLore().get(0)).substring(30, nomeTamanho);
                    p.performCommand("f perfil " + nome);
                    p.closeInventory();
                } else {
                    p.performCommand("f convite listar");
                }
                return;
            }
        } else if (inventarioNome.equals("\u00a78Gerenciar rela\u00e7\u00f5es")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            MPlayer mp = MPlayer.get(p);
            Faction f = mp.getFaction();
            if (slot == 11) {
                p.sendMessage("\u00a7f");
                p.sendMessage("\u00a7aDefina j\u00e1 alguma rela\u00e7\u00e3o com alguma fac\u00e7\u00e3o usando o comando '\u00a7f/f rela\u00e7\u00e3o definir <fac\u00e7\u00e3o>\u00a7e'");
                p.sendMessage("\u00a7f");
                p.closeInventory();
                return;
            }
            if (slot == 13) {
                this.abrirMenuRelacoesPendentes(p);
                return;
            }
            if (slot == 15) {
                if (f.getRelationWishes().size() - EngineEditSource.getAliadosPendentesEnviados(f).size() > 0) {
                    p.performCommand("f relacao listar");
                    p.closeInventory();
                }
                return;
            }
        } else {
            if (inventarioNome.startsWith("\u00a78Alian\u00e7as pendentes (")) {
                e.setCancelled(true);
                e.setResult(Event.Result.DENY);
                MPlayer mp = MPlayer.get(p);
                Faction f = mp.getFaction();
                if (slot == 15) {
                    if (EngineEditSource.getAliadosPendentesEnviados(f).size() > 0) {
                        this.abrirMenuRelacoesPendentesEnviados(p);
                    }
                    return;
                }
                if (slot == 11) {
                    if (EngineEditSource.getAliadosPendentesRecebidos(f).size() > 0) {
                        this.abrirMenuRelacoesPendentesRecebidos(p);
                    }
                    return;
                }
                if (slot == 31) {
                    this.abrirMenuRelacoes(p);
                }
                return;
            }
            if (inventarioNome.startsWith("\u00a78Convites enviados pendentes")) {
                e.setCancelled(true);
                e.setResult(Event.Result.DENY);
                MPlayer mp = MPlayer.get(p);
                Faction f = mp.getFaction();
                ItemStack item = e.getCurrentItem();
                if (item.getType() == Material.ARROW) {
                    this.abrirMenuRelacoesPendentes(p);
                    return;
                }
                if (item.getType() == Material.PAPER) {
                    if (e.getClick().isShiftClick()) {
                        int nomeTamanho = item.getItemMeta().getDisplayName().length();
                        String nome = item.getItemMeta().getDisplayName().substring(22, nomeTamanho);
                        p.performCommand("f info " + nome.replace(" ", ""));
                        p.closeInventory();
                    } else if (e.getClick().isRightClick()) {
                        int nomeTamanho = item.getItemMeta().getDisplayName().length();
                        String nome = item.getItemMeta().getDisplayName().substring(22, nomeTamanho);
                        Faction target = EngineEditSource.getFactionByName(nome);
                        f.setRelationWish(target, Rel.NEUTRAL);
                        this.abrirMenuRelacoesPendentesEnviados(p);
                    }
                    return;
                }
            } else if (inventarioNome.startsWith("\u00a78Convites recebidos pendentes")) {
                e.setCancelled(true);
                e.setResult(Event.Result.DENY);
                MPlayer mp = MPlayer.get(p);
                Faction f = mp.getFaction();
                String factionNome = f.getName();
                ItemStack item = e.getCurrentItem();
                if (item.getType() == Material.ARROW) {
                    this.abrirMenuRelacoesPendentes(p);
                    return;
                }
                if (item.getType() == Material.PAPER) {
                    if (e.getClick().isShiftClick()) {
                        int nomeTamanho = item.getItemMeta().getDisplayName().length();
                        String nome = item.getItemMeta().getDisplayName().substring(20, nomeTamanho);
                        p.performCommand("f info " + nome.replace(" ", ""));
                        p.closeInventory();
                    } else if (e.getClick().isLeftClick()) {
                        int nomeTamanho = item.getItemMeta().getDisplayName().length();
                        String nome = item.getItemMeta().getDisplayName().substring(20, nomeTamanho);
                        p.performCommand("f relacao definir ally " + nome.replace(" ", ""));
                        this.abrirMenuRelacoesPendentesRecebidos(p);
                    } else if (e.getClick().isRightClick()) {
                        int nomeTamanho = item.getItemMeta().getDisplayName().length();
                        String nome = item.getItemMeta().getDisplayName().substring(20, nomeTamanho);
                        Faction target = EngineEditSource.getFactionByName(nome);
                        target.setRelationWish(f, Rel.NEUTRAL);
                        if (target.getOnlinePlayers().size() > 0) {
                            target.msg("\u00a7eA fac\u00e7\u00e3o \u00a7f" + factionNome + "\u00a7e recusou seu pedido de alian\u00e7a.");
                        }
                        f.msg("\u00a7ePedido de alian\u00e7a da fac\u00e7\u00e3o \u00a7f" + target.getName() + "\u00a7e recusado.");
                        this.abrirMenuRelacoesPendentesRecebidos(p);
                    }
                    return;
                }
            } else {
                if (inventarioNome.startsWith("\u00a78Membros da ")) {
                    e.setCancelled(true);
                    e.setResult(Event.Result.DENY);
                    return;
                }
                if (inventarioNome.startsWith("\u00a78Terrenos sob ataque")) {
                    e.setCancelled(true);
                    e.setResult(Event.Result.DENY);
                    return;
                }
                if (inventarioNome.startsWith("\u00a78Rela\u00e7\u00e3o com ")) {
                    e.setCancelled(true);
                    e.setResult(Event.Result.DENY);
                    ItemStack item = e.getCurrentItem();
                    if (slot == 11) {
                        if (item.getItemMeta().getLore().size() == 2) {
                            int nomeTamanho = item.getItemMeta().getDisplayName().length();
                            String nome = item.getItemMeta().getDisplayName().substring(22, nomeTamanho);
                            p.performCommand("f relacao definir " + nome.replace(" ", "") + " ally");
                            p.closeInventory();
                        } else if (item.getItemMeta().getLore().size() > 2) {
                            this.abrirMenuRelacoesPendentes(p);
                        }
                        return;
                    }
                    if (slot == 13) {
                        if (item.getItemMeta().getLore().size() == 2) {
                            int nomeTamanho = item.getItemMeta().getDisplayName().length();
                            String nome = item.getItemMeta().getDisplayName().substring(27, nomeTamanho);
                            p.performCommand("f relacao definir " + nome.replace(" ", "") + " neutral");
                            p.closeInventory();
                        }
                        return;
                    }
                    if (slot == 15) {
                        if (item.getItemMeta().getLore().size() == 2) {
                            int nomeTamanho = item.getItemMeta().getDisplayName().length();
                            String nome = item.getItemMeta().getDisplayName().substring(25, nomeTamanho);
                            p.performCommand("f relacao definir " + nome.replace(" ", "") + " enemy");
                            p.closeInventory();
                        }
                        return;
                    }
                }
            }
        }
    }
}

