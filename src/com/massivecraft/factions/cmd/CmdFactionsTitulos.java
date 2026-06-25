/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.command.Visibility
 *  com.massivecraft.massivecore.command.requirement.Requirement
 *  com.massivecraft.massivecore.command.requirement.RequirementIsPlayer
 *  com.massivecraft.massivecore.command.requirement.RequirementTitlesAvailable
 *  com.massivecraft.massivecore.command.type.Type
 *  com.massivecraft.massivecore.command.type.primitive.TypeBooleanOn
 *  com.massivecraft.massivecore.mixin.MixinTitle
 *  com.massivecraft.massivecore.util.Txt
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.Visibility;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.requirement.RequirementTitlesAvailable;
import com.massivecraft.massivecore.command.type.Type;
import com.massivecraft.massivecore.command.type.primitive.TypeBooleanOn;
import com.massivecraft.massivecore.mixin.MixinTitle;
import com.massivecraft.massivecore.util.Txt;

public class CmdFactionsTitulos
extends FactionsCommand {
    public CmdFactionsTitulos() {
        this.addAliases(new String[]{"tt", "territorytitles"});
        this.addParameter((Type)TypeBooleanOn.get(), "on|off", "toggle");
        this.addRequirements(new Requirement[]{RequirementIsPlayer.get()});
        this.addRequirements(new Requirement[]{RequirementTitlesAvailable.get()});
        this.setDesc("\u00a76 tt,titulos \u00a78-\u00a77 Mostra os titulos dos territ\u00f3rio.");
    }

    public Visibility getVisibility() {
        if (!MixinTitle.get().isAvailable()) {
            return Visibility.INVISIBLE;
        }
        return super.getVisibility();
    }

    public void perform() throws MassiveException {
        boolean before = this.msender.isTerritoryInfoTitles();
        boolean after = (Boolean)this.readArg(!before);
        String desc = Txt.parse((String)(after ? "\u00a72ativada" : "\u00a7cdesativada"));
        if (after == before) {
            this.msg("\u00a7aA visualiza\u00e7\u00e3o dos titulos dos territ\u00f3rios j\u00e1 est\u00e1 %s\u00a7a.", new Object[]{desc});
            return;
        }
        this.msender.setTerritoryInfoTitles(after);
        this.msg("\u00a7aVisualiza\u00e7\u00e3o dos titulos dos territ\u00f3rios %s\u00a7a.", new Object[]{desc});
    }
}

