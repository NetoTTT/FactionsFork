/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.mixin.MixinSenderPs
 *  com.massivecraft.massivecore.mixin.MixinTitle
 *  com.massivecraft.massivecore.ps.PS
 *  com.massivecraft.massivecore.ps.PSFormat
 *  com.massivecraft.massivecore.ps.PSFormatHumanSpace
 *  com.massivecraft.massivecore.store.SenderEntity
 *  com.massivecraft.massivecore.util.MUtil
 *  com.massivecraft.massivecore.xlib.gson.annotations.SerializedName
 *  org.bukkit.ChatColor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.massivecraft.factions.entity;

import com.massivecraft.factions.FactionsIndex;
import com.massivecraft.factions.FactionsParticipator;
import com.massivecraft.factions.Perm;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.RelationParticipator;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.factions.entity.MPlayerColl;
import com.massivecraft.factions.event.EventFactionsChunkChangeType;
import com.massivecraft.factions.event.EventFactionsChunksChange;
import com.massivecraft.factions.event.EventFactionsDisband;
import com.massivecraft.factions.event.EventFactionsMembershipChange;
import com.massivecraft.factions.mixin.PowerMixin;
import com.massivecraft.factions.util.RelationUtil;
import com.massivecraft.massivecore.mixin.MixinSenderPs;
import com.massivecraft.massivecore.mixin.MixinTitle;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.ps.PSFormat;
import com.massivecraft.massivecore.ps.PSFormatHumanSpace;
import com.massivecraft.massivecore.store.SenderEntity;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.xlib.gson.annotations.SerializedName;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MPlayer
extends SenderEntity<MPlayer>
implements FactionsParticipator {
    private long lastActivityMillis = System.currentTimeMillis();
    private String factionId = null;
    private Rel role = null;
    private Double powerBoost = null;
    private Double power = null;
    private Boolean mapAutoUpdating = null;
    @SerializedName(value="usingAdminMode")
    private Boolean overriding = null;
    private Boolean territoryInfoTitles = null;
    private Double kills = null;
    private Double deaths = null;
    private transient WeakReference<Faction> autoClaimFaction = new WeakReference<Faction>(null);
    private transient boolean seeingChunk = false;

    public static MPlayer get(Object oid) {
        return (MPlayer)MPlayerColl.get().get(oid);
    }

    public MPlayer load(MPlayer that) {
        this.setLastActivityMillis(that.lastActivityMillis);
        this.setFactionId(that.factionId);
        this.setRole(that.role);
        this.setPowerBoost(that.powerBoost);
        this.setPower(that.power);
        this.setMapAutoUpdating(that.mapAutoUpdating);
        this.setOverriding(that.overriding);
        this.setTerritoryInfoTitles(that.territoryInfoTitles);
        this.setKills(that.kills);
        this.setDeaths(that.deaths);
        return this;
    }

    public boolean isDefault() {
        if (this.hasFaction()) {
            return false;
        }
        if (this.hasPowerBoost()) {
            return false;
        }
        if (this.getPowerRounded() != (int)Math.round(MConf.get().defaultPlayerPower)) {
            return false;
        }
        if (this.isOverriding()) {
            return false;
        }
        return this.isTerritoryInfoTitles() == MConf.get().territoryInfoTitlesDefault;
    }

    public void postAttach(String id) {
        FactionsIndex.get().update(this);
    }

    public void preDetach(String id) {
        FactionsIndex.get().update(this);
    }

    public void preClean() {
        if (this.getRole() == Rel.LEADER) {
            this.getFaction().promoteNewLeader();
        }
        this.leave();
    }

    public Faction getAutoClaimFaction() {
        if (this.isFactionOrphan()) {
            return null;
        }
        Faction ret = (Faction)this.autoClaimFaction.get();
        if (ret == null) {
            return null;
        }
        if (ret.detached()) {
            return null;
        }
        return ret;
    }

    public void setAutoClaimFaction(Faction autoClaimFaction) {
        this.autoClaimFaction = new WeakReference<Faction>(autoClaimFaction);
    }

    public boolean isSeeingChunk() {
        return this.seeingChunk;
    }

    public void setSeeingChunk(boolean seeingChunk) {
        this.seeingChunk = seeingChunk;
    }

    public void resetFactionData() {
        this.setFactionId(null);
        this.setRole(null);
        this.setAutoClaimFaction(null);
    }

    public long getLastActivityMillis() {
        return this.lastActivityMillis;
    }

    public void setLastActivityMillis(long lastActivityMillis) {
        long target = lastActivityMillis;
        if (MUtil.equals((Object)this.lastActivityMillis, (Object)target)) {
            return;
        }
        this.lastActivityMillis = target;
        this.changed();
    }

    public void setLastActivityMillis() {
        this.setLastActivityMillis(System.currentTimeMillis());
    }

    public boolean shouldBeCleaned(long now) {
        return this.shouldBeCleaned(now, this.lastActivityMillis);
    }

    private Faction getFactionInternal() {
        String effectiveFactionId = (String)this.convertGet(this.factionId, MConf.get().defaultPlayerFactionId);
        return Faction.get(effectiveFactionId);
    }

    public boolean isFactionOrphan() {
        return this.getFactionInternal() == null;
    }

    @Deprecated
    public String getFactionId() {
        return this.getFaction().getId();
    }

    public Faction getFaction() {
        Faction ret = this.getFactionInternal();
        if (ret == null) {
            ret = FactionColl.get().getNone();
        }
        return ret;
    }

    public boolean hasFaction() {
        return !this.getFaction().isNone();
    }

    public void setFactionId(String factionId) {
        String beforeId = this.factionId;
        String afterId = factionId;
        if (MUtil.equals((Object)beforeId, (Object)afterId)) {
            return;
        }
        this.factionId = afterId;
        FactionsIndex.get().update(this);
        this.changed();
    }

    public void setFaction(Faction faction) {
        this.setFactionId(faction.getId());
    }

    public Rel getRole() {
        if (this.isFactionOrphan()) {
            return Rel.RECRUIT;
        }
        if (this.role == null) {
            return MConf.get().defaultPlayerRole;
        }
        return this.role;
    }

    public void setRole(Rel role) {
        Rel target = role;
        if (MUtil.equals((Object)((Object)this.role), (Object)((Object)target))) {
            return;
        }
        this.role = target;
        this.changed();
    }

    @Override
    public double getPowerBoost() {
        Double ret = this.powerBoost;
        if (ret == null) {
            ret = 0.0;
        }
        return ret;
    }

    @Override
    public void setPowerBoost(Double powerBoost) {
        Double target = powerBoost;
        if (target == null || target == 0.0) {
            target = null;
        }
        if (MUtil.equals((Object)this.powerBoost, (Object)target)) {
            return;
        }
        this.powerBoost = target;
        this.changed();
    }

    public boolean hasPowerBoost() {
        return this.getPowerBoost() != 0.0;
    }

    public double getPowerMaxUniversal() {
        return PowerMixin.get().getMaxUniversal(this);
    }

    public double getPowerMax() {
        return PowerMixin.get().getMax(this);
    }

    public double getPowerMin() {
        return PowerMixin.get().getMin(this);
    }

    public double getPowerPerHour() {
        return PowerMixin.get().getPerHour(this);
    }

    public double getPowerPerDeath() {
        return PowerMixin.get().getPerDeath(this);
    }

    public double getLimitedPower(double power) {
        power = Math.max(power, this.getPowerMin());
        power = Math.min(power, this.getPowerMax());
        return power;
    }

    public int getPowerMaxRounded() {
        return (int)Math.round(this.getPowerMax());
    }

    public int getPowerMinRounded() {
        return (int)Math.round(this.getPowerMin());
    }

    public int getPowerMaxUniversalRounded() {
        return (int)Math.round(this.getPowerMaxUniversal());
    }

    @Deprecated
    public double getDefaultPower() {
        return MConf.get().defaultPlayerPower;
    }

    public double getPower() {
        Double ret = this.power;
        if (ret == null) {
            ret = MConf.get().defaultPlayerPower;
        }
        ret = this.getLimitedPower(ret);
        return ret;
    }

    public void setPower(Double power) {
        Double target = power;
        if (MUtil.equals((Object)this.power, (Object)target)) {
            return;
        }
        this.power = target;
        this.changed();
    }

    public int getPowerRounded() {
        return (int)Math.round(this.getPower());
    }

    public boolean isMapAutoUpdating() {
        if (this.mapAutoUpdating == null) {
            return false;
        }
        return this.mapAutoUpdating != false;
    }

    public void setMapAutoUpdating(Boolean mapAutoUpdating) {
        Boolean target = mapAutoUpdating;
        if (MUtil.equals((Object)target, (Object)false)) {
            target = null;
        }
        if (MUtil.equals((Object)this.mapAutoUpdating, (Object)target)) {
            return;
        }
        this.mapAutoUpdating = target;
        this.changed();
    }

    public boolean isOverriding() {
        if (this.overriding == null) {
            return false;
        }
        if (!this.overriding.booleanValue()) {
            return false;
        }
        if (!this.hasPermission((Object)Perm.ADMIN, true).booleanValue()) {
            this.setOverriding(false);
            return false;
        }
        return true;
    }

    public void setOverriding(Boolean overriding) {
        Boolean target = overriding;
        if (MUtil.equals((Object)target, (Object)false)) {
            target = null;
        }
        if (MUtil.equals((Object)this.overriding, (Object)target)) {
            return;
        }
        this.overriding = target;
        this.changed();
    }

    public void setDeaths(Double deaths) {
        Double target = deaths;
        if (MUtil.equals((Object)this.deaths, (Object)target)) {
            return;
        }
        this.deaths = target;
        this.changed();
    }

    public double getDeaths() {
        Double deaths = this.deaths;
        if (deaths == null) {
            deaths = 0.0;
        }
        return deaths;
    }

    public void setKills(Double kills) {
        Double target = kills;
        if (MUtil.equals((Object)this.kills, (Object)target)) {
            return;
        }
        this.kills = target;
        this.changed();
    }

    public double getKills() {
        Double kills = this.kills;
        if (kills == null) {
            kills = 0.0;
        }
        return kills;
    }

    public double getKdr() {
        Double kills = this.getKills();
        Double deaths = this.getDeaths();
        if (deaths == 0.0) {
            return kills;
        }
        double kdr = kills / deaths;
        return kdr;
    }

    public boolean isTerritoryInfoTitles() {
        if (!MixinTitle.get().isAvailable()) {
            return false;
        }
        if (this.territoryInfoTitles == null) {
            return MConf.get().territoryInfoTitlesDefault;
        }
        return this.territoryInfoTitles;
    }

    public void setTerritoryInfoTitles(Boolean territoryInfoTitles) {
        Boolean target = territoryInfoTitles;
        if (MUtil.equals((Object)target, (Object)MConf.get().territoryInfoTitlesDefault)) {
            target = null;
        }
        if (MUtil.equals((Object)this.territoryInfoTitles, (Object)target)) {
            return;
        }
        this.territoryInfoTitles = target;
        this.changed();
    }

    public String getFactionName() {
        Faction faction = this.getFaction();
        if (faction.isNone()) {
            return "";
        }
        return faction.getName();
    }

    public String getNameAndSomething(String color, String something) {
        String ret = "";
        ret = String.valueOf(ret) + color;
        ret = String.valueOf(ret) + this.getRole().getPrefix();
        if (something != null && something.length() > 0) {
            ret = String.valueOf(ret) + something;
            ret = String.valueOf(ret) + " ";
            ret = String.valueOf(ret) + color;
        }
        ret = String.valueOf(ret) + this.getName();
        return ret;
    }

    public String getNameAndFactionName() {
        return this.getNameAndSomething("", this.getFactionName());
    }

    @Override
    public String describeTo(RelationParticipator observer, boolean ucfirst) {
        return RelationUtil.describeThatToMe(this, observer, ucfirst);
    }

    @Override
    public String describeTo(RelationParticipator observer) {
        return RelationUtil.describeThatToMe(this, observer);
    }

    @Override
    public Rel getRelationTo(RelationParticipator observer) {
        return RelationUtil.getRelationOfThatToMe(this, observer);
    }

    @Override
    public Rel getRelationTo(RelationParticipator observer, boolean ignorePeaceful) {
        return RelationUtil.getRelationOfThatToMe(this, observer, ignorePeaceful);
    }

    @Override
    public ChatColor getColorTo(RelationParticipator observer) {
        return RelationUtil.getColorOfThatToMe(this, observer);
    }

    public void heal(int amnt) {
        Player player = this.getPlayer();
        if (player == null) {
            return;
        }
        player.setHealth(player.getHealth() + (double)amnt);
    }

    public boolean isInOwnTerritory() {
        PS ps = MixinSenderPs.get().getSenderPs((Object)this.getId());
        if (ps == null) {
            return false;
        }
        return BoardColl.get().getFactionAt(ps) == this.getFaction();
    }

    public boolean isInEnemyTerritory() {
        PS ps = MixinSenderPs.get().getSenderPs((Object)this.getId());
        if (ps == null) {
            return false;
        }
        return BoardColl.get().getFactionAt(ps).getRelationTo(this) == Rel.ENEMY;
    }

    public void leave() {
        Faction myFaction = this.getFaction();
        boolean permanent = myFaction.getFlag(MFlag.getFlagPermanent());
        if (myFaction.getMPlayers().size() > 1 && !permanent && this.getRole() == Rel.LEADER) {
            this.msg("\u00a7cVoc\u00ea deve passar lideran\u00e7a da fac\u00e7\u00e3o para outra pessoa para poder fazer isso.");
            return;
        }
        EventFactionsMembershipChange membershipChangeEvent = new EventFactionsMembershipChange(this.getSender(), this, myFaction, EventFactionsMembershipChange.MembershipChangeReason.LEAVE);
        membershipChangeEvent.run();
        if (membershipChangeEvent.isCancelled()) {
            return;
        }
        if (myFaction.isNormal()) {
            for (MPlayer mplayer : myFaction.getMPlayersWhereOnline(true)) {
                mplayer.msg("\u00a7f%s\u00a7e abandonou a sua fac\u00e7\u00e3o.", new Object[]{this.describeTo(mplayer, true).replace("Voc\u00ea", "\u00a7eVoc\u00ea")});
            }
        }
        this.resetFactionData();
        if (myFaction.isNormal() && !permanent && myFaction.getMPlayers().isEmpty()) {
            EventFactionsDisband eventFactionsDisband = new EventFactionsDisband(this.getSender(), myFaction);
            eventFactionsDisband.run();
            if (!eventFactionsDisband.isCancelled()) {
                this.msg("\u00a7eA fac\u00e7\u00e3o \u00a7f%s \u00a7efoi desfeita pois voc\u00ea saiu e voc\u00ea era o ultimo membro da fa\u00e7c\u00e3o.", new Object[]{myFaction.getName()});
                myFaction.detach();
            }
        }
    }

    public boolean tryClaim(Faction newFaction, Collection<PS> pss) {
        return this.tryClaim(newFaction, pss, null, null);
    }

    public boolean tryClaim(Faction newFaction, Collection<PS> pss, String formatOne, String formatMany) {
        if (formatOne == null) {
            formatOne = "\u00a7a%s\u00a7a %s \u00a7d%d \u00a7achunk\u00a7a.";
        }
        if (formatMany == null) {
            formatMany = "\u00a7a%s\u00a7a %s \u00a7d%d \u00a7achunks pr\u00f3ximas\u00a7a.";
        }
        if (newFaction == null) {
            throw new NullPointerException("newFaction");
        }
        if (pss == null) {
            throw new NullPointerException("pss");
        }
        Set<PS> chunks = PS.getDistinctChunks(pss);
        Iterator<PS> iter = chunks.iterator();
        while (iter.hasNext()) {
            PS chunk = iter.next();
            Faction oldFaction = BoardColl.get().getFactionAt(chunk);
            if (newFaction != oldFaction) continue;
            iter.remove();
        }
        if (chunks.isEmpty()) {
            this.msg("\u00a7e%s\u00a7e j\u00e1 \u00e9 dona deste territ\u00f3rio.", new Object[]{newFaction.describeTo(this, true).replace("Sua fac\u00e7\u00e3o", "\u00a7eSua fac\u00e7\u00e3o")});
            return true;
        }
        CommandSender sender = this.getSender();
        if (sender == null) {
            this.msg("\u00a7cERROR: O seu \"CommandSender Link\" foi cortado/depurado.");
            this.msg("\u00a7c\u00c9 prov\u00e1vel que voc\u00ea esteja usando Cauldron.");
            this.msg("\u00a7cAtualmete o factions n\u00e3o suporta Cauldron.");
            this.msg("\u00a7cN\u00f3s adorar\u00edamos adicionar suporte ao Cauldron, mas infelizmente n\u00e3o temos tempo para desenvolver.");
            this.msg("\u00a7aVoc\u00ea sabe como codificar? Por favor, envie-nos um pull request <3, desculpe por tudo.");
            return false;
        }
        EventFactionsChunksChange event = new EventFactionsChunksChange(sender, chunks, newFaction);
        event.run();
        if (event.isCancelled()) {
            return false;
        }
        for (PS pS : chunks) {
            BoardColl.get().setFactionAt(pS, newFaction);
        }
        for (Map.Entry entry : event.getOldFactionChunks().entrySet()) {
            Faction oldFaction = (Faction)entry.getKey();
            Set oldChunks = (Set)entry.getValue();
            PS oldChunk = (PS)oldChunks.iterator().next();
            Set<MPlayer> informees = MPlayer.getClaimInformees(this, oldFaction, newFaction);
            EventFactionsChunkChangeType type = EventFactionsChunkChangeType.get(oldFaction, newFaction, this.getFaction());
            String chunkString = oldChunk.toString((PSFormat)PSFormatHumanSpace.get());
            String typeString = type.past;
            for (MPlayer informee : informees) {
                informee.msg(oldChunks.size() == 1 ? formatOne : formatMany, new Object[]{this.describeTo(informee, true), typeString, oldChunks.size(), chunkString});
                informee.msg("\u00a7f%s\u00a7e --> \u00a7d%s", new Object[]{oldFaction.describeTo(informee, true), newFaction.describeTo(informee, true)});
            }
        }
        return true;
    }

    public static Set<MPlayer> getClaimInformees(MPlayer msender, Faction ... factions) {
        HashSet<MPlayer> ret = new HashSet<MPlayer>();
        if (msender != null) {
            ret.add(msender);
        }
        Faction[] factionArray = factions;
        int n = factions.length;
        int n2 = 0;
        while (n2 < n) {
            Faction faction = factionArray[n2];
            if (faction != null && !faction.isNone()) {
                ret.addAll(faction.getMPlayers());
            }
            ++n2;
        }
        return ret;
    }
}

