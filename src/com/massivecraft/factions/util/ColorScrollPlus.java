/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 */
package com.massivecraft.factions.util;

import org.bukkit.ChatColor;

public class ColorScrollPlus {
    private int position;
    private String str;
    private String colorBefore;
    private String colorAfter;
    private String colorMid;
    private boolean bold;
    private String textColor;
    private boolean upperCaseMid;
    private ScrollType scrollType;

    public ColorScrollPlus(String textColor, String str, String colorMid, String colorBefore, String colorAfter, boolean bold, boolean upperCaseMid, ScrollType scrollType) {
        this.str = str;
        this.colorMid = colorMid;
        this.bold = bold;
        this.colorBefore = colorBefore;
        this.colorAfter = colorAfter;
        this.textColor = textColor;
        this.upperCaseMid = upperCaseMid;
        this.scrollType = scrollType;
        this.position = scrollType == ScrollType.FORWARD ? -1 : str.length();
    }

    public String getString() {
        return this.str;
    }

    public String next() {
        if (this.position >= this.str.length()) {
            String one = this.str.substring(this.position - 1, this.str.length() - 1);
            if (this.bold) {
                String two = this.upperCaseMid ? String.valueOf(this.colorMid) + ChatColor.BOLD + one.toUpperCase() : String.valueOf(this.colorMid) + ChatColor.BOLD + one;
                String fin = String.valueOf(this.textColor) + ChatColor.BOLD + this.str.substring(0, this.str.length() - 1) + this.colorBefore + ChatColor.BOLD + this.str.substring(this.str.length() - 1, this.str.length()) + two;
                this.position = this.getScrollType() == ScrollType.FORWARD ? -1 : --this.position;
                return fin;
            }
            String two = this.upperCaseMid ? String.valueOf(this.colorMid) + one.toUpperCase() : String.valueOf(this.colorMid) + one;
            String fin = String.valueOf(this.textColor) + this.str.substring(0, this.str.length() - 1) + this.colorBefore + this.str.substring(this.str.length() - 1, this.str.length()) + two;
            this.position = this.getScrollType() == ScrollType.FORWARD ? -1 : --this.position;
            return fin;
        }
        if (this.position <= -1) {
            this.position = this.getScrollType() == ScrollType.FORWARD ? ++this.position : this.str.length();
            if (this.bold) {
                return String.valueOf(this.colorBefore) + ChatColor.BOLD + this.str.substring(0, 1) + this.textColor + ChatColor.BOLD + this.str.substring(1, this.str.length());
            }
            return String.valueOf(this.colorBefore) + this.str.substring(0, 1) + this.textColor + this.str.substring(1, this.str.length());
        }
        if (this.position == 0) {
            String one = this.str.substring(0, 1);
            if (this.bold) {
                String two = this.upperCaseMid ? String.valueOf(this.colorMid) + ChatColor.BOLD + one.toUpperCase() : String.valueOf(this.colorMid) + ChatColor.BOLD + one;
                String fin = String.valueOf(two) + this.colorAfter + ChatColor.BOLD + this.str.substring(1, 2) + this.textColor + ChatColor.BOLD + this.str.substring(2, this.str.length());
                this.position = this.getScrollType() == ScrollType.FORWARD ? ++this.position : --this.position;
                return fin;
            }
            String two = this.upperCaseMid ? String.valueOf(this.colorMid) + one.toUpperCase() : String.valueOf(this.colorMid) + one;
            String fin = String.valueOf(two) + this.colorAfter + this.str.substring(1, 2) + this.textColor + this.str.substring(2, this.str.length());
            this.position = this.getScrollType() == ScrollType.FORWARD ? ++this.position : --this.position;
            return fin;
        }
        if (this.position >= 1) {
            String one = this.str.substring(0, this.position);
            String two = this.str.substring(this.position + 1, this.str.length());
            if (this.bold) {
                String three = this.upperCaseMid ? String.valueOf(this.colorMid) + ChatColor.BOLD + this.str.substring(this.position, this.position + 1).toUpperCase() : String.valueOf(this.colorMid) + ChatColor.BOLD + this.str.substring(this.position, this.position + 1);
                String fin = null;
                int m = one.length();
                int l = two.length();
                String first = m <= 1 ? String.valueOf(this.colorBefore) + ChatColor.BOLD + one : ChatColor.BOLD + one.substring(0, one.length() - 1) + this.colorBefore + ChatColor.BOLD + one.substring(one.length() - 1, one.length());
                String second = l <= 1 ? String.valueOf(this.colorAfter) + ChatColor.BOLD + two : String.valueOf(this.colorAfter) + ChatColor.BOLD + two.substring(0, 1) + this.textColor + ChatColor.BOLD + two.substring(1, two.length());
                fin = String.valueOf(this.textColor) + first + three + second;
                this.position = this.getScrollType() == ScrollType.FORWARD ? ++this.position : --this.position;
                return fin;
            }
            String three = this.upperCaseMid ? String.valueOf(this.colorMid) + this.str.substring(this.position, this.position + 1).toUpperCase() : String.valueOf(this.colorMid) + this.str.substring(this.position, this.position + 1);
            String fin = null;
            int m = one.length();
            int l = two.length();
            String first = m <= 1 ? String.valueOf(this.colorBefore) + one : String.valueOf(one.substring(0, one.length() - 1)) + this.colorBefore + one.substring(one.length() - 1, one.length());
            String second = l <= 1 ? String.valueOf(this.colorAfter) + two : String.valueOf(this.colorAfter) + two.substring(0, 1) + this.textColor + two.substring(1, two.length());
            fin = String.valueOf(this.textColor) + first + three + second;
            this.position = this.getScrollType() == ScrollType.FORWARD ? ++this.position : --this.position;
            return fin;
        }
        return null;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int stringLength() {
        return this.str.length();
    }

    public String getTextColor() {
        return this.textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public boolean isUpperCaseMid() {
        return this.upperCaseMid;
    }

    public void setUpperCaseMid(boolean upperCaseMid) {
        this.upperCaseMid = upperCaseMid;
    }

    public ScrollType getScrollType() {
        return this.scrollType;
    }

    public void setScrollType(ScrollType scrollType) {
        this.scrollType = scrollType;
    }

    public static enum ScrollType {
        FORWARD,
        BACKWARD;

    }
}

