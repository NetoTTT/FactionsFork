/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 */
package com.massivecraft.factions.util;

import java.util.Arrays;
import java.util.HashSet;
import org.bukkit.ChatColor;

public class MiscUtil {
    public static HashSet<String> substanceChars = new HashSet<String>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"));

    public static long[] range(long start, long end) {
        long[] values = new long[(int)Math.abs(end - start) + 1];
        if (end < start) {
            long oldstart = start;
            start = end;
            end = oldstart;
        }
        long i = start;
        while (i <= end) {
            values[(int)(i - start)] = i;
            ++i;
        }
        return values;
    }

    public static String getComparisonString(String str) {
        String ret = "";
        str = ChatColor.stripColor((String)str);
        str = str.toLowerCase();
        char[] cArray = str.toCharArray();
        int n = cArray.length;
        int n2 = 0;
        while (n2 < n) {
            char c = cArray[n2];
            if (substanceChars.contains(String.valueOf(c))) {
                ret = String.valueOf(ret) + c;
            }
            ++n2;
        }
        return ret.toLowerCase();
    }
}

