/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.PriorityLines
 *  com.massivecraft.massivecore.command.type.Type
 *  com.massivecraft.massivecore.mixin.MixinMessage
 *  com.massivecraft.massivecore.util.Txt
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandSender
 *  org.bukkit.plugin.Plugin
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.event.EventFactionsFactionShowAsync;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.PriorityLines;
import com.massivecraft.massivecore.command.type.Type;
import com.massivecraft.massivecore.mixin.MixinMessage;
import com.massivecraft.massivecore.util.Txt;
import java.util.Collection;
import java.util.TreeSet;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class CmdFactionsInfo
extends FactionsCommand {
    public CmdFactionsInfo() {
        this.addAliases(new String[]{"f", "show", "ver", "faction"});
        this.addParameter((Type)TypeFaction.get(), "fac\u00e7\u00e3o", "voc\u00ea");
        this.setDesc("\u00a76 f,info \u00a7e<fac\u00e7\u00e3o> \u00a78-\u00a77 Mostra as informa\u00e7\u00f5es da fac\u00e7\u00e3o.");
    }

    public void perform() throws MassiveException {
        final Faction faction = (Faction)this.readArg(this.msenderFaction);
        final CommandSender sender = this.sender;
        Bukkit.getScheduler().runTaskAsynchronously((Plugin)Factions.get(), new Runnable(){

            @Override
            public void run() {
                EventFactionsFactionShowAsync event = new EventFactionsFactionShowAsync(sender, faction);
                event.run();
                if (event.isCancelled()) {
                    return;
                }
                MixinMessage.get().messageOne((Object)sender, (Object)Txt.titleize((Object)("\u00a7eFac\u00e7\u00e3o " + faction.getName(CmdFactionsInfo.this.msender))));
                TreeSet<PriorityLines> priorityLiness = new TreeSet<PriorityLines>(event.getIdPriorityLiness().values());
                for (PriorityLines priorityLines : priorityLiness) {
                    MixinMessage.get().messageOne((Object)sender, (Collection)priorityLines.getLines());
                }
            }
        });
    }
}

