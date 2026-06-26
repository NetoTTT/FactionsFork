/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.collections.MassiveList
 *  com.massivecraft.massivecore.collections.MassiveMap
 *  com.massivecraft.massivecore.collections.MassiveMapDef
 *  com.massivecraft.massivecore.collections.MassiveSet
 *  com.massivecraft.massivecore.mixin.MixinMessage
 *  com.massivecraft.massivecore.predicate.Predicate
 *  com.massivecraft.massivecore.predicate.PredicateAnd
 *  com.massivecraft.massivecore.predicate.PredicateVisibleTo
 *  com.massivecraft.massivecore.ps.PS
 *  com.massivecraft.massivecore.store.Entity
 *  com.massivecraft.massivecore.store.EntityInternal
 *  com.massivecraft.massivecore.store.EntityInternalMap
 *  com.massivecraft.massivecore.store.SenderColl
 *  com.massivecraft.massivecore.util.IdUtil
 *  com.massivecraft.massivecore.util.MUtil
 *  com.massivecraft.massivecore.util.Txt
 *  org.bukkit.ChatColor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.massivecraft.factions.entity;

import com.massivecraft.factions.FactionsIndex;
import com.massivecraft.factions.FactionsParticipator;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.RelationParticipator;
import com.massivecraft.factions.entity.Board;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.Invitation;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.entity.MPlayerColl;
import com.massivecraft.factions.predicate.PredicateCommandSenderFaction;
import com.massivecraft.factions.predicate.PredicateMPlayerRole;
import com.massivecraft.factions.util.MiscUtil;
import com.massivecraft.factions.util.RelationUtil;
import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.collections.MassiveMap;
import com.massivecraft.massivecore.collections.MassiveMapDef;
import com.massivecraft.massivecore.collections.MassiveSet;
import com.massivecraft.massivecore.mixin.MixinMessage;
import com.massivecraft.massivecore.predicate.Predicate;
import com.massivecraft.massivecore.predicate.PredicateAnd;
import com.massivecraft.massivecore.predicate.PredicateVisibleTo;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.store.Entity;
import com.massivecraft.massivecore.store.EntityInternal;
import com.massivecraft.massivecore.store.EntityInternalMap;
import com.massivecraft.massivecore.store.SenderColl;
import com.massivecraft.massivecore.util.IdUtil;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.Txt;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Faction
extends Entity<Faction>
implements FactionsParticipator {
    public static final transient String NODESCRIPTION = Txt.parse((String)"\u00a77\u00a7oDescri\u00e7\u00e3o Indefinida");
    public static final transient String NOMOTD = Txt.parse((String)"\u00a77\u00a7oMensagem do dia indefinida.");
    public int version = 1;
    private String name = null;
    private String tag = null;
    private java.util.Map<String, String> shopSpawners = new java.util.HashMap<String, String>();
    private String description = null;
    private String motd = null;
    private long createdAtMillis = System.currentTimeMillis();
    private PS home = null;
    private Double powerBoost = null;
    private EntityInternalMap<Invitation> invitations = new EntityInternalMap((EntityInternal)this, Invitation.class);
    private MassiveMapDef<String, Rel> relationWishes = new MassiveMapDef();
    private MassiveMapDef<String, Boolean> flags = new MassiveMapDef();
    private MassiveMapDef<String, Set<Rel>> perms = new MassiveMapDef();

    public static Faction get(Object oid) {
        return (Faction)FactionColl.get().get(oid);
    }

    public void addShopSpawner(String locKey, String entityType) {
        if (shopSpawners == null) shopSpawners = new java.util.HashMap<String, String>();
        shopSpawners.put(locKey, entityType.toUpperCase());
        this.changed();
    }

    public String removeShopSpawner(String locKey) {
        if (shopSpawners == null) return null;
        String type = shopSpawners.remove(locKey);
        if (type != null) this.changed();
        return type;
    }

    public java.util.Map<String, String> getShopSpawners() {
        if (shopSpawners == null) shopSpawners = new java.util.HashMap<String, String>();
        return shopSpawners;
    }

    public double getSpawnerValue() {
        if (shopSpawners == null || shopSpawners.isEmpty()) return 0;
        java.util.Map<String, Double> values = MConf.get().spawnerValues;
        double total = 0;
        for (String type : shopSpawners.values()) {
            Double val = values.get(type.toUpperCase());
            if (val != null) total += val;
        }
        return total;
    }

    public Faction load(Faction that) {
        this.setName(that.name);
        this.setTag(that.tag);
        this.shopSpawners = that.shopSpawners != null ? new java.util.HashMap<String, String>(that.shopSpawners) : new java.util.HashMap<String, String>();
        this.setDescription(that.description);
        this.setMotd(that.motd);
        this.setCreatedAtMillis(that.createdAtMillis);
        this.setHome(that.home);
        this.setPowerBoost(that.powerBoost);
        this.invitations.load(that.invitations);
        this.setRelationWishes((Map<String, Rel>)that.relationWishes);
        this.setFlagIds((Map<String, Boolean>)that.flags);
        this.setPermIds((Map<String, Set<Rel>>)that.perms);
        return this;
    }

    public boolean isNone() {
        return this.getId().equals("none");
    }

    public boolean isNormal() {
        return !this.isNone();
    }

    public String getName() {
        String ret = this.name;
        if (MConf.get().factionNameForceUpperCase) {
            ret = ret.toUpperCase();
        }
        return ret;
    }

    public void setName(String name) {
        String target = name;
        if (MUtil.equals((Object)this.name, (Object)target)) {
            return;
        }
        this.name = target;
        this.changed();
    }

    public boolean hasTag() {
        return this.tag != null && !this.tag.isEmpty();
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = (tag == null || tag.isEmpty()) ? null : tag.toUpperCase();
        this.changed();
    }

    public String getComparisonName() {
        return MiscUtil.getComparisonString(this.getName());
    }

    public String getName(String prefix) {
        return String.valueOf(prefix) + this.getName();
    }

    public String getName(RelationParticipator observer) {
        if (observer == null) {
            return this.getName();
        }
        return this.getName(this.getColorTo(observer).toString());
    }

    public boolean hasDescription() {
        return this.description != null;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        String target = Faction.clean(description);
        if (MUtil.equals((Object)this.description, (Object)target)) {
            return;
        }
        this.description = target;
        this.changed();
    }

    public String getDescriptionDesc() {
        String motd = this.getDescription();
        if (motd == null) {
            motd = NODESCRIPTION;
        }
        return motd;
    }

    public boolean hasMotd() {
        return this.motd != null;
    }

    public String getMotd() {
        return this.motd;
    }

    public void setMotd(String motd) {
        String target = Faction.clean(motd);
        if (MUtil.equals((Object)this.motd, (Object)target)) {
            return;
        }
        this.motd = target;
        this.changed();
    }

    public String getMotdDesc() {
        return Faction.getMotdDesc(this.getMotd());
    }

    private static String getMotdDesc(String motd) {
        if (motd == null) {
            motd = NOMOTD;
        }
        return motd;
    }

    public List<Object> getMotdMessages() {
        MassiveList ret = new MassiveList();
        String title = "\u00a7bMensagem do Dia.";
        title = Txt.titleize((Object)title).toString();
        ret.add(title);
        String motd = String.valueOf(Txt.parse((String)"\u00a7e")) + this.getMotdDesc();
        ret.add(motd);
        ret.add("");
        return ret;
    }

    public long getCreatedAtMillis() {
        return this.createdAtMillis;
    }

    public void setCreatedAtMillis(long createdAtMillis) {
        long target = createdAtMillis;
        if (MUtil.equals((Object)this.createdAtMillis, (Object)createdAtMillis)) {
            return;
        }
        this.createdAtMillis = target;
        this.changed();
    }

    public long getAge() {
        return this.getAge(System.currentTimeMillis());
    }

    public long getAge(long now) {
        return now - this.getCreatedAtMillis();
    }

    public PS getHome() {
        this.verifyHomeIsValid();
        return this.home;
    }

    public void verifyHomeIsValid() {
        if (this.isValidHome(this.home)) {
            return;
        }
        this.home = null;
        this.changed();
        this.msg("\u00a7cA Home da sua fac\u00e7\u00e3o foi deleta pois ela n\u00e3o est\u00e1 mais em um territ\u00f3rio da sua fac\u00e7\u00e3o!");
    }

    public boolean isValidHome(PS ps) {
        if (ps == null) {
            return true;
        }
        if (!MConf.get().homesMustBeInClaimedTerritory) {
            return true;
        }
        return BoardColl.get().getFactionAt(ps) == this;
    }

    public boolean hasHome() {
        return this.getHome() != null;
    }

    public void setHome(PS home) {
        PS target = home;
        if (MUtil.equals((Object)this.home, (Object)target)) {
            return;
        }
        this.home = target;
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

    public EntityInternalMap<Invitation> getInvitations() {
        return this.invitations;
    }

    public boolean isInvited(String playerId) {
        return this.getInvitations().containsKey(playerId);
    }

    public boolean isInvited(MPlayer mplayer) {
        return this.isInvited(mplayer.getId());
    }

    public boolean uninvite(String playerId) {
        System.out.println(playerId);
        return this.getInvitations().detachId((Object)playerId) != null;
    }

    public boolean uninvite(MPlayer mplayer) {
        return this.uninvite(mplayer.getId());
    }

    public void invite(String playerId, Invitation invitation) {
        this.uninvite(playerId);
        this.invitations.attach(invitation, playerId);
    }

    public Map<String, Rel> getRelationWishes() {
        return this.relationWishes;
    }

    public void setRelationWishes(Map<String, Rel> relationWishes) {
        MassiveMapDef target = new MassiveMapDef(relationWishes);
        if (MUtil.equals(this.relationWishes, (Object)target)) {
            return;
        }
        this.relationWishes = target;
        this.changed();
    }

    public Rel getRelationWish(String factionId) {
        Rel ret = this.getRelationWishes().get(factionId);
        if (ret == null) {
            ret = Rel.NEUTRAL;
        }
        return ret;
    }

    public Rel getRelationWish(Faction faction) {
        return this.getRelationWish(faction.getId());
    }

    public void setRelationWish(String factionId, Rel rel) {
        Map<String, Rel> relationWishes = this.getRelationWishes();
        if (rel == null || rel == Rel.NEUTRAL) {
            relationWishes.remove(factionId);
        } else {
            relationWishes.put(factionId, rel);
        }
        this.setRelationWishes(relationWishes);
    }

    public void setRelationWish(Faction faction, Rel rel) {
        this.setRelationWish(faction.getId(), rel);
    }

    public Map<MFlag, Boolean> getFlags() {
        MassiveMap ret = new MassiveMap();
        for (MFlag mflag : MFlag.getAll()) {
            ret.put(mflag, mflag.isPadrao());
        }
        Iterator iter = this.flags.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            String id = (String)entry.getKey();
            if (id == null) {
                iter.remove();
                this.changed();
                continue;
            }
            MFlag mflag = MFlag.get(id);
            if (mflag == null) continue;
            ret.put(mflag, (Boolean)entry.getValue());
        }
        return ret;
    }

    public void setFlags(Map<MFlag, Boolean> flags) {
        MassiveMap flagIds = new MassiveMap();
        for (Map.Entry<MFlag, Boolean> entry : flags.entrySet()) {
            flagIds.put(entry.getKey().getId(), entry.getValue());
        }
        this.setFlagIds((Map<String, Boolean>)flagIds);
    }

    public void setFlagIds(Map<String, Boolean> flagIds) {
        MassiveMapDef target = new MassiveMapDef();
        for (Map.Entry<String, Boolean> entry : flagIds.entrySet()) {
            String key = entry.getKey();
            if (key == null) continue;
            key = key.toLowerCase();
            Boolean value = entry.getValue();
            if (value == null) continue;
            target.put((Object)key, (Object)value);
        }
        if (MUtil.equals(this.flags, (Object)target)) {
            return;
        }
        this.flags = new MassiveMapDef((Map)target);
        this.changed();
    }

    public boolean getFlag(String flagId) {
        if (flagId == null) {
            throw new NullPointerException("flagId");
        }
        Boolean ret = (Boolean)this.flags.get((Object)flagId);
        if (ret != null) {
            return ret;
        }
        MFlag flag = MFlag.get(flagId);
        if (flag == null) {
            throw new NullPointerException("flag");
        }
        return flag.isPadrao();
    }

    public boolean getFlag(MFlag flag) {
        if (flag == null) {
            throw new NullPointerException("flag");
        }
        String flagId = flag.getId();
        if (flagId == null) {
            throw new NullPointerException("flagId");
        }
        Boolean ret = (Boolean)this.flags.get((Object)flagId);
        if (ret != null) {
            return ret;
        }
        return flag.isPadrao();
    }

    public Boolean setFlag(String flagId, boolean value) {
        if (flagId == null) {
            throw new NullPointerException("flagId");
        }
        Boolean ret = this.flags.put(flagId, value);
        if (ret == null || ret != value) {
            this.changed();
        }
        return ret;
    }

    public Boolean setFlag(MFlag flag, boolean value) {
        if (flag == null) {
            throw new NullPointerException("flag");
        }
        String flagId = flag.getId();
        if (flagId == null) {
            throw new NullPointerException("flagId");
        }
        Boolean ret = this.flags.put(flagId, value);
        if (ret == null || ret != value) {
            this.changed();
        }
        return ret;
    }

    public Map<MPerm, Set<Rel>> getPerms() {
        MassiveMap ret = new MassiveMap();
        for (MPerm mperm : MPerm.getAll()) {
            ret.put(mperm, new MassiveSet(mperm.getStandard()));
        }
        Iterator iter = this.perms.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            String id = (String)entry.getKey();
            if (id == null) {
                iter.remove();
                continue;
            }
            MPerm mperm = MPerm.get(id);
            if (mperm == null) continue;
            ret.put(mperm, new MassiveSet((Collection)entry.getValue()));
        }
        return ret;
    }

    public void setPerms(Map<MPerm, Set<Rel>> perms) {
        MassiveMap permIds = new MassiveMap();
        for (Map.Entry<MPerm, Set<Rel>> entry : perms.entrySet()) {
            permIds.put(entry.getKey().getId(), entry.getValue());
        }
        this.setPermIds((Map<String, Set<Rel>>)permIds);
    }

    public void setPermIds(Map<String, Set<Rel>> perms) {
        MassiveMapDef target = new MassiveMapDef();
        for (Map.Entry<String, Set<Rel>> entry : perms.entrySet()) {
            String key = entry.getKey();
            if (key == null) continue;
            key = key.toLowerCase();
            Set<Rel> value = entry.getValue();
            if (value == null) continue;
            target.put((Object)key, value);
        }
        if (MUtil.equals(this.perms, (Object)target)) {
            return;
        }
        this.perms = target;
        this.changed();
    }

    public boolean isPermitted(String permId, Rel rel) {
        if (permId == null) {
            throw new NullPointerException("permId");
        }
        Set rels = (Set)this.perms.get((Object)permId);
        if (rels != null) {
            return rels.contains((Object)rel);
        }
        MPerm perm = MPerm.get(permId);
        if (perm == null) {
            throw new NullPointerException("perm");
        }
        return perm.getStandard().contains((Object)rel);
    }

    public boolean isPermitted(MPerm perm, Rel rel) {
        if (perm == null) {
            throw new NullPointerException("perm");
        }
        String permId = perm.getId();
        if (permId == null) {
            throw new NullPointerException("permId");
        }
        Set rels = (Set)this.perms.get((Object)permId);
        if (rels != null) {
            return rels.contains((Object)rel);
        }
        return perm.getStandard().contains((Object)rel);
    }

    public Set<Rel> getPermitted(MPerm perm) {
        if (perm == null) {
            throw new NullPointerException("perm");
        }
        String permId = perm.getId();
        if (permId == null) {
            throw new NullPointerException("permId");
        }
        Set rels = (Set)this.perms.get((Object)permId);
        if (rels != null) {
            return rels;
        }
        return perm.getStandard();
    }

    public Set<Rel> getPermitted(String permId) {
        if (permId == null) {
            throw new NullPointerException("permId");
        }
        Set rels = (Set)this.perms.get((Object)permId);
        if (rels != null) {
            return rels;
        }
        MPerm perm = MPerm.get(permId);
        if (perm == null) {
            throw new NullPointerException("perm");
        }
        return perm.getStandard();
    }

    @Deprecated
    public Set<Rel> getPermittedRelations(MPerm perm) {
        return this.getPerms().get((Object)perm);
    }

    public void setPermittedRelations(MPerm perm, Set<Rel> rels) {
        Map<MPerm, Set<Rel>> perms = this.getPerms();
        perms.put(perm, rels);
        this.setPerms(perms);
    }

    public void setPermittedRelations(MPerm perm, Rel ... rels) {
        HashSet<Rel> temp = new HashSet<Rel>();
        temp.addAll(Arrays.asList(rels));
        this.setPermittedRelations(perm, temp);
    }

    public void setRelationPermitted(MPerm perm, Rel rel, boolean permitted) {
        Map<MPerm, Set<Rel>> perms = this.getPerms();
        Set<Rel> rels = perms.get((Object)perm);
        boolean changed = permitted ? rels.add(rel) : rels.remove((Object)rel);
        this.setPerms(perms);
        if (changed) {
            this.changed();
        }
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

    public double getPower() {
        if (this.getFlag(MFlag.getFlagInfpower())) {
            return 9999.0;
        }
        double ret = 0.0;
        for (MPlayer mplayer : this.getMPlayers()) {
            ret += mplayer.getPower();
        }
        ret = this.limitWithPowerMax(ret);
        return ret += this.getPowerBoost();
    }

    public double getPowerMax() {
        if (this.getFlag(MFlag.getFlagInfpower())) {
            return 9999.0;
        }
        double ret = 0.0;
        for (MPlayer mplayer : this.getMPlayers()) {
            ret += mplayer.getPowerMax();
        }
        ret = this.limitWithPowerMax(ret);
        return ret += this.getPowerBoost();
    }

    private double limitWithPowerMax(double power) {
        double powerMax = MConf.get().factionPowerMax;
        return powerMax <= 0.0 || power < powerMax ? power : powerMax;
    }

    public int getPowerRounded() {
        return (int)Math.round(this.getPower());
    }

    public int getPowerMaxRounded() {
        return (int)Math.round(this.getPowerMax());
    }

    public int getLandCount() {
        return BoardColl.get().getCount(this);
    }

    public int getLandCountInWorld(String worldName) {
        return Board.get(worldName).getCount(this);
    }

    public boolean hasLandInflation() {
        return this.getLandCount() > this.getPowerRounded();
    }

    public Set<String> getClaimedWorlds() {
        return BoardColl.get().getClaimedWorlds(this);
    }

    public List<MPlayer> getMPlayers() {
        return new MassiveList(FactionsIndex.get().getMPlayers(this));
    }

    public List<MPlayer> getMPlayers(Predicate<? super MPlayer> where, Comparator<? super MPlayer> orderby, Integer limit, Integer offset) {
        return MUtil.transform(this.getMPlayers(), where, orderby, (Integer)limit, (Integer)offset);
    }

    public List<MPlayer> getMPlayersWhere(Predicate<? super MPlayer> predicate) {
        return this.getMPlayers(predicate, null, null, null);
    }

    public List<MPlayer> getMPlayersWhereOnline(boolean online) {
        return this.getMPlayersWhere((Predicate<? super MPlayer>)(online ? SenderColl.PREDICATE_ONLINE : SenderColl.PREDICATE_OFFLINE));
    }

    public List<MPlayer> getMPlayersWhereOnlineTo(Object senderObject) {
        return this.getMPlayersWhere((Predicate<? super MPlayer>)PredicateAnd.get((Predicate[])new Predicate[]{SenderColl.PREDICATE_ONLINE, PredicateVisibleTo.get((Object)senderObject)}));
    }

    public List<MPlayer> getMPlayersWhereRole(Rel role) {
        return this.getMPlayersWhere(PredicateMPlayerRole.get(role));
    }

    public MPlayer getLeader() {
        List<MPlayer> ret = this.getMPlayersWhereRole(Rel.LEADER);
        if (ret.size() == 0) {
            return null;
        }
        return ret.get(0);
    }

    public List<CommandSender> getOnlineCommandSenders() {
        MassiveList ret = new MassiveList();
        for (CommandSender sender : IdUtil.getLocalSenders()) {
            MPlayer mplayer;
            if (MUtil.isntSender((Object)sender) || (mplayer = MPlayer.get(sender)).getFaction() != this) continue;
            ret.add(sender);
        }
        return ret;
    }

    public List<Player> getOnlinePlayers() {
        MassiveList ret = new MassiveList();
        for (Player player : MUtil.getOnlinePlayers()) {
            MPlayer mplayer;
            if (MUtil.isntPlayer((Object)player) || (mplayer = MPlayer.get(player)).getFaction() != this) continue;
            ret.add(player);
        }
        return ret;
    }

    public void promoteNewLeader() {
        if (!this.isNormal()) {
            return;
        }
        if (this.getFlag(MFlag.getFlagPermanent()) && MConf.get().permanentFactionsDisableLeaderPromotion) {
            return;
        }
        MPlayer oldLeader = this.getLeader();
        List<MPlayer> replacements = this.getMPlayersWhereRole(Rel.OFFICER);
        if (replacements == null || replacements.isEmpty()) {
            replacements = this.getMPlayersWhereRole(Rel.MEMBER);
        }
        if (replacements == null || replacements.isEmpty()) {
            if (this.getFlag(MFlag.getFlagPermanent())) {
                if (oldLeader != null) {
                    oldLeader.setRole(Rel.MEMBER);
                }
                return;
            }
            for (MPlayer mplayer : MPlayerColl.get().getAllOnline()) {
                mplayer.msg("\u00a7eA fac\u00e7\u00e3o %s\u00a7e foi desfeita.", new Object[]{this.getName(mplayer)});
            }
            this.detach();
        } else {
            if (oldLeader != null) {
                oldLeader.setRole(Rel.MEMBER);
            }
            replacements.get(0).setRole(Rel.LEADER);
            this.msg("\u00a7e\"%s\"\u00a7e n\u00e3o \u00e9 mais o l\u00edder da fac\u00e7\u00e3o, \u00a7e\"%s\"\u00a7e agora \u00e9 o novo l\u00edder da fac\u00e7\u00e3o!", oldLeader == null ? "" : oldLeader.getName(), replacements.get(0).getName());
        }
    }

    public boolean isAllMPlayersOffline() {
        return this.getMPlayersWhereOnline(true).size() == 0;
    }

    public boolean isAnyMPlayersOnline() {
        return !this.isAllMPlayersOffline();
    }

    public boolean sendMessage(Object message) {
        return MixinMessage.get().messagePredicate((Predicate)new PredicateCommandSenderFaction(this), message);
    }

    public boolean sendMessage(Object ... messages) {
        return MixinMessage.get().messagePredicate((Predicate)new PredicateCommandSenderFaction(this), messages);
    }

    public boolean sendMessage(Collection<Object> messages) {
        return MixinMessage.get().messagePredicate((Predicate)new PredicateCommandSenderFaction(this), messages);
    }

    public boolean msg(String msg) {
        return MixinMessage.get().msgPredicate((Predicate)new PredicateCommandSenderFaction(this), msg);
    }

    public boolean msg(String msg, Object ... args) {
        return MixinMessage.get().msgPredicate((Predicate)new PredicateCommandSenderFaction(this), msg, args);
    }

    public boolean msg(Collection<String> msgs) {
        return MixinMessage.get().msgPredicate((Predicate)new PredicateCommandSenderFaction(this), msgs);
    }

    public static String clean(String message) {
        String target = message;
        if (target == null) {
            return null;
        }
        if ((target = target.trim()).isEmpty()) {
            target = null;
        }
        return target;
    }
}

