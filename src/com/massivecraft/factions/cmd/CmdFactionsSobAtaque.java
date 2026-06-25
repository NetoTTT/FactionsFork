/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.command.requirement.Requirement
 *  com.massivecraft.massivecore.command.requirement.RequirementIsPlayer
 *  org.bukkit.entity.Player
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.req.ReqHasFaction;
import com.massivecraft.factions.engine.EngineMenuGui;
import com.massivecraft.factions.engine.EngineSobAtaque;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import org.bukkit.entity.Player;

public class CmdFactionsSobAtaque
extends FactionsCommand {
    public CmdFactionsSobAtaque() {
        this.addAliases(new String[]{"ataque"});
        this.addRequirements(new Requirement[]{RequirementIsPlayer.get()});
        this.addRequirements(new Requirement[]{ReqHasFaction.get()});
        this.setDesc("\u00a76 sobataque \u00a78-\u00a77 Mostra mais informa\u00e7\u00f5es sobre o ataque.");
    }

    public void perform() throws MassiveException {
        Player p = this.msender.getPlayer();
        if (!EngineSobAtaque.factionattack.containsKey(this.msenderFaction.getName())) {
            this.msender.message("\u00a7cSua fac\u00e7\u00e3o n\u00e3o esta sob ataque!");
        } else {
            EngineMenuGui.abrirMenuFaccaoSobAtaque(p);
        }
    }
}

