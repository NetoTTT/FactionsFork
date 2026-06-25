/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.ModuloRepeatTask
 *  com.massivecraft.massivecore.nms.NmsChat
 *  com.massivecraft.massivecore.ps.PS
 *  org.bukkit.Chunk
 *  org.bukkit.entity.Player
 */
package com.massivecraft.factions.task;

import com.massivecraft.factions.engine.EngineSobAtaque;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.util.ColorScrollPlus;
import com.massivecraft.massivecore.ModuloRepeatTask;
import com.massivecraft.massivecore.nms.NmsChat;
import com.massivecraft.massivecore.ps.PS;
import java.util.ArrayList;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class TaskUnderAttack
extends ModuloRepeatTask {
    final ColorScrollPlus cs = new ColorScrollPlus("\u00a74", "Sua fac\u00e7\u00e3o est\u00e1 sob ataque!", "\u00a7c", "\u00a74", "\u00a74", false, false, ColorScrollPlus.ScrollType.FORWARD);
    private static TaskUnderAttack i = new TaskUnderAttack();

    public static TaskUnderAttack get() {
        return i;
    }

    public long getDelayMillis() {
        return 250L;
    }

    public void invoke(long now) {
        if (this.cs.getScrollType() == ColorScrollPlus.ScrollType.FORWARD) {
            if (this.cs.getPosition() >= this.cs.getString().length()) {
                this.cs.setScrollType(ColorScrollPlus.ScrollType.BACKWARD);
            }
        } else if (this.cs.getPosition() <= -1) {
            this.cs.setScrollType(ColorScrollPlus.ScrollType.FORWARD);
        }
        ArrayList<Faction> facunder = new ArrayList<Faction>();
        for (Chunk chunk : EngineSobAtaque.underattack.keySet()) {
            Faction fac = BoardColl.get().getFactionAt(PS.valueOf((Chunk)chunk));
            if (fac == null) {
                return;
            }
            if (!facunder.contains(fac)) {
                facunder.add(fac);
            }
            EngineSobAtaque.get().aumentarSegundos(chunk);
            if (System.currentTimeMillis() - EngineSobAtaque.get().getTime(chunk) <= 300000L) continue;
            EngineSobAtaque.get().remover(chunk, fac);
        }
        for (Faction f : facunder) {
            for (Player p : f.getOnlinePlayers()) {
                NmsChat.get().sendActionbarMsg((Object)p, this.cs.next());
            }
        }
    }
}

