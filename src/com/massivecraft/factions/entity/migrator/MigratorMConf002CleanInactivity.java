/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.store.migrator.Migrator
 *  com.massivecraft.massivecore.store.migrator.MigratorFieldRename
 *  com.massivecraft.massivecore.store.migrator.MigratorRoot
 */
package com.massivecraft.factions.entity.migrator;

import com.massivecraft.factions.entity.MConf;
import com.massivecraft.massivecore.store.migrator.Migrator;
import com.massivecraft.massivecore.store.migrator.MigratorFieldRename;
import com.massivecraft.massivecore.store.migrator.MigratorRoot;

public class MigratorMConf002CleanInactivity
extends MigratorRoot {
    private static MigratorMConf002CleanInactivity i = new MigratorMConf002CleanInactivity();

    public static MigratorMConf002CleanInactivity get() {
        return i;
    }

    private MigratorMConf002CleanInactivity() {
        super(MConf.class);
        this.addInnerMigrator((Migrator)MigratorFieldRename.get((String)"removePlayerMillisDefault", (String)"playercleanToleranceMillis"));
        this.addInnerMigrator((Migrator)MigratorFieldRename.get((String)"removePlayerMillisPlayerAgeToBonus", (String)"playercleanToleranceMillisPlayerAgeToBonus"));
        this.addInnerMigrator((Migrator)MigratorFieldRename.get((String)"removePlayerMillisFactionAgeToBonus", (String)"playercleanToleranceMillisFactionAgeToBonus"));
    }
}

