/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.command.MassiveCommand
 *  com.massivecraft.massivecore.command.requirement.RequirementAbstract
 *  com.massivecraft.massivecore.util.MUtil
 *  com.massivecraft.massivecore.util.Txt
 *  org.bukkit.command.CommandSender
 */
package com.massivecraft.factions.cmd.req;

import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.command.MassiveCommand;
import com.massivecraft.massivecore.command.requirement.RequirementAbstract;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.Txt;
import org.bukkit.command.CommandSender;

public class ReqHasFaction
extends RequirementAbstract {
    private static final long serialVersionUID = 1L;
    private static ReqHasFaction i = new ReqHasFaction();

    public static ReqHasFaction get() {
        return i;
    }

    public boolean apply(CommandSender sender, MassiveCommand command) {
        if (MUtil.isntSender((Object)sender)) {
            return false;
        }
        MPlayer mplayer = MPlayer.get(sender);
        return mplayer.hasFaction();
    }

    public String createErrorMessage(CommandSender sender, MassiveCommand command) {
        return Txt.parse((String)"\u00a7cVoc\u00ea precisa estar em alguma fac\u00e7\u00e3o para poder utilizar este comando.");
    }
}

