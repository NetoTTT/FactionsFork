/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.predicate.Predicate
 *  com.massivecraft.massivecore.util.MUtil
 *  org.bukkit.command.CommandSender
 */
package com.massivecraft.factions.predicate;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.predicate.Predicate;
import com.massivecraft.massivecore.util.MUtil;
import java.io.Serializable;
import org.bukkit.command.CommandSender;

public class PredicateCommandSenderFaction
implements Predicate<CommandSender>,
Serializable {
    private static final long serialVersionUID = 1L;
    private final String factionId;

    public String getFactionId() {
        return this.factionId;
    }

    public PredicateCommandSenderFaction(Faction faction) {
        this.factionId = faction.getId();
    }

    public boolean apply(CommandSender sender) {
        if (MUtil.isntSender((Object)sender)) {
            return false;
        }
        MPlayer mplayer = MPlayer.get(sender);
        return this.factionId.equals(mplayer.getFaction().getId());
    }
}

