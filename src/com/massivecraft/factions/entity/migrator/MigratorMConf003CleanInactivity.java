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

public class MigratorMConf003CleanInactivity
extends MigratorRoot {
    private static MigratorMConf003CleanInactivity i = new MigratorMConf003CleanInactivity();

    public static MigratorMConf003CleanInactivity get() {
        return i;
    }

    private MigratorMConf003CleanInactivity() {
        super(MConf.class);
        this.addInnerMigrator((Migrator)MigratorFieldRename.get((String)"playercleanToleranceMillis", (String)"cleanInactivityToleranceMillis"));
        this.addInnerMigrator((Migrator)MigratorFieldRename.get((String)"playercleanToleranceMillisPlayerAgeToBonus", (String)"cleanInactivityToleranceMillisPlayerAgeToBonus"));
        this.addInnerMigrator((Migrator)MigratorFieldRename.get((String)"playercleanToleranceMillisFactionAgeToBonus", (String)"cleanInactivityToleranceMillisFactionAgeToBonus"));
    }
}

