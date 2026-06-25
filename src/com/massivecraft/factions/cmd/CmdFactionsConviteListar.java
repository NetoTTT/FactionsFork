/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.store.EntityInternalMap
 *  com.massivecraft.massivecore.util.TimeDiffUtil
 *  com.massivecraft.massivecore.util.TimeUnit
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.Invitation;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.util.ItemBuilder;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.store.EntityInternalMap;
import com.massivecraft.massivecore.util.TimeDiffUtil;
import com.massivecraft.massivecore.util.TimeUnit;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CmdFactionsConviteListar
extends FactionsCommand {
    public CmdFactionsConviteListar() {
        this.addAliases(new String[]{"ver", "list"});
        this.setDesc("\u00a76 convite listar \u00a78-\u00a77 Mostra a lista de convites pendentes.");
    }

    public void perform() throws MassiveException {
        Faction faction = this.msenderFaction;
        Player p = this.msender.getPlayer();
        long now = System.currentTimeMillis();
        int tamanho = 54;
        int flecha = 49;
        EntityInternalMap<Invitation> invitations = faction.getInvitations();
        if (invitations.size() <= 5) {
            tamanho = 36;
            flecha = 31;
        } else if (invitations.size() > 5 && invitations.size() <= 10) {
            tamanho = 45;
            flecha = 40;
        } else {
            tamanho = 54;
            flecha = 49;
        }
        Inventory inv = Bukkit.createInventory(null, (int)tamanho, (String)("\u00a71\u00a72\u00a73\u00a78Convites pendentes (" + invitations.size() + "\u00a78)"));
        inv.setItem(flecha, new ItemBuilder(Material.ARROW).setName("\u00a71\u00a72\u00a7cVoltar").toItemStack());
        int n = 1;
        int slot = 11;
        for (Map.Entry invitation : invitations.entrySet()) {
            MPlayer invited = MPlayer.get(invitation.getKey());
            MPlayer inviter = MPlayer.get(((Invitation)((Object)invitation.getValue())).getInviterId());
            String invitedName = invited.getName();
            String inviterName = inviter != null ? inviter.getName() : "\u00a77\u00a70Desconhecido";
            String inviteTime = "\u00a77\u00a7o0 minutos";
            if (((Invitation)((Object)invitation.getValue())).getCreationMillis() != null) {
                long millis = now - ((Invitation)((Object)invitation.getValue())).getCreationMillis();
                LinkedHashMap ageUnitcounts = TimeDiffUtil.limit((LinkedHashMap)TimeDiffUtil.unitcounts((long)millis, (TreeSet)TimeUnit.getAllButMillis()), (int)2);
                inviteTime = TimeDiffUtil.formatedMinimal((Map)ageUnitcounts);
            }
            inv.setItem(slot, new ItemBuilder(Material.PAPER).setName("\u00a7eConvite #" + n).setLore("\u00a7fPlayer que foi convidado: \u00a77" + invitedName, "\u00a7fPlayer que convidou: \u00a77" + inviter.getRole().getPrefix() + inviterName, "\u00a7fConvite enviado h\u00e1 " + inviteTime + "\u00a7f atr\u00e1s.", "", "\u00a7fBot\u00e3o direito: \u00a77Remover convite", "\u00a7fBot\u00e3o Esquerdo: \u00a77Informa\u00e7\u00f5es do player").toItemStack());
            slot += slot == 15 || slot == 24 ? 5 : 1;
            ++n;
        }
        p.openInventory(inv);
    }
}

