/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.command.requirement.Requirement
 *  com.massivecraft.massivecore.command.requirement.RequirementIsPlayer
 *  com.massivecraft.massivecore.command.type.Type
 *  com.massivecraft.massivecore.command.type.primitive.TypeBooleanYes
 *  com.massivecraft.massivecore.mson.Mson
 *  com.massivecraft.massivecore.ps.PS
 *  com.massivecraft.massivecore.util.Txt
 *  org.bukkit.Location
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.Type;
import com.massivecraft.massivecore.command.type.primitive.TypeBooleanYes;
import com.massivecraft.massivecore.mson.Mson;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.Txt;
import java.util.List;
import org.bukkit.Location;

public class CmdFactionsMapa
extends FactionsCommand {
    public CmdFactionsMapa() {
        this.addAliases(new String[]{"map"});
        this.addParameter((Type)TypeBooleanYes.get(), "on/off", "uma vez");
        this.addRequirements(new Requirement[]{RequirementIsPlayer.get()});
        this.setDesc("\u00a76 mapa \u00a7e[on/off] \u00a78-\u00a77 Mostra o mapa dos territ\u00f3rios.");
    }

    public void perform() throws MassiveException {
        boolean argSet = this.argIsSet();
        boolean showMap = true;
        if (argSet) {
            showMap = this.adjustAutoUpdating();
        }
        if (!showMap) {
            return;
        }
        this.mostrarMap(19, 19);
    }

    private boolean adjustAutoUpdating() throws MassiveException {
        boolean autoUpdating = (Boolean)this.readArg(!this.msender.isMapAutoUpdating());
        this.msender.setMapAutoUpdating(autoUpdating);
        this.msg("\u00a7aMapa autom\u00e1tico %s\u00a7e.", new Object[]{Txt.parse((String)(autoUpdating ? "\u00a72habilitado" : "\u00a7cdesabilitado"))});
        return autoUpdating;
    }

    public void mostrarMap(int width, int height) {
        Location location = this.me.getLocation();
        List<Mson> message = BoardColl.get().getMap(this.msender.getPlayer(), this.msenderFaction, PS.valueOf((Location)location), location.getYaw(), width, height);
        this.message(message);
    }
}

