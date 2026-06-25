package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.req.ReqHasFaction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.type.Type;
import com.massivecraft.massivecore.command.type.primitive.TypeString;

public class CmdFactionsTag
extends FactionsCommand {

    private static final int TAG_MAX = 4;
    private static final int TAG_MIN = 2;

    public CmdFactionsTag() {
        this.addParameter((Type)TypeString.get(), "tag");
        this.addAliases(new String[]{"tag", "sigla"});
        this.addRequirements(new Requirement[]{ReqHasFaction.get()});
        this.setDesc("§6 tag §e<tag> §8-§7 Altera a tag da facção.");
    }

    public void perform() throws MassiveException {
        if (this.msender.getRole() != Rel.LEADER && !this.msender.isOverriding()) {
            this.msender.message("§cApenas o líder da facção pode alterar a tag.");
            return;
        }

        String newTag = (String)this.readArg();

        if (newTag.length() < TAG_MIN || newTag.length() > TAG_MAX) {
            this.msg("§cA tag deve ter entre §f" + TAG_MIN + "§c e §f" + TAG_MAX + "§c caracteres.");
            return;
        }
        if (!newTag.matches("[a-zA-Z0-9]+")) {
            this.msg("§cA tag só pode conter letras e números.");
            return;
        }
        if (FactionColl.get().isTagTaken(newTag)) {
            String currentTag = this.msenderFaction.getTag();
            if (currentTag == null || !currentTag.equalsIgnoreCase(newTag)) {
                this.msg("§cA tag §f[" + newTag.toUpperCase() + "]§c já está em uso por outra facção.");
                return;
            }
        }

        String oldTag = this.msenderFaction.hasTag() ? this.msenderFaction.getTag() : this.msenderFaction.getName();
        this.msenderFaction.setTag(newTag);
        this.msenderFaction.msg("§e%s§e alterou a tag da facção de §f[%s]§e para §f[%s]§e.",
            String.valueOf(this.msender.getRole().getPrefix()) + this.msender.getName(),
            oldTag, newTag.toUpperCase());
    }
}
