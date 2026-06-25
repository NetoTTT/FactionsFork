/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.command.requirement.Requirement
 *  com.massivecraft.massivecore.command.requirement.RequirementIsPlayer
 *  com.massivecraft.massivecore.command.type.Type
 *  com.massivecraft.massivecore.command.type.primitive.TypeBooleanOn
 *  com.massivecraft.massivecore.util.Txt
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.Type;
import com.massivecraft.massivecore.command.type.primitive.TypeBooleanOn;
import com.massivecraft.massivecore.util.Txt;

public class CmdFactionsVerTerras
extends FactionsCommand {
    public CmdFactionsVerTerras() {
        this.addAliases(new String[]{"sc"});
        this.addParameter((Type)TypeBooleanOn.get(), "mostrar", "esconder");
        this.addRequirements(new Requirement[]{RequirementIsPlayer.get()});
        this.setDesc("\u00a76 sc,verterras \u00a78-\u00a77 Mostra as delimita\u00e7\u00f5es das terras.");
    }

    public void perform() throws MassiveException {
        boolean old = this.msender.isSeeingChunk();
        boolean target = (Boolean)this.readArg(!old);
        String targetDesc = Txt.parse((String)(target ? "\u00a72ativada" : "\u00a7cdesativada"));
        if (target == old) {
            this.msg("\u00a7aA visualiza\u00e7\u00e3o das delimita\u00e7\u00f5es das terras j\u00e1 est\u00e1 %s\u00a7a.", new Object[]{targetDesc});
            return;
        }
        this.msender.setSeeingChunk(target);
        this.msg("\u00a7aVisualiza\u00e7\u00e3o das delimita\u00e7\u00f5es das terras %s\u00a7a.", new Object[]{targetDesc});
    }
}

