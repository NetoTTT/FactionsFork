/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.util.Txt
 *  org.bukkit.ChatColor
 */
package com.massivecraft.factions.util;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.RelationParticipator;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.util.Txt;
import org.bukkit.ChatColor;

public class RelationUtil {
    private static final String UNKNOWN_RELATION_OTHER = "A server admin";
    private static final String UNDEFINED_FACTION_OTHER = "ERRO";
    private static final String OWN_FACTION = "sua fac\u00e7\u00e3o";
    private static final String SELF = "voc\u00ea";

    public static String describeThatToMe(RelationParticipator that, RelationParticipator me, boolean ucfirst) {
        boolean isSameFaction;
        String ret = "";
        if (that == null) {
            return UNKNOWN_RELATION_OTHER;
        }
        Faction thatFaction = RelationUtil.getFaction(that);
        if (thatFaction == null) {
            return UNDEFINED_FACTION_OTHER;
        }
        Faction myFaction = RelationUtil.getFaction(me);
        boolean bl = isSameFaction = thatFaction == myFaction;
        if (that instanceof Faction) {
            String thatFactionName = thatFaction.getName();
            ret = thatFaction.isNone() ? thatFactionName : (me instanceof MPlayer && isSameFaction ? OWN_FACTION : thatFactionName);
        } else if (that instanceof MPlayer) {
            MPlayer mplayerthat = (MPlayer)that;
            ret = that == me ? SELF : (isSameFaction ? mplayerthat.getDisplayName(myFaction) : mplayerthat.getNameAndFactionName());
        }
        if (ucfirst) {
            ret = Txt.upperCaseFirst((String)ret);
        }
        return String.valueOf(RelationUtil.getColorOfThatToMe(that, me).toString()) + ret;
    }

    public static String describeThatToMe(RelationParticipator that, RelationParticipator me) {
        return RelationUtil.describeThatToMe(that, me, false);
    }

    public static Rel getRelationOfThatToMe(RelationParticipator that, RelationParticipator me) {
        return RelationUtil.getRelationOfThatToMe(that, me, false);
    }

    public static Rel getRelationOfThatToMe(RelationParticipator that, RelationParticipator me, boolean ignorePeaceful) {
        Rel myWish;
        Faction myFaction = RelationUtil.getFaction(me);
        if (myFaction == null) {
            return Rel.NEUTRAL;
        }
        Faction thatFaction = RelationUtil.getFaction(that);
        if (thatFaction == null) {
            return Rel.NEUTRAL;
        }
        if (myFaction.equals(thatFaction)) {
            if (that instanceof MPlayer) {
                return ((MPlayer)that).getRole();
            }
            return Rel.MEMBER;
        }
        MFlag flagPeaceful = MFlag.getFlagPeaceful();
        if (!ignorePeaceful && (thatFaction.getFlag(flagPeaceful) || myFaction.getFlag(flagPeaceful))) {
            return Rel.TRUCE;
        }
        Rel theirWish = thatFaction.getRelationWish(myFaction);
        return theirWish.isLessThan(myWish = myFaction.getRelationWish(thatFaction)) ? theirWish : myWish;
    }

    public static Faction getFaction(RelationParticipator rp) {
        if (rp instanceof Faction) {
            return (Faction)rp;
        }
        if (rp instanceof MPlayer) {
            return ((MPlayer)rp).getFaction();
        }
        return null;
    }

    public static ChatColor getColorOfThatToMe(RelationParticipator that, RelationParticipator me) {
        Faction thatFaction = RelationUtil.getFaction(that);
        if (thatFaction != null && thatFaction != RelationUtil.getFaction(me)) {
            if (thatFaction.getFlag(MFlag.getFlagFriendlyire())) {
                return MConf.get().colorFriendlyFire;
            }
            if (!thatFaction.getFlag(MFlag.getFlagPvp())) {
                return MConf.get().colorNoPVP;
            }
        }
        return RelationUtil.getRelationOfThatToMe(that, me).getColor();
    }
}

