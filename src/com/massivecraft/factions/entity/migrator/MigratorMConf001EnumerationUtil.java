/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.store.migrator.MigratorRoot
 *  com.massivecraft.massivecore.xlib.gson.JsonArray
 *  com.massivecraft.massivecore.xlib.gson.JsonElement
 *  com.massivecraft.massivecore.xlib.gson.JsonObject
 *  com.massivecraft.massivecore.xlib.gson.JsonPrimitive
 */
package com.massivecraft.factions.entity.migrator;

import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.util.EnumerationUtil;
import com.massivecraft.massivecore.store.migrator.MigratorRoot;
import com.massivecraft.massivecore.xlib.gson.JsonArray;
import com.massivecraft.massivecore.xlib.gson.JsonElement;
import com.massivecraft.massivecore.xlib.gson.JsonObject;
import com.massivecraft.massivecore.xlib.gson.JsonPrimitive;
import java.util.Collection;
import java.util.Iterator;

public class MigratorMConf001EnumerationUtil
extends MigratorRoot {
    private static MigratorMConf001EnumerationUtil i = new MigratorMConf001EnumerationUtil();

    public static MigratorMConf001EnumerationUtil get() {
        return i;
    }

    private MigratorMConf001EnumerationUtil() {
        super(MConf.class);
    }

    public void migrateInner(JsonObject entity) {
        this.removeFromStringsField(entity, "materialsEditOnInteract", EnumerationUtil.MATERIALS_EDIT_ON_INTERACT.getStringSet());
        this.removeFromStringsField(entity, "materialsEditTools", EnumerationUtil.MATERIALS_EDIT_TOOL.getStringSet());
        this.removeFromStringsField(entity, "materialsDoor", EnumerationUtil.MATERIALS_DOOR.getStringSet());
        this.removeFromStringsField(entity, "materialsContainer", EnumerationUtil.MATERIALS_CONTAINER.getStringSet());
        this.removeFromStringsField(entity, "entityTypesEditOnInteract", EnumerationUtil.ENTITY_TYPES_EDIT_ON_INTERACT.getStringSet());
        this.removeFromStringsField(entity, "entityTypesEditOnDamage", EnumerationUtil.ENTITY_TYPES_EDIT_ON_DAMAGE.getStringSet());
        this.removeFromStringsField(entity, "entityTypesContainer", EnumerationUtil.ENTITY_TYPES_CONTAINER.getStringSet());
        this.removeFromStringsField(entity, "entityTypesMonsters", EnumerationUtil.ENTITY_TYPES_MONSTER.getStringSet());
        this.removeFromStringsField(entity, "entityTypesAnimals", EnumerationUtil.ENTITY_TYPES_ANIMAL.getStringSet());
    }

    private void removeFromStringsField(JsonObject entity, String fieldName, Collection<String> removals) {
        JsonElement stringsElement = entity.get(fieldName);
        if (!(stringsElement instanceof JsonArray)) {
            return;
        }
        JsonArray strings = (JsonArray)stringsElement;
        Iterator iterator = strings.iterator();
        while (iterator.hasNext()) {
            JsonPrimitive string;
            JsonElement stringElement = (JsonElement)iterator.next();
            if (!(stringElement instanceof JsonPrimitive) || !removals.contains((string = (JsonPrimitive)stringElement).getAsString())) continue;
            iterator.remove();
        }
    }
}

