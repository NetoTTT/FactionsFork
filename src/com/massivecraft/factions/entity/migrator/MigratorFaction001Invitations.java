/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.store.migrator.Migrator
 *  com.massivecraft.massivecore.store.migrator.MigratorFieldConvert
 *  com.massivecraft.massivecore.store.migrator.MigratorFieldRename
 *  com.massivecraft.massivecore.store.migrator.MigratorRoot
 *  com.massivecraft.massivecore.xlib.gson.JsonElement
 *  com.massivecraft.massivecore.xlib.gson.JsonObject
 */
package com.massivecraft.factions.entity.migrator;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.store.migrator.Migrator;
import com.massivecraft.massivecore.store.migrator.MigratorFieldConvert;
import com.massivecraft.massivecore.store.migrator.MigratorFieldRename;
import com.massivecraft.massivecore.store.migrator.MigratorRoot;
import com.massivecraft.massivecore.xlib.gson.JsonElement;
import com.massivecraft.massivecore.xlib.gson.JsonObject;

public class MigratorFaction001Invitations
extends MigratorRoot {
    private static MigratorFaction001Invitations i = new MigratorFaction001Invitations();

    public static MigratorFaction001Invitations get() {
        return i;
    }

    private MigratorFaction001Invitations() {
        super(Faction.class);
        this.addInnerMigrator((Migrator)MigratorFieldRename.get((String)"invitedPlayerIds", (String)"invitations"));
        this.addInnerMigrator((Migrator)new MigratorFaction001InvitationsField());
    }

    public class MigratorFaction001InvitationsField
    extends MigratorFieldConvert {
        private MigratorFaction001InvitationsField() {
            super("invitations");
        }

        public Object migrateInner(JsonElement idList) {
            JsonObject ret = new JsonObject();
            if (!idList.isJsonNull()) {
                if (!idList.isJsonArray()) {
                    throw new IllegalArgumentException(idList.toString());
                }
                for (JsonElement playerId : idList.getAsJsonArray()) {
                    String id = playerId.getAsString();
                    JsonObject invitation = new JsonObject();
                    ret.add(id, (JsonElement)invitation);
                }
            }
            return ret;
        }
    }
}

