/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.Progressbar
 *  com.massivecraft.massivecore.util.TimeDiffUtil
 *  com.massivecraft.massivecore.util.TimeUnit
 *  com.massivecraft.massivecore.util.Txt
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.type.TypeMPlayer;
import com.massivecraft.factions.engine.EngineKdr;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.Progressbar;
import com.massivecraft.massivecore.util.TimeDiffUtil;
import com.massivecraft.massivecore.util.TimeUnit;
import com.massivecraft.massivecore.util.Txt;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;

public class CmdFactionsPerfil
extends FactionsCommand {
    public CmdFactionsPerfil() {
        this.addAliases(new String[]{"p", "player"});
        this.addParameter(TypeMPlayer.get(), "player", "voc\u00ea");
        this.setDesc("\u00a76 p,perfil \u00a7e<player> \u00a78-\u00a77 Mostra os status do player.");
    }

    public void perform() throws MassiveException {
        MPlayer mplayer = (MPlayer)this.readArg(this.msender);
        boolean hasfac = mplayer.hasFaction();
        this.message(Txt.titleize((Object)("\u00a7ePefil de \u00a7e" + mplayer.getName())));
        double progressbarQuota = 0.0;
        double playerPowerMax = mplayer.getPowerMax();
        if (playerPowerMax != 0.0) {
            progressbarQuota = mplayer.getPower() / playerPowerMax;
        }
        int progressbarWidth = (int)Math.round(mplayer.getPowerMax() / mplayer.getPowerMaxUniversal() * 100.0);
        this.msg("\u00a76Poder: \u00a7e%s", new Object[]{Progressbar.HEALTHBAR_CLASSIC.withQuota(progressbarQuota).withWidth(progressbarWidth).render()});
        this.msg("\u00a76Poder Total: \u00a7e%.2f\u00a7e/\u00a7e%.2f", new Object[]{mplayer.getPower(), mplayer.getPowerMax()});
        this.msg("\u00a76Fac\u00e7\u00e3o: \u00a7e" + (hasfac ? mplayer.getFactionName() : "\u00a77\u00a7oNenhuma"));
        this.msg("\u00a76Cargo: \u00a7e" + (hasfac ? String.valueOf(mplayer.getRole().getPrefix()) + mplayer.getRole().getName() : "\u00a77\u00a7oNenhum"));
        if (mplayer.hasPowerBoost()) {
            this.msg("\u00a76Poder m\u00e1ximo: \u00a7e%f", new Object[]{mplayer.getPowerBoost()});
        }
        this.msg("\u00a76Status: " + (mplayer.isOnline() ? "\u00a72Online" : "\u00a7cOffline"));
        long ultimoLoginMillis = mplayer.getLastActivityMillis() - System.currentTimeMillis();
        LinkedHashMap ageUnitcounts = TimeDiffUtil.limit((LinkedHashMap)TimeDiffUtil.unitcounts((long)ultimoLoginMillis, (TreeSet)TimeUnit.getAllButMillis()), (int)3);
        String ultimoLogin = TimeDiffUtil.formatedMinimal((Map)ageUnitcounts, (String)"\u00a7e");
        this.msg("\u00a76\u00daltimo login: \u00a7e" + ultimoLogin + "\u00a7e atr\u00e1s");
        this.msg("\u00a76Abates / Mortes / Kdr: \u00a7e" + EngineKdr.getPlayerKills(mplayer) + "\u00a7e/" + EngineKdr.getPlayerDeaths(mplayer) + "\u00a7e/" + EngineKdr.getPlayerKdr(mplayer));
    }
}

