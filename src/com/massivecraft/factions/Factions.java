/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassivePlugin
 *  com.massivecraft.massivecore.collections.MassiveList
 *  com.massivecraft.massivecore.command.type.RegistryType
 *  com.massivecraft.massivecore.command.type.Type
 *  com.massivecraft.massivecore.predicate.Predicate
 *  com.massivecraft.massivecore.util.MUtil
 *  com.massivecraft.massivecore.util.extractor.Extractor
 *  com.massivecraft.massivecore.xlib.gson.GsonBuilder
 *  org.bukkit.ChatColor
 */
package com.massivecraft.factions;

import com.massivecraft.factions.ExtractorFactionAccountId;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.TerritoryAccess;
import com.massivecraft.factions.adapter.BoardAdapter;
import com.massivecraft.factions.adapter.BoardMapAdapter;
import com.massivecraft.factions.adapter.RelAdapter;
import com.massivecraft.factions.adapter.TerritoryAccessAdapter;
import com.massivecraft.factions.chat.ChatActive;
import com.massivecraft.factions.cmd.type.TypeFactionChunkChangeType;
import com.massivecraft.factions.cmd.type.TypeRel;
import com.massivecraft.factions.entity.Board;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MConfColl;
import com.massivecraft.factions.entity.MFlagColl;
import com.massivecraft.factions.entity.MPermColl;
import com.massivecraft.factions.entity.MPlayerColl;
import com.massivecraft.factions.event.EventFactionsChunkChangeType;
import com.massivecraft.factions.mixin.PowerMixin;
import com.massivecraft.massivecore.MassivePlugin;
import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.command.type.RegistryType;
import com.massivecraft.massivecore.command.type.Type;
import com.massivecraft.massivecore.predicate.Predicate;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.extractor.Extractor;
import com.massivecraft.massivecore.xlib.gson.GsonBuilder;
import java.util.List;
import org.bukkit.ChatColor;

public class Factions
extends MassivePlugin {
    public static final String ID_NONE = "none";
    public static final String ID_SAFEZONE = "safezone";
    public static final String ID_WARZONE = "warzone";
    public static final String NAME_NONE_DEFAULT = String.valueOf(ChatColor.DARK_GREEN.toString()) + "Zona Livre";
    public static final String NAME_SAFEZONE_DEFAULT = String.valueOf(ChatColor.GOLD.toString()) + "Zona Protegida";
    public static final String NAME_WARZONE_DEFAULT = String.valueOf(ChatColor.DARK_RED.toString()) + "Zona de Guerra";
    private static Factions i;

    public static Factions get() {
        return i;
    }

    public Factions() {
        i = this;
    }

    @Deprecated
    public PowerMixin getPowerMixin() {
        return PowerMixin.get();
    }

    @Deprecated
    public void setPowerMixin(PowerMixin powerMixin) {
        PowerMixin.get().setInstance(powerMixin);
    }

    public void onEnableInner() {
        RegistryType.register(Rel.class, (Type)TypeRel.get());
        RegistryType.register(EventFactionsChunkChangeType.class, (Type)TypeFactionChunkChangeType.get());
        MUtil.registerExtractor(String.class, (String)"accountId", (Extractor)ExtractorFactionAccountId.get());
        this.activateAuto();
        this.activate(new Object[]{this.getClassesActive("chat", ChatActive.class, new Predicate[0])});
    }

    public List<Class<?>> getClassesActiveColls() {
        return new MassiveList((Object[])new Class[]{MConfColl.class, MFlagColl.class, MPermColl.class, FactionColl.class, MPlayerColl.class, BoardColl.class});
    }

    public GsonBuilder getGsonBuilder() {
        return super.getGsonBuilder().registerTypeAdapter(TerritoryAccess.class, (Object)TerritoryAccessAdapter.get()).registerTypeAdapter(Board.class, (Object)BoardAdapter.get()).registerTypeAdapter(Board.MAP_TYPE, (Object)BoardMapAdapter.get()).registerTypeAdapter(Rel.class, (Object)RelAdapter.get());
    }
}

