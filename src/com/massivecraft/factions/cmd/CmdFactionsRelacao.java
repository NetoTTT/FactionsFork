/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.command.requirement.Requirement
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.CmdFactionsRelacaoDefinir;
import com.massivecraft.factions.cmd.CmdFactionsRelacaoListar;
import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.req.ReqHasFaction;
import com.massivecraft.massivecore.command.requirement.Requirement;

public class CmdFactionsRelacao
extends FactionsCommand {
    public CmdFactionsRelacaoDefinir cmdFactionsRelacaoDefinir;
    public CmdFactionsRelacaoListar cmdFactionsRelacaoListar;

    public CmdFactionsRelacao() {
        this.addAliases(new String[]{"rela\u00e7\u00e3o", "relation", "rel"});
        this.addRequirements(new Requirement[]{ReqHasFaction.get()});
        this.setDesc("\u00a76 relacao \u00a78-\u00a77 Gerencia as rela\u00e7\u00f5es da fac\u00e7\u00e3o.");
        this.cmdFactionsRelacaoDefinir = new CmdFactionsRelacaoDefinir();
        this.cmdFactionsRelacaoListar = new CmdFactionsRelacaoListar();
    }
}

