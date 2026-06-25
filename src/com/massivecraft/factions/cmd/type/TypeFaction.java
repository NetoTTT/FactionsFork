/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveCore
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.command.type.TypeAbstract
 *  com.massivecraft.massivecore.comparator.ComparatorCaseInsensitive
 *  com.massivecraft.massivecore.util.IdUtil
 *  org.bukkit.ChatColor
 *  org.bukkit.command.CommandSender
 */
package com.massivecraft.factions.cmd.type;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.entity.MPlayerColl;
import com.massivecraft.massivecore.MassiveCore;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.TypeAbstract;
import com.massivecraft.massivecore.comparator.ComparatorCaseInsensitive;
import com.massivecraft.massivecore.util.IdUtil;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class TypeFaction
extends TypeAbstract<Faction> {
    private static TypeFaction i = new TypeFaction();

    public static TypeFaction get() {
        return i;
    }

    public TypeFaction() {
        super(Faction.class);
    }

    public String getVisualInner(Faction value, CommandSender sender) {
        return value.describeTo(MPlayer.get(sender));
    }

    public String getNameInner(Faction value) {
        return ChatColor.stripColor((String)value.getName());
    }

    public Faction read(String str, CommandSender sender) throws MassiveException {
        Faction ret;
        if (MassiveCore.NOTHING_REMOVE.contains(str)) {
            return FactionColl.get().getNone();
        }
        if (FactionColl.get().containsId(str) && (ret = (Faction)FactionColl.get().get(str)) != null) {
            return ret;
        }
        ret = FactionColl.get().getByName(str);
        if (ret != null) {
            return ret;
        }
        String id = IdUtil.getId((Object)str);
        MPlayer mplayer = (MPlayer)MPlayerColl.get().get(id, false);
        if (mplayer != null) {
            return mplayer.getFaction();
        }
        throw new MassiveException().addMsg("\u00a7cNenhum player ou fac\u00e7\u00e3o encontrado com o nome \"\u00a7c%s\u00a7c\".", new Object[]{str});
    }

    public Collection<String> getTabList(CommandSender sender, String arg) {
        TreeSet<String> ret = new TreeSet<String>((Comparator<String>)ComparatorCaseInsensitive.get());
        for (Faction faction : FactionColl.get().getAll()) {
            ret.add(ChatColor.stripColor((String)faction.getName()));
        }
        return ret;
    }
}

