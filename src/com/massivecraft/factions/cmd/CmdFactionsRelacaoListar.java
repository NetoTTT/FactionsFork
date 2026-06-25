/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.collections.MassiveList
 *  com.massivecraft.massivecore.collections.MassiveSet
 *  com.massivecraft.massivecore.command.MassiveCommand
 *  com.massivecraft.massivecore.command.Parameter
 *  com.massivecraft.massivecore.command.type.Type
 *  com.massivecraft.massivecore.command.type.container.TypeSet
 *  com.massivecraft.massivecore.pager.Pager
 *  com.massivecraft.massivecore.pager.Stringifier
 *  com.massivecraft.massivecore.util.Txt
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.cmd.type.TypeRelation;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.collections.MassiveSet;
import com.massivecraft.massivecore.command.MassiveCommand;
import com.massivecraft.massivecore.command.Parameter;
import com.massivecraft.massivecore.command.type.Type;
import com.massivecraft.massivecore.command.type.container.TypeSet;
import com.massivecraft.massivecore.pager.Pager;
import com.massivecraft.massivecore.pager.Stringifier;
import com.massivecraft.massivecore.util.Txt;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class CmdFactionsRelacaoListar
extends FactionsCommand {
    public static final Set<Rel> RELEVANT_RELATIONS = new MassiveSet((Object[])new Rel[]{Rel.ALLY, Rel.ENEMY});
    public static final String SEPERATOR = Txt.parse((String)"\u00a7f: ");

    public CmdFactionsRelacaoListar() {
        this.addAliases(new String[]{"list", "lista"});
        this.setDesc("\u00a76 relacao listar \u00a7e<fac\u00e7\u00e3o> \u00a78-\u00a77 Mostra a lista de rela\u00e7\u00f5es.");
        this.addParameter(Parameter.getPage());
        this.addParameter((Type)TypeFaction.get(), "fac\u00e7\u00e3o", "voc\u00ea");
        this.addParameter((Type)TypeSet.get((Type)TypeRelation.get()), "rela\u00e7\u00e3o", "all");
    }

    public void perform() throws MassiveException {
        int page = (Integer)this.readArg();
        final Faction faction = (Faction)this.readArg(this.msenderFaction);
        @SuppressWarnings("unchecked") final Set<Rel> relations = (Set<Rel>)this.readArg(RELEVANT_RELATIONS);
        final Pager pager = new Pager((MassiveCommand)this, "", Integer.valueOf(page), (Stringifier)new Stringifier<String>(){

            public String toString(String item, int index) {
                return item;
            }
        });
        Bukkit.getScheduler().runTaskAsynchronously((Plugin)Factions.get(), new Runnable(){

            @Override
            public void run() {
                MassiveList relNames = new MassiveList();
                for (Map.Entry<Rel, List<String>> entry : FactionColl.get().getRelationNames(faction, relations).entrySet()) {
                    Rel relation = entry.getKey();
                    String coloredName = String.valueOf(relation.getColor().toString()) + relation.getName();
                    for (String name : entry.getValue()) {
                        relNames.add(String.valueOf(coloredName) + SEPERATOR + name);
                    }
                }
                pager.setTitle(Txt.parse((String)"\u00a7eRela\u00e7\u00f5es da %s \u00a72(%d)", (Object[])new Object[]{faction.getName(), relNames.size()}));
                pager.setItems((Collection)relNames);
                pager.message();
            }
        });
    }
}

