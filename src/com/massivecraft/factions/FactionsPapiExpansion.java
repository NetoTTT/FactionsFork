package com.massivecraft.factions;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class FactionsPapiExpansion extends PlaceholderExpansion {

    @Override public String getIdentifier() { return "factionsfork"; }
    @Override public String getAuthor()     { return "HaskDev"; }
    @Override public String getVersion()    { return "1.0"; }
    @Override public boolean persist()      { return true; }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) return "";
        MPlayer mp = MPlayer.get(player);
        if (mp == null) return "";

        switch (identifier) {
            case "tag": {
                if (!mp.hasFaction()) return "";
                Faction fac = mp.getFaction();
                String tag = fac.getTag();
                if (tag != null && !tag.isEmpty()) {
                    return FactionColl.get().getRankColor(fac) + "[" + tag + "]";
                }
                return "§7[" + mp.getFactionName() + "]";
            }
            case "tag_raw": {
                if (!mp.hasFaction()) return "";
                Faction fac = mp.getFaction();
                String tag = fac.getTag();
                return (tag != null && !tag.isEmpty()) ? tag : mp.getFactionName();
            }
            case "name": {
                return mp.hasFaction() ? mp.getFactionName() : "";
            }
            case "rank_color": {
                if (!mp.hasFaction()) return "§7";
                return FactionColl.get().getRankColor(mp.getFaction());
            }
            case "power": {
                return String.valueOf((int) mp.getPower());
            }
            case "power_max": {
                return String.valueOf((int) mp.getPowerMax());
            }
            default:
                return null;
        }
    }
}
