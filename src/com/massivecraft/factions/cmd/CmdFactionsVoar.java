/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.command.requirement.Requirement
 *  com.massivecraft.massivecore.command.requirement.RequirementIsPlayer
 *  com.massivecraft.massivecore.command.type.Type
 *  com.massivecraft.massivecore.command.type.primitive.TypeBooleanYes
 *  com.massivecraft.massivecore.ps.PS
 *  com.massivecraft.massivecore.util.Txt
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.req.ReqHasFaction;
import com.massivecraft.factions.engine.EngineSobAtaque;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.Type;
import com.massivecraft.massivecore.command.type.primitive.TypeBooleanYes;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.Txt;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CmdFactionsVoar
extends FactionsCommand {
    public CmdFactionsVoar() {
        this.addAliases(new String[]{"fly"});
        this.addParameter((Type)TypeBooleanYes.get(), "on/off", "uma vez");
        this.addRequirements(new Requirement[]{RequirementIsPlayer.get()});
        this.addRequirements(new Requirement[]{ReqHasFaction.get()});
        this.setDesc("\u00a76 voar \u00a7e[on/off] \u00a78-\u00a77 Habilita o fly nos territ\u00f3rios da fac\u00e7\u00e3o.");
    }

    public void perform() throws MassiveException {
        Player p = this.msender.getPlayer();
        PS ps = PS.valueOf((Location)p.getLocation());
        if (!this.msender.isOverriding() && !BoardColl.get().getFactionAt(ps).equals(this.msenderFaction)) {
            this.msg("\u00a7cVoc\u00ea n\u00e3o pode habilitar o modo voar fora dos territ\u00f3rios da sua fac\u00e7\u00e3o.");
            return;
        }
        if (EngineSobAtaque.factionattack.containsKey(this.msenderFaction.getName())) {
            this.msg("\u00a7cVoc\u00ea n\u00e3o pode habilitar o modo voar enquanto sua fac\u00e7\u00e3o estiver sob ataque.");
            return;
        }
        boolean old = p.getAllowFlight();
        boolean target = (Boolean)this.readArg(!old);
        String targetDesc = Txt.parse((String)(target ? "\u00a72ativado" : "\u00a7cdesativado"));
        if (target == old) {
            this.msg("\u00a7aO modo voar j\u00e1 est\u00e1 %s\u00a7a.", new Object[]{targetDesc});
            return;
        }
        p.setAllowFlight(target);
        this.msg("\u00a7aModo voar %s\u00a7a.", new Object[]{targetDesc});
    }
}

