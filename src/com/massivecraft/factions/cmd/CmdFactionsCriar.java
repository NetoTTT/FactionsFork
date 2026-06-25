package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.req.ReqHasntFaction;
import com.massivecraft.factions.cmd.type.TypeFactionNameStrict;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.event.EventFactionsCreate;
import com.massivecraft.factions.event.EventFactionsMembershipChange;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.type.Type;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.store.MStore;

public class CmdFactionsCriar
extends FactionsCommand {

    private static final int TAG_MAX = 4;
    private static final int TAG_MIN = 2;

    public CmdFactionsCriar() {
        this.addAliases(new String[]{"new", "create"});
        this.addParameter((Type)TypeString.get(), "tag");
        this.addParameter((Type)TypeFactionNameStrict.get(), "nome");
        this.addRequirements(new Requirement[]{ReqHasntFaction.get()});
        this.setDesc("§6 criar §e<tag> <nome> §8-§7 Cria uma nova facção.");
    }

    public void perform() throws MassiveException {
        String newTag  = (String)this.readArg();
        String newName = (String)this.readArg();

        if (newTag.length() < TAG_MIN || newTag.length() > TAG_MAX) {
            this.msg("§cA tag deve ter entre §f" + TAG_MIN + "§c e §f" + TAG_MAX + "§c caracteres.");
            return;
        }
        if (!newTag.matches("[a-zA-Z0-9]+")) {
            this.msg("§cA tag só pode conter letras e números.");
            return;
        }
        if (FactionColl.get().isTagTaken(newTag)) {
            this.msg("§cA tag §f[" + newTag.toUpperCase() + "]§c já está em uso por outra facção.");
            return;
        }

        String factionId = MStore.createId();
        EventFactionsCreate createEvent = new EventFactionsCreate(this.sender, factionId, newName);
        createEvent.run();
        if (createEvent.isCancelled()) {
            return;
        }
        Faction faction = (Faction)FactionColl.get().create(factionId);
        faction.setName(newName);
        faction.setTag(newTag);
        this.msender.setRole(Rel.LEADER);
        this.msender.setFaction(faction);
        EventFactionsMembershipChange joinEvent = new EventFactionsMembershipChange(this.sender, this.msender, faction, EventFactionsMembershipChange.MembershipChangeReason.CREATE);
        joinEvent.run();
        this.msg("§aFacção §f%s §a[§f%s§a] criada com sucesso!", new Object[]{newName, newTag.toUpperCase()});
        this.setDefaultPermissions(faction);
    }

    private void setDefaultPermissions(Faction faction) {
        faction.getPermitted(MPerm.getPermBuild()).remove((Object)Rel.ALLY);
        faction.getPermitted(MPerm.getPermBuild()).remove((Object)Rel.ENEMY);
        faction.getPermitted(MPerm.getPermBuild()).remove((Object)Rel.TRUCE);
        faction.getPermitted(MPerm.getPermBuild()).remove((Object)Rel.NEUTRAL);
        faction.getPermitted(MPerm.getPermBuild()).remove((Object)Rel.RECRUIT);
        faction.getPermitted(MPerm.getPermContainer()).remove((Object)Rel.ALLY);
        faction.getPermitted(MPerm.getPermContainer()).remove((Object)Rel.ENEMY);
        faction.getPermitted(MPerm.getPermContainer()).remove((Object)Rel.TRUCE);
        faction.getPermitted(MPerm.getPermContainer()).remove((Object)Rel.NEUTRAL);
        faction.getPermitted(MPerm.getPermContainer()).remove((Object)Rel.RECRUIT);
        faction.getPermitted(MPerm.getPermHome()).remove((Object)Rel.ALLY);
        faction.getPermitted(MPerm.getPermHome()).remove((Object)Rel.ENEMY);
        faction.getPermitted(MPerm.getPermHome()).remove((Object)Rel.TRUCE);
        faction.getPermitted(MPerm.getPermHome()).remove((Object)Rel.NEUTRAL);
        faction.getPermitted(MPerm.getPermHome()).add(Rel.RECRUIT);
        faction.getPermitted(MPerm.getPermDoor()).add(Rel.RECRUIT);
        faction.getPermitted(MPerm.getPermDoor()).remove((Object)Rel.ENEMY);
        faction.getPermitted(MPerm.getPermDoor()).remove((Object)Rel.TRUCE);
        faction.getPermitted(MPerm.getPermDoor()).remove((Object)Rel.NEUTRAL);
        faction.getPermitted(MPerm.getPermButton()).remove((Object)Rel.ENEMY);
        faction.getPermitted(MPerm.getPermButton()).remove((Object)Rel.TRUCE);
        faction.getPermitted(MPerm.getPermButton()).remove((Object)Rel.NEUTRAL);
        faction.getPermitted(MPerm.getPermLever()).remove((Object)Rel.ENEMY);
        faction.getPermitted(MPerm.getPermLever()).remove((Object)Rel.TRUCE);
        faction.getPermitted(MPerm.getPermLever()).remove((Object)Rel.NEUTRAL);
    }
}
