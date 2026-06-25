/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.Colorized
 *  com.massivecraft.massivecore.Named
 *  com.massivecraft.massivecore.collections.MassiveSet
 *  org.bukkit.ChatColor
 */
package com.massivecraft.factions;

import com.massivecraft.factions.entity.MConf;
import com.massivecraft.massivecore.Colorized;
import com.massivecraft.massivecore.Named;
import com.massivecraft.massivecore.collections.MassiveSet;
import java.util.Collections;
import java.util.Set;
import org.bukkit.ChatColor;

public enum Rel implements Colorized,
Named
{
    ENEMY("um inimigo", "inimigos", "\u00a7euma fac\u00e7\u00e3o \u00a7cinimiga", "fac\u00e7\u00e3o inimiga", new String[]{"Inimigo"}){

        @Override
        public ChatColor getColor() {
            return MConf.get().colorEnemy;
        }
    }
    ,
    NEUTRAL("um neutro", "neutros", "\u00a7euma fac\u00e7\u00e3o \u00a7fneutra", "fac\u00e7\u00e3o neutra", new String[]{"Neutro"}){

        @Override
        public ChatColor getColor() {
            return MConf.get().colorNeutral;
        }
    }
    ,
    ALLY("um aliado", "aliados", "\u00a7euma fac\u00e7\u00e3o \u00a7baliada", "fac\u00e7\u00e3o aliada", new String[]{"Aliado"}){

        @Override
        public ChatColor getColor() {
            return MConf.get().colorAlly;
        }
    }
    ,
    TRUCE("um tr\u00e9gua", "tr\u00e9guas", "\u00a7euma fac\u00e7\u00e3o em \u00a78tr\u00e9gua", "fac\u00e7\u00e3o em tr\u00e9gua", new String[]{""}){

        @Override
        public ChatColor getColor() {
            return MConf.get().colorTruce;
        }
    }
    ,
    RECRUIT("um recruta da sua fac\u00e7\u00e3o", "recrutas da sua fac\u00e7\u00e3o", "", "", new String[]{"Recruta"}){

        @Override
        public String getPrefix() {
            return MConf.get().prefixRecruit;
        }
    }
    ,
    MEMBER("um membro da sua fac\u00e7\u00e3o", "membros da sua fac\u00e7\u00e3o", "sua fac\u00e7\u00e3o", "suas fac\u00e7\u00f5es", new String[]{"Membro"}){

        @Override
        public String getPrefix() {
            return MConf.get().prefixMember;
        }
    }
    ,
    OFFICER("um capit\u00e3o da sua fac\u00e7\u00e3o", "capit\u00f5es da sua fac\u00e7\u00e3o", "", "", new String[]{"Capit\u00e3o", "Capitao"}){

        @Override
        public String getPrefix() {
            return MConf.get().prefixOfficer;
        }
    }
    ,
    LEADER("l\u00edder da sua fac\u00e7\u00e3o", "lider da sua fac\u00e7\u00e3o", "", "", new String[]{"L\u00edder", "Lider", "Dono"}){

        @Override
        public String getPrefix() {
            return MConf.get().prefixLeader;
        }
    };

    private final String descPlayerOne;
    private final String descPlayerMany;
    private final String descFactionOne;
    private final String descFactionMany;
    private final Set<String> names;

    public int getValue() {
        return this.ordinal();
    }

    public String getDescPlayerOne() {
        return this.descPlayerOne;
    }

    public String getDescPlayerMany() {
        return this.descPlayerMany;
    }

    public String getDescFactionOne() {
        return this.descFactionOne;
    }

    public String getDescFactionMany() {
        return this.descFactionMany;
    }

    public Set<String> getNames() {
        return this.names;
    }

    public String getName() {
        return this.getNames().iterator().next();
    }

    private Rel(String descPlayerOne, String descPlayerMany, String descFactionOne, String descFactionMany, String ... names) {
        this.descPlayerOne = descPlayerOne;
        this.descPlayerMany = descPlayerMany;
        this.descFactionOne = descFactionOne;
        this.descFactionMany = descFactionMany;
        this.names = Collections.unmodifiableSet(new MassiveSet((Object[])names));
    }

    public ChatColor getColor() {
        return MConf.get().colorMember;
    }

    public boolean isAtLeast(Rel rel) {
        return this.getValue() >= rel.getValue();
    }

    public boolean isAtMost(Rel rel) {
        return this.getValue() <= rel.getValue();
    }

    public boolean isLessThan(Rel rel) {
        return this.getValue() < rel.getValue();
    }

    public boolean isMoreThan(Rel rel) {
        return this.getValue() > rel.getValue();
    }

    public boolean isRank() {
        return this.isAtLeast(RECRUIT);
    }

    public boolean isFriend() {
        return this.isAtLeast(TRUCE);
    }

    public String getPrefix() {
        return "";
    }

    /* synthetic */ Rel(String string, int n, String string2, String string3, String string4, String string5, String[] stringArray, Rel rel) {
        this(string2, string3, string4, string5, stringArray);
    }
}

