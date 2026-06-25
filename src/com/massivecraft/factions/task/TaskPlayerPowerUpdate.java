/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.ModuloRepeatTask
 *  com.massivecraft.massivecore.util.MUtil
 *  org.bukkit.entity.Player
 */
package com.massivecraft.factions.task;

import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsPowerChange;
import com.massivecraft.massivecore.ModuloRepeatTask;
import com.massivecraft.massivecore.util.MUtil;
import org.bukkit.entity.Player;

public class TaskPlayerPowerUpdate
extends ModuloRepeatTask {
    private static TaskPlayerPowerUpdate i = new TaskPlayerPowerUpdate();

    public static TaskPlayerPowerUpdate get() {
        return i;
    }

    public long getDelayMillis() {
        return (long)(MConf.get().taskPlayerPowerUpdateMinutes * 60000.0);
    }

    public void invoke(long now) {
        long millis = this.getDelayMillis();
        for (Player player : MUtil.getOnlinePlayers()) {
            if (MUtil.isntPlayer((Object)player) || player.isDead()) continue;
            MPlayer mplayer = MPlayer.get(player);
            double newPower = mplayer.getPower() + mplayer.getPowerPerHour() * (double)millis / 3600000.0;
            EventFactionsPowerChange event = new EventFactionsPowerChange(null, mplayer, EventFactionsPowerChange.PowerChangeReason.TIME, newPower);
            event.run();
            if (event.isCancelled()) continue;
            newPower = event.getNewPower();
            mplayer.setPower(newPower);
        }
    }
}

