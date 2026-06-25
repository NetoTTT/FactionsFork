/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.command.MassiveCommand
 *  com.massivecraft.massivecore.command.Parameter
 *  com.massivecraft.massivecore.pager.Pager
 *  com.massivecraft.massivecore.pager.Stringifier
 *  com.massivecraft.massivecore.util.Txt
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandSender
 *  org.bukkit.plugin.Plugin
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.comparator.ComparatorFactionList;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.MassiveCommand;
import com.massivecraft.massivecore.command.Parameter;
import com.massivecraft.massivecore.pager.Pager;
import com.massivecraft.massivecore.pager.Stringifier;
import com.massivecraft.massivecore.util.Txt;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class CmdFactionsListar
extends FactionsCommand {
    public CmdFactionsListar() {
        this.addAliases(new String[]{"l", "list"});
        this.addParameter(Parameter.getPage());
        this.setDesc("\u00a76 l,listar \u00a7e[p\u00e1gina] \u00a78-\u00a77 Mostra a lista de fac\u00e7\u00f5es.");
    }

    public void perform() throws MassiveException {
        int page = (Integer)this.readArg();
        final CommandSender sender = this.sender;
        final MPlayer msender = this.msender;
        final Pager pager = new Pager((MassiveCommand)this, "\u00a7eLista de Fac\u00e7\u00f5es", Integer.valueOf(page), (Stringifier)new Stringifier<Faction>(){

            public String toString(Faction faction, int index) {
                if (faction.isNone()) {
                    return Txt.parse((String)"\u00a72Sem fac\u00e7\u00e3o\u00a7e %d online", (Object[])new Object[]{FactionColl.get().getNone().getMPlayersWhereOnlineTo(sender).size()});
                }
                return Txt.parse((String)"%s\u00a7e: Online %d/%d, Terras %d, Poder %d/%d", (Object[])new Object[]{faction.getName(msender), faction.getMPlayersWhereOnlineTo(sender).size(), faction.getMPlayers().size(), faction.getLandCount(), faction.getPowerRounded(), faction.getPowerMaxRounded()});
            }
        });
        Bukkit.getScheduler().runTaskAsynchronously((Plugin)Factions.get(), new Runnable(){

            @Override
            public void run() {
                List factions = FactionColl.get().getAll((Comparator)((Object)ComparatorFactionList.get(sender)));
                pager.setItems((Collection)factions);
                pager.message();
            }
        });
    }
}

