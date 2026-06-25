/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.command.requirement.Requirement
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.CmdFactionsConviteAdd;
import com.massivecraft.factions.cmd.CmdFactionsConviteDel;
import com.massivecraft.factions.cmd.CmdFactionsConviteListar;
import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.req.ReqHasFaction;
import com.massivecraft.massivecore.command.requirement.Requirement;

public class CmdFactionsConvite
extends FactionsCommand {
    public CmdFactionsConviteAdd cmdFactionsConviteAdd;
    public CmdFactionsConviteDel cmdFactionsConviteDel;
    public CmdFactionsConviteListar cmdFactionsConviteListar;

    public CmdFactionsConvite() {
        this.addAliases(new String[]{"convidar", "i", "adicionar", "invite"});
        this.setDesc("\u00a76 convite \u00a78-\u00a77 Gerencia os convites da fac\u00e7\u00e3o.");
        this.addRequirements(new Requirement[]{ReqHasFaction.get()});
        this.cmdFactionsConviteAdd = new CmdFactionsConviteAdd();
        this.cmdFactionsConviteDel = new CmdFactionsConviteDel();
        this.cmdFactionsConviteListar = new CmdFactionsConviteListar();
    }
}

