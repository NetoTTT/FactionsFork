/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.ps.PS
 *  com.massivecraft.massivecore.util.MUtil
 *  org.bukkit.command.CommandSender
 *  org.bukkit.event.HandlerList
 */
package com.massivecraft.factions.event;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsAbstractSender;
import com.massivecraft.factions.event.EventFactionsChunkChangeType;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.MUtil;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;

public class EventFactionsChunksChange
extends EventFactionsAbstractSender {
    private static final HandlerList handlers = new HandlerList();
    private final Set<PS> chunks;
    private final Faction newFaction;
    private final Map<PS, Faction> oldChunkFaction;
    private final Map<Faction, Set<PS>> oldFactionChunks;
    private final Map<PS, EventFactionsChunkChangeType> chunkType;
    private final Map<EventFactionsChunkChangeType, Set<PS>> typeChunks;

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Set<PS> getChunks() {
        return this.chunks;
    }

    public Faction getNewFaction() {
        return this.newFaction;
    }

    public Map<PS, Faction> getOldChunkFaction() {
        return this.oldChunkFaction;
    }

    public Map<Faction, Set<PS>> getOldFactionChunks() {
        return this.oldFactionChunks;
    }

    public Map<PS, EventFactionsChunkChangeType> getChunkType() {
        return this.chunkType;
    }

    public Map<EventFactionsChunkChangeType, Set<PS>> getTypeChunks() {
        return this.typeChunks;
    }

    public EventFactionsChunksChange(CommandSender sender, Set<PS> chunks, Faction newFaction) {
        super(sender);
        chunks = PS.getDistinctChunks(chunks);
        this.chunks = Collections.unmodifiableSet(chunks);
        this.newFaction = newFaction;
        this.oldChunkFaction = Collections.unmodifiableMap(BoardColl.getChunkFaction(chunks));
        this.oldFactionChunks = Collections.unmodifiableMap(MUtil.reverseIndex(this.oldChunkFaction));
        MPlayer msender = this.getMPlayer();
        Faction self = null;
        if (msender != null) {
            self = msender.getFaction();
        }
        LinkedHashMap<PS, EventFactionsChunkChangeType> currentChunkType = new LinkedHashMap<PS, EventFactionsChunkChangeType>();
        for (Map.Entry<PS, Faction> entry : this.oldChunkFaction.entrySet()) {
            PS chunk = entry.getKey();
            Faction from = entry.getValue();
            currentChunkType.put(chunk, EventFactionsChunkChangeType.get(from, newFaction, self));
        }
        this.chunkType = Collections.unmodifiableMap(currentChunkType);
        this.typeChunks = Collections.unmodifiableMap(MUtil.reverseIndex(this.chunkType));
    }
}

