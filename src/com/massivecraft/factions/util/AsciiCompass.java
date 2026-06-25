/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.collections.MassiveList
 */
package com.massivecraft.factions.util;

import com.massivecraft.factions.util.AsciiCompassDirection;
import com.massivecraft.massivecore.collections.MassiveList;
import java.util.List;

public class AsciiCompass {
    public static List<String> getAsciiCompass(double degrees) {
        return AsciiCompass.getAsciiCompass(AsciiCompassDirection.getByDegrees(degrees));
    }

    private static List<String> getAsciiCompass(AsciiCompassDirection directionFacing) {
        MassiveList ret = new MassiveList();
        ret.add(AsciiCompass.visualizeRow(directionFacing, AsciiCompassDirection.NW, AsciiCompassDirection.N, AsciiCompassDirection.NE));
        ret.add(AsciiCompass.visualizeRow(directionFacing, AsciiCompassDirection.W, AsciiCompassDirection.NONE, AsciiCompassDirection.E));
        ret.add(AsciiCompass.visualizeRow(directionFacing, AsciiCompassDirection.SW, AsciiCompassDirection.S, AsciiCompassDirection.SE));
        return ret;
    }

    private static String visualizeRow(AsciiCompassDirection directionFacing, AsciiCompassDirection ... cardinals) {
        if (cardinals == null) {
            throw new NullPointerException("cardinals");
        }
        StringBuilder ret = new StringBuilder(cardinals.length);
        AsciiCompassDirection[] asciiCompassDirectionArray = cardinals;
        int n = cardinals.length;
        int n2 = 0;
        while (n2 < n) {
            AsciiCompassDirection asciiCardinal = asciiCompassDirectionArray[n2];
            ret.append(asciiCardinal.visualize(directionFacing));
            ++n2;
        }
        return ret.toString();
    }
}

