/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Color
 *  org.bukkit.Material
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.LeatherArmorMeta
 *  org.bukkit.inventory.meta.SkullMeta
 */
package com.massivecraft.factions.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemBuilder {
    private ItemStack is;

    public ItemBuilder(Material m) {
        this(m, 1);
    }

    public ItemBuilder(ItemStack is) {
        this.is = is;
    }

    public ItemBuilder(Material m, int quantia) {
        this.is = new ItemStack(m, quantia);
    }

    public ItemBuilder(Material m, int quantia, byte durabilidade) {
        this.is = new ItemStack(m, quantia, (short)durabilidade);
    }

    public ItemBuilder(Material m, int quantia, int durabilidade) {
        this.is = new ItemStack(m, quantia, (short)durabilidade);
    }

    public ItemBuilder clone() {
        return new ItemBuilder(this.is);
    }

    public ItemBuilder setAmount(int amount) {
        this.is.setAmount(amount);
        ItemMeta im = this.is.getItemMeta();
        im.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_POTION_EFFECTS});
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setName(String nome) {
        ItemMeta im = this.is.getItemMeta();
        im.setDisplayName(nome);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addUnsafeEnchantment(Enchantment ench, int level) {
        this.is.addUnsafeEnchantment(ench, level);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment ench) {
        this.is.removeEnchantment(ench);
        return this;
    }

    public ItemBuilder setSkullOwner(String dono) {
        try {
            SkullMeta im = (SkullMeta)this.is.getItemMeta();
            im.setOwner(dono);
            this.is.setItemMeta((ItemMeta)im);
        }
        catch (ClassCastException classCastException) {
            // empty catch block
        }
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag flag) {
        ItemMeta im = this.is.getItemMeta();
        im.addItemFlags(new ItemFlag[]{flag});
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setLore(String ... lore) {
        ItemMeta im = this.is.getItemMeta();
        im.setLore(Arrays.asList(lore));
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setLore(String string, String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, String string10, List<String> lore2, String string12, String string13, String string14, String string15, String string16, List<String> lore3) {
        ItemMeta im = this.is.getItemMeta();
        ArrayList<String> l = new ArrayList<String>();
        l.add(string);
        l.add(string2);
        l.add(string3);
        l.add(string4);
        l.add(string5);
        l.add(string6);
        l.add(string7);
        l.add(string8);
        l.add(string9);
        l.add(string10);
        l.addAll(lore2);
        l.add(string12);
        l.add(string13);
        l.add(string14);
        l.add(string15);
        l.add(string16);
        l.addAll(lore3);
        im.setLore(l);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setLeatherArmorColor(Color cor) {
        try {
            LeatherArmorMeta im = (LeatherArmorMeta)this.is.getItemMeta();
            im.setColor(cor);
            this.is.setItemMeta((ItemMeta)im);
        }
        catch (ClassCastException classCastException) {
            // empty catch block
        }
        return this;
    }

    public ItemStack toItemStack() {
        return this.is;
    }
}

