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
 *  com.massivecraft.massivecore.store.Entity
 *  com.massivecraft.massivecore.store.EntityInternal
 */
package com.massivecraft.factions.entity;

import com.massivecraft.factions.entity.MFlagColl;
import com.massivecraft.factions.event.EventFactionsCreateFlags;
import com.massivecraft.massivecore.Named;
import com.massivecraft.massivecore.Prioritized;
import com.massivecraft.massivecore.Registerable;
import com.massivecraft.massivecore.comparator.ComparatorSmart;
import com.massivecraft.massivecore.predicate.Predicate;
import com.massivecraft.massivecore.predicate.PredicateIsRegistered;
import com.massivecraft.massivecore.store.Entity;
import com.massivecraft.massivecore.store.EntityInternal;
import java.util.Comparator;
import java.util.List;

public class MFlag
extends Entity<MFlag>
implements Prioritized,
Registerable,
Named {
    public static final transient String ID_POWERLOSS = "powerloss";
    public static final transient String ID_PVP = "pvp";
    public static final transient String ID_FRIENDLYFIRE = "friendlyfire";
    public static final transient String ID_EXPLOSIONS = "explosions";
    public static final transient String ID_PERMANENT = "permanent";
    public static final transient String ID_PEACEFUL = "peaceful";
    public static final transient String ID_INFPOWER = "infpower";
    public static final transient int PRIORITY_POWERLOSS = 3000;
    public static final transient int PRIORITY_PVP = 4000;
    public static final transient int PRIORITY_FRIENDLYFIRE = 5000;
    public static final transient int PRIORITY_EXPLOSIONS = 6000;
    public static final transient int PRIORITY_PERMANENT = 7000;
    public static final transient int PRIORITY_PEACEFUL = 8000;
    public static final transient int PRIORITY_INFPOWER = 9000;
    private transient boolean registered = false;
    private int priority = 0;
    private String nome = "defaultNome";
    private String desc = "defaultDesc";
    private boolean padrao = true;

    public static MFlag get(Object oid) {
        return (MFlag)MFlagColl.get().get(oid);
    }

    public static List<MFlag> getAll() {
        return MFlag.getAll(false);
    }

    public static List<MFlag> getAll(boolean isAsync) {
        MFlag.setupStandardFlags();
        new EventFactionsCreateFlags(isAsync).run();
        return MFlagColl.get().getAll((Predicate)PredicateIsRegistered.get(), (Comparator)ComparatorSmart.get());
    }

    public static void setupStandardFlags() {
        MFlag.getFlagPowerloss();
        MFlag.getFlagPvp();
        MFlag.getFlagFriendlyire();
        MFlag.getFlagPermanent();
        MFlag.getFlagPeaceful();
        MFlag.getFlagInfpower();
    }

    public static MFlag getFlagPowerloss() {
        return MFlag.getCreative(3000, ID_POWERLOSS, ID_POWERLOSS, "Ao morrer neste territ\u00f3rio, voc\u00ea ira perder poder? ", true);
    }

    public static MFlag getFlagPvp() {
        return MFlag.getCreative(4000, ID_PVP, ID_PVP, "O pvp podera acontecer no territ\u00f3rio da fac\u00e7\u00e3o?", true);
    }

    public static MFlag getFlagFriendlyire() {
        return MFlag.getCreative(5000, ID_FRIENDLYFIRE, ID_FRIENDLYFIRE, "Membros da fac\u00e7\u00e3o e aliados poder\u00e3o se bater no territ\u00f3rio da fac\u00e7\u00e3o?", false);
    }

    public static MFlag getFlagPermanent() {
        return MFlag.getCreative(7000, ID_PERMANENT, ID_PERMANENT, "A fac\u00e7\u00e3o n\u00e3o pode ser deletada (permanente)?", false);
    }

    public static MFlag getFlagPeaceful() {
        return MFlag.getCreative(8000, ID_PEACEFUL, ID_PEACEFUL, "A fac\u00e7\u00e3o esta em tr\u00e9gua com todos?", false);
    }

    public static MFlag getFlagInfpower() {
        return MFlag.getCreative(9000, ID_INFPOWER, ID_INFPOWER, "A fac\u00e7\u00e3o possui poder ifinito?", false);
    }

    public static MFlag getCreative(int priority, String id, String nome, String desc, boolean padrao) {
        MFlag ret = (MFlag)MFlagColl.get().get(id, false);
        if (ret != null) {
            ret.setRegistered(true);
            return ret;
        }
        ret = new MFlag(priority, nome, desc, padrao);
        MFlagColl.get().attach(ret, id);
        ret.setRegistered(true);
        ret.sync();
        return ret;
    }

    public MFlag load(MFlag that) {
        this.priority = that.priority;
        this.nome = that.nome;
        this.desc = that.desc;
        this.padrao = that.padrao;
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

    public MFlag setPriority(int priority) {
        this.priority = priority;
        this.changed();
        return this;
    }

    public String getNome() {
        return this.nome;
    }

    public MFlag setNome(String nome) {
        this.nome = nome;
        this.changed();
        return this;
    }

    public String getDesc() {
        return this.desc;
    }

    public MFlag setDesc(String desc) {
        this.desc = desc;
        this.changed();
        return this;
    }

    public boolean isPadrao() {
        return this.padrao;
    }

    public MFlag setPadrao(boolean padrao) {
        this.padrao = padrao;
        this.changed();
        return this;
    }

    public MFlag() {
    }

    public MFlag(int priority, String nome, String desc, boolean padrao) {
        this.priority = priority;
        this.nome = nome;
        this.desc = desc;
        this.padrao = padrao;
    }

    public String getName() {
        return null;
    }
}

