/*
 * Decompiled with CFR 0.152.
 */
package com.massivecraft.factions.cmd.type;

import com.massivecraft.factions.cmd.type.TypeFactionNameAbstract;

public class TypeFactionNameStrict
extends TypeFactionNameAbstract {
    private static TypeFactionNameStrict i = new TypeFactionNameStrict();

    public static TypeFactionNameStrict get() {
        return i;
    }

    public TypeFactionNameStrict() {
        super(true);
    }
}

