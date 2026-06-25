/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.xlib.gson.JsonDeserializationContext
 *  com.massivecraft.massivecore.xlib.gson.JsonDeserializer
 *  com.massivecraft.massivecore.xlib.gson.JsonElement
 *  com.massivecraft.massivecore.xlib.gson.JsonObject
 *  com.massivecraft.massivecore.xlib.gson.JsonParseException
 *  com.massivecraft.massivecore.xlib.gson.JsonPrimitive
 *  com.massivecraft.massivecore.xlib.gson.JsonSerializationContext
 *  com.massivecraft.massivecore.xlib.gson.JsonSerializer
 *  com.massivecraft.massivecore.xlib.gson.reflect.TypeToken
 */
package com.massivecraft.factions.adapter;

import com.massivecraft.factions.TerritoryAccess;
import com.massivecraft.massivecore.xlib.gson.JsonDeserializationContext;
import com.massivecraft.massivecore.xlib.gson.JsonDeserializer;
import com.massivecraft.massivecore.xlib.gson.JsonElement;
import com.massivecraft.massivecore.xlib.gson.JsonObject;
import com.massivecraft.massivecore.xlib.gson.JsonParseException;
import com.massivecraft.massivecore.xlib.gson.JsonPrimitive;
import com.massivecraft.massivecore.xlib.gson.JsonSerializationContext;
import com.massivecraft.massivecore.xlib.gson.JsonSerializer;
import com.massivecraft.massivecore.xlib.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Set;

public class TerritoryAccessAdapter
implements JsonDeserializer<TerritoryAccess>,
JsonSerializer<TerritoryAccess> {
    public static final String HOST_FACTION_ID = "hostFactionId";
    public static final String HOST_FACTION_ALLOWED = "hostFactionAllowed";
    public static final String FACTION_IDS = "factionIds";
    public static final String PLAYER_IDS = "playerIds";
    public static final Type SET_OF_STRING_TYPE = new TypeToken<Set<String>>(){}.getType();
    private static TerritoryAccessAdapter i = new TerritoryAccessAdapter();

    public static TerritoryAccessAdapter get() {
        return i;
    }

    public TerritoryAccess deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonPrimitive()) {
            String hostFactionId = json.getAsString();
            return TerritoryAccess.valueOf(hostFactionId);
        }
        JsonObject obj = json.getAsJsonObject();
        String hostFactionId = null;
        Boolean hostFactionAllowed = null;
        Set factionIds = null;
        Set playerIds = null;
        JsonElement element = null;
        element = obj.get("ID");
        if (element == null) {
            element = obj.get(HOST_FACTION_ID);
        }
        hostFactionId = element.getAsString();
        element = obj.get("open");
        if (element == null) {
            element = obj.get(HOST_FACTION_ALLOWED);
        }
        if (element != null) {
            hostFactionAllowed = element.getAsBoolean();
        }
        if ((element = obj.get("factions")) == null) {
            element = obj.get(FACTION_IDS);
        }
        if (element != null) {
            factionIds = (Set)context.deserialize(element, SET_OF_STRING_TYPE);
        }
        if ((element = obj.get("fplayers")) == null) {
            element = obj.get(PLAYER_IDS);
        }
        if (element != null) {
            playerIds = (Set)context.deserialize(element, SET_OF_STRING_TYPE);
        }
        return TerritoryAccess.valueOf(hostFactionId, hostFactionAllowed, factionIds, playerIds);
    }

    public JsonElement serialize(TerritoryAccess src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null) {
            return null;
        }
        if (src.isDefault()) {
            return new JsonPrimitive(src.getHostFactionId());
        }
        JsonObject obj = new JsonObject();
        obj.addProperty(HOST_FACTION_ID, src.getHostFactionId());
        if (!src.isHostFactionAllowed()) {
            obj.addProperty(HOST_FACTION_ALLOWED, Boolean.valueOf(src.isHostFactionAllowed()));
        }
        if (!src.getFactionIds().isEmpty()) {
            obj.add(FACTION_IDS, context.serialize(src.getFactionIds(), SET_OF_STRING_TYPE));
        }
        if (!src.getPlayerIds().isEmpty()) {
            obj.add(PLAYER_IDS, context.serialize(src.getPlayerIds(), SET_OF_STRING_TYPE));
        }
        return obj;
    }
}

