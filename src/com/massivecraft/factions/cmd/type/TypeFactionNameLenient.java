/*
 * Decompiled with CFR 0.152.
 */
package com.massivecraft.factions.cmd.type;

import com.massivecraft.factions.cmd.type.TypeFactionNameAbstract;

public class TypeFactionNameLenient
extends TypeFactionNameAbstract {
    private static TypeFactionNameLenient i = new TypeFactionNameLenient();

    public static TypeFactionNameLenient get() {
        return i;
    }

    public TypeFactionNameLenient() {
        super(false);
    }
}

