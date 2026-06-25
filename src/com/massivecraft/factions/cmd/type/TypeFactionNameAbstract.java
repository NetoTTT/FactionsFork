/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.Named
 *  com.massivecraft.massivecore.command.type.TypeNameAbstract
 *  org.bukkit.command.CommandSender
 */
package com.massivecraft.factions.cmd.type;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.util.MiscUtil;
import com.massivecraft.massivecore.Named;
import com.massivecraft.massivecore.command.type.TypeNameAbstract;
import org.bukkit.command.CommandSender;

public class TypeFactionNameAbstract
extends TypeNameAbstract {
    public TypeFactionNameAbstract(boolean strict) {
        super(strict);
    }

    public Named getCurrent(CommandSender sender) {
        MPlayer mplayer = MPlayer.get(sender);
        Faction faction = mplayer.getFaction();
        return faction;
    }

    public boolean isNameTaken(String name) {
        return FactionColl.get().isNameTaken(name);
    }

    public boolean isCharacterAllowed(char character) {
        return MiscUtil.substanceChars.contains(String.valueOf(character));
    }

    public Integer getLengthMin() {
        return MConf.get().factionNameLengthMin;
    }

    public Integer getLengthMax() {
        return MConf.get().factionNameLengthMax;
    }
}

