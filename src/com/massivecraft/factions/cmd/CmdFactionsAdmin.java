/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.command.Visibility
 *  com.massivecraft.massivecore.command.type.Type
 *  com.massivecraft.massivecore.command.type.primitive.TypeBooleanYes
 *  com.massivecraft.massivecore.util.Txt
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.Visibility;
import com.massivecraft.massivecore.command.type.Type;
import com.massivecraft.massivecore.command.type.primitive.TypeBooleanYes;
import com.massivecraft.massivecore.util.Txt;

public class CmdFactionsAdmin
extends FactionsCommand {
    public CmdFactionsAdmin() {
        this.addAliases(new String[]{"adm"});
        this.addParameter((Type)TypeBooleanYes.get(), "on/off", "flip");
        this.setDesc("\u00a76 admin \u00a7e[on/off] \u00a78-\u00a77 Entra e sai do modo admin.");
        this.setVisibility(Visibility.SECRET);
    }

    public void perform() throws MassiveException {
        boolean old = this.msender.isOverriding();
        boolean target = (Boolean)this.readArg(!old);
        String desc = Txt.parse((String)(target ? "\u00a72habilitado" : "\u00a7cdesabilitado"));
        if (target == old) {
            this.msg("\u00a7aVoc\u00ea j\u00e1 est\u00e1 com o modo admin %s\u00a7a.", new Object[]{desc});
            return;
        }
        this.msender.setOverriding(target);
        this.msender.msg(Txt.parse((String)("\u00a7aModo admin " + desc + "\u00a7a.")));
    }
}

