/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.xlib.gson.JsonDeserializationContext
 *  com.massivecraft.massivecore.xlib.gson.JsonDeserializer
 *  com.massivecraft.massivecore.xlib.gson.JsonElement
 *  com.massivecraft.massivecore.xlib.gson.JsonParseException
 */
package com.massivecraft.factions.adapter;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.cmd.type.TypeRel;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.xlib.gson.JsonDeserializationContext;
import com.massivecraft.massivecore.xlib.gson.JsonDeserializer;
import com.massivecraft.massivecore.xlib.gson.JsonElement;
import com.massivecraft.massivecore.xlib.gson.JsonParseException;
import java.lang.reflect.Type;

public class RelAdapter
implements JsonDeserializer<Rel> {
    private static RelAdapter i = new RelAdapter();

    public static RelAdapter get() {
        return i;
    }

    public Rel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return (Rel)((Object)TypeRel.get().read(json.getAsString()));
        }
        catch (MassiveException e) {
            return null;
        }
    }
}

