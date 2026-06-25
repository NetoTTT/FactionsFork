/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.Named
 *  com.massivecraft.massivecore.Prioritized
 *  com.massivecraft.massivecore.Registerable
 *  com.massivecraft.massivecore.comparator.ComparatorSmart
 *  com.massivecraft.massivecore.predicate.Predicate
 *  com.massivecraft.massivecore.predicate.PredicateIsRegistered
 *  com.massivecraft.massivecore.ps.PS
 *  com.massivecraft.massivecore.store.Entity
 *  com.massivecraft.massivecore.store.EntityInternal
 *  com.massivecraft.massivecore.util.MUtil
 *  com.massivecraft.massivecore.util.Txt
 *  org.bukkit.entity.Player
 *  org.bukkit.permissions.Permissible
 */
package com.massivecraft.factions.entity;

import com.massivecraft.factions.AccessStatus;
import com.massivecraft.factions.Perm;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.TerritoryAccess;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPermColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsCreatePerms;
import com.massivecraft.massivecore.Named;
import com.massivecraft.massivecore.Prioritized;
import com.massivecraft.massivecore.Registerable;
import com.massivecraft.massivecore.comparator.ComparatorSmart;
import com.massivecraft.massivecore.predicate.Predicate;
import com.massivecraft.massivecore.predicate.PredicateIsRegistered;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.store.Entity;
import com.massivecraft.massivecore.store.EntityInternal;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.Txt;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;

public class MPerm
extends Entity<MPerm>
implements Prioritized,
Registerable,
Named {
    public static final transient String ID_BUILD = "build";
    public static final transient String ID_DOOR = "door";
    public static final transient String ID_BUTTON = "button";
    public static final transient String ID_LEVER = "lever";
    public static final transient String ID_CONTAINER = "container";
    public static final transient String ID_TERRITORY = "territory";
    public static final transient String ID_HOME = "home";
    public static final transient String ID_CLAIMNEAR = "claimnear";
    public static final transient int PRIORITY_BUILD = 1000;
    public static final transient int PRIORITY_DOOR = 2000;
    public static final transient int PRIORITY_BUTTON = 3000;
    public static final transient int PRIORITY_LEVER = 4000;
    public static final transient int PRIORITY_CONTAINER = 5000;
    public static final transient int PRIORITY_TERRITORY = 6000;
    public static final transient int PRIORITY_HOME = 7000;
    public static final transient int PRIORITY_CLAIMNEAR = 8000;
    private transient boolean registered = false;
    private int priority = 0;
    private String name = "defaultName";
    private String desc = "defaultDesc";
    private Set<Rel> standard = new LinkedHashSet<Rel>();
    private boolean territory = false;

    public static MPerm get(Object oid) {
        return (MPerm)MPermColl.get().get(oid);
    }

    public static List<MPerm> getAll() {
        return MPerm.getAll(false);
    }

    public static List<MPerm> getAll(boolean isAsync) {
        MPerm.setupStandardPerms();
        new EventFactionsCreatePerms().run();
        return MPermColl.get().getAll((Predicate)PredicateIsRegistered.get(), (Comparator)ComparatorSmart.get());
    }

    public static void setupStandardPerms() {
        MPerm.getPermBuild();
        MPerm.getPermDoor();
        MPerm.getPermButton();
        MPerm.getPermLever();
        MPerm.getPermContainer();
        MPerm.getPermTerritory();
        MPerm.getPermHome();
        MPerm.getPermClaimnear();
    }

    public static MPerm getPermBuild() {
        return MPerm.getCreative(1000, ID_BUILD, ID_BUILD, "editar o terreno", MUtil.set(Rel.LEADER, Rel.OFFICER, Rel.MEMBER), true);
    }

    public static MPerm getPermDoor() {
        return MPerm.getCreative(2000, ID_DOOR, ID_DOOR, "usar portas", MUtil.set(Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.RECRUIT, Rel.ALLY), true);
    }

    public static MPerm getPermButton() {
        return MPerm.getCreative(3000, ID_BUTTON, ID_BUTTON, "usar bot\u00f5es", MUtil.set(Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.RECRUIT, Rel.ALLY), true);
    }

    public static MPerm getPermLever() {
        return MPerm.getCreative(4000, ID_LEVER, ID_LEVER, "usar alavancas", MUtil.set(Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.RECRUIT, Rel.ALLY), true);
    }

    public static MPerm getPermContainer() {
        return MPerm.getCreative(5000, ID_CONTAINER, ID_CONTAINER, "usar containers", MUtil.set(Rel.LEADER, Rel.OFFICER, Rel.MEMBER), true);
    }

    public static MPerm getPermHome() {
        return MPerm.getCreative(7000, ID_HOME, ID_HOME, "se teleportar para a home", MUtil.set(Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.RECRUIT, Rel.ALLY), false);
    }

    public static MPerm getPermTerritory() {
        return MPerm.getCreative(6000, ID_TERRITORY, ID_TERRITORY, "administrar os territ\u00f3rios", MUtil.set(Rel.LEADER, Rel.OFFICER), false);
    }

    public static MPerm getPermClaimnear() {
        return MPerm.getCreative(8000, ID_CLAIMNEAR, ID_CLAIMNEAR, "claimar perto", MUtil.set(Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.RECRUIT), false);
    }

    public static MPerm getCreative(int priority, String id, String name, String desc, Set<Rel> standard, boolean territory) {
        MPerm ret = (MPerm)MPermColl.get().get(id, false);
        if (ret != null) {
            ret.setRegistered(true);
            return ret;
        }
        ret = new MPerm(priority, name, desc, standard, territory);
        MPermColl.get().attach(ret, id);
        ret.setRegistered(true);
        ret.sync();
        return ret;
    }

    public MPerm load(MPerm that) {
        this.priority = that.priority;
        this.name = that.name;
        this.desc = that.desc;
        this.standard = that.standard;
        this.territory = that.territory;
        return this;
    }

    public boolean isRegistered() {
        return this.registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public int getPriority() {
        return this.priority;
    }

    public MPerm setPriority(int priority) {
        this.priority = priority;
        this.changed();
        return this;
    }

    public String getName() {
        return this.name;
    }

    public MPerm setName(String name) {
        this.name = name;
        this.changed();
        return this;
    }

    public String getDesc() {
        return this.desc;
    }

    public MPerm setDesc(String desc) {
        this.desc = desc;
        this.changed();
        return this;
    }

    public Set<Rel> getStandard() {
        return this.standard;
    }

    public MPerm setStandard(Set<Rel> standard) {
        this.standard = standard;
        this.changed();
        return this;
    }

    public boolean isTerritory() {
        return this.territory;
    }

    public MPerm setTerritory(boolean territory) {
        this.territory = territory;
        this.changed();
        return this;
    }

    public MPerm() {
    }

    public MPerm(int priority, String name, String desc, Set<Rel> standard, boolean territory) {
        this.priority = priority;
        this.name = name;
        this.desc = desc;
        this.standard = standard;
        this.territory = territory;
    }

    public String createDeniedMessage(MPlayer mplayer, Faction hostFaction) {
        if (mplayer == null) {
            throw new NullPointerException("mplayer");
        }
        if (hostFaction == null) {
            throw new NullPointerException("hostFaction");
        }
        String ret = Txt.parse((String)"%s\u00a7c n\u00e3o deixa voc\u00ea \u00a7c%s\u00a7c.", (Object[])new Object[]{hostFaction.describeTo(mplayer, true), this.getDesc()}).replace("\u00a7a", "\u00a7c");
        Player player = mplayer.getPlayer();
        if (player != null && Perm.ADMIN.has((Permissible)player)) {
            ret = String.valueOf(ret) + Txt.parse((String)"\n\u00a7eVoc\u00ea pode burlar isso usando o comando \u00a76/f admin\u00a7e");
        }
        return ret;
    }

    public boolean has(Faction faction, Faction hostFaction) {
        if (faction == null) {
            throw new NullPointerException("faction");
        }
        if (hostFaction == null) {
            throw new NullPointerException("hostFaction");
        }
        Rel rel = faction.getRelationTo(hostFaction);
        return hostFaction.isPermitted(this, rel);
    }

    public boolean has(MPlayer mplayer, Faction hostFaction, boolean verboose) {
        if (mplayer == null) {
            throw new NullPointerException("mplayer");
        }
        if (hostFaction == null) {
            throw new NullPointerException("hostFaction");
        }
        if (mplayer.isOverriding()) {
            return true;
        }
        Rel rel = mplayer.getRelationTo(hostFaction);
        if (hostFaction.isPermitted(this, rel)) {
            return true;
        }
        if (verboose) {
            mplayer.message(this.createDeniedMessage(mplayer, hostFaction));
        }
        return false;
    }

    public boolean has(MPlayer mplayer, PS ps, boolean verboose) {
        AccessStatus accessStatus;
        if (mplayer == null) {
            throw new NullPointerException("mplayer");
        }
        if (ps == null) {
            throw new NullPointerException("ps");
        }
        if (mplayer.isOverriding()) {
            return true;
        }
        TerritoryAccess ta = BoardColl.get().getTerritoryAccessAt(ps);
        Faction hostFaction = ta.getHostFaction();
        if (this.isTerritory() && (accessStatus = ta.getTerritoryAccess(mplayer)) != AccessStatus.STANDARD) {
            if (verboose && accessStatus == AccessStatus.DECREASED) {
                mplayer.message(this.createDeniedMessage(mplayer, hostFaction));
            }
            return accessStatus.hasAccess();
        }
        return this.has(mplayer, hostFaction, verboose);
    }

    public static String getStateHeaders() {
        String ret = "";
        Rel[] relArray = Rel.values();
        int n = relArray.length;
        int n2 = 0;
        while (n2 < n) {
            Rel rel = relArray[n2];
            ret = String.valueOf(ret) + rel.getColor().toString();
            ret = String.valueOf(ret) + rel.toString().substring(0, 3);
            ret = String.valueOf(ret) + " ";
            ++n2;
        }
        return ret;
    }
}

