/*
 * Decompiled with CFR 0.152.
 */
package com.massivecraft.factions.cmd.type;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.cmd.type.TypeRel;

public class TypeRelation
extends TypeRel {
    private static TypeRelation i = new TypeRelation();

    public static TypeRelation get() {
        return i;
    }

    public TypeRelation() {
        this.setAll(Rel.NEUTRAL, Rel.TRUCE, Rel.ALLY, Rel.ENEMY);
    }

    @Override
    public String getName() {
        return "relation";
    }
}

