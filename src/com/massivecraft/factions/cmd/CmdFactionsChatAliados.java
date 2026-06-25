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

import com.massivecraft.factions.Rel;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CmdFactionsChatAliados
extends FactionsCommand {
    private static CmdFactionsChatAliados i = new CmdFactionsChatAliados(){

        public List<String> getAliases() {
            return MConf.get().aliasesChatAliados;
        }
    };

    public static CmdFactionsChatAliados get() {
        return i;
    }

    public CmdFactionsChatAliados() {
        this.addRequirements(new Requirement[]{ReqHasFaction.get()});
        this.addParameter((Type)TypeNullable.get((Type)TypeString.get()), "mensagem", "erro", true);
        this.setVisibility(Visibility.INVISIBLE);
    }

    public void perform() {
        for (String msg : this.getArgs()) {
            Faction f = this.msender.getFaction();
            if (!this.argIsSet(0)) {
                this.msender.message("\u00a7cArgumentos insuficientes, use /a <mensagem>");
                return;
            }
            ArrayList<Faction> aliados = new ArrayList<Faction>();
            Set<String> relations = f.getRelationWishes().keySet();
            for (String id : relations) {
                Faction fac = Faction.get(id);
                if (fac == null || !fac.getRelationTo(f).equals((Object)Rel.ALLY)) continue;
                aliados.add(fac);
            }
            if (aliados.size() == 0) {
                this.msender.msg("\u00a7cSua fac\u00e7\u00e3o n\u00e3o possui aliados!");
                return;
            }
            for (Faction ally : aliados) {
                for (MPlayer mp : ally.getMPlayersWhereOnline(true)) {
                    mp.message(("\u00a7b[a] \u00a77[" + f.getName() + "\u00a77] \u00a7f" + this.msender.getRole().getPrefix() + this.msender.getName() + "\u00a7b: " + msg).replace('&', '\u00a7'));
                }
            }
            for (MPlayer mp : f.getMPlayersWhereOnline(true)) {
                mp.message(("\u00a7b[a] \u00a77[" + f.getName() + "\u00a77] \u00a7f" + this.msender.getRole().getPrefix() + this.msender.getName() + "\u00a7b: " + msg).replace('&', '\u00a7'));
            }
        }
    }
}

