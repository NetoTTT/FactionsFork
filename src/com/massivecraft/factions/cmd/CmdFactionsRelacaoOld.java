/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.command.Visibility
 *  com.massivecraft.massivecore.command.type.Type
 *  com.massivecraft.massivecore.util.MUtil
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.cmd.CmdFactions;
import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.Visibility;
import com.massivecraft.massivecore.command.type.Type;
import com.massivecraft.massivecore.util.MUtil;

public class CmdFactionsRelacaoOld
extends FactionsCommand {
    public final String relName;

    public CmdFactionsRelacaoOld(String rel) {
        this.relName = rel.toLowerCase();
        this.setSetupEnabled(false);
        this.addAliases(new String[]{this.relName});
        this.addParameter((Type)TypeFaction.get(), "fac\u00e7\u00e3o", true);
        this.setVisibility(Visibility.INVISIBLE);
    }

    public void perform() throws MassiveException {
        Faction faction = (Faction)this.readArg();
        if (this.msender.getRole() != Rel.LEADER && this.msender.getRole() != Rel.OFFICER && !this.msender.isOverriding()) {
            this.msender.message("\u00a7cVoc\u00ea precisar ser capit\u00e3o ou superior para poder gerenciar as rela\u00e7\u00f5es da fac\u00e7\u00e3o.");
            return;
        }
        CmdFactions.get().cmdFactionsRelacao.cmdFactionsRelacaoDefinir.execute(this.sender, MUtil.list(faction.getId(), this.relName));
    }
}

