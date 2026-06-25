/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.command.Visibility
 *  com.massivecraft.massivecore.command.requirement.Requirement
 *  com.massivecraft.massivecore.command.type.Type
 *  com.massivecraft.massivecore.command.type.TypeNullable
 *  com.massivecraft.massivecore.command.type.primitive.TypeString
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.req.ReqHasFaction;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.command.Visibility;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.type.Type;
import com.massivecraft.massivecore.command.type.TypeNullable;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import java.util.List;

public class CmdFactionsChatFaccao
extends FactionsCommand {
    private static CmdFactionsChatFaccao i = new CmdFactionsChatFaccao(){

        public List<String> getAliases() {
            return MConf.get().aliasesChatFaccao;
        }
    };

    public static CmdFactionsChatFaccao get() {
        return i;
    }

    public CmdFactionsChatFaccao() {
        this.addRequirements(new Requirement[]{ReqHasFaction.get()});
        this.addParameter((Type)TypeNullable.get((Type)TypeString.get()), "mensagem", "erro", true);
        this.setVisibility(Visibility.INVISIBLE);
    }

    public void perform() {
        for (String msg : this.getArgs()) {
            Faction f = this.msender.getFaction();
            if (!this.argIsSet(0)) {
                this.msender.message("\u00a7cArgumentos insuficientes, use /. <mensagem>");
                return;
            }
            for (MPlayer mp : f.getMPlayersWhereOnline(true)) {
                mp.message(("\u00a7a[f] \u00a7f" + this.msender.getRole().getPrefix() + this.msender.getName() + "\u00a7a: " + msg).replace('&', '\u00a7'));
            }
        }
    }
}

