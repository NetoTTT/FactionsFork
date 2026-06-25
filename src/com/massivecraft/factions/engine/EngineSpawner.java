package com.massivecraft.factions.engine;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.ps.PS;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EngineSpawner extends Engine {

    private static final EngineSpawner i = new EngineSpawner();
    public static EngineSpawner get() { return i; }

    private static final Map<String, String> PT_NAMES = new HashMap<String, String>();
    static {
        PT_NAMES.put("COW",          "§aSpawner §fde Vaca");
        PT_NAMES.put("PIG",          "§aSpawner §fde Porco");
        PT_NAMES.put("SHEEP",        "§aSpawner §fde Ovelha");
        PT_NAMES.put("CHICKEN",      "§aSpawner §fde Galinha");
        PT_NAMES.put("SQUID",        "§aSpawner §fde Lula");
        PT_NAMES.put("MUSHROOM_COW", "§aSpawner §fde Vaca Cogumelo");
        PT_NAMES.put("WOLF",         "§aSpawner §fde Lobo");
        PT_NAMES.put("OCELOT",       "§aSpawner §fde Ocelote");
        PT_NAMES.put("IRON_GOLEM",   "§aSpawner §fde Golem de Ferro");
        PT_NAMES.put("ZOMBIE",       "§aSpawner §fde Zumbi");
        PT_NAMES.put("SKELETON",     "§aSpawner §fde Esqueleto");
        PT_NAMES.put("SPIDER",       "§aSpawner §fde Aranha");
        PT_NAMES.put("CAVE_SPIDER",  "§aSpawner §fde Aranha de Caverna");
        PT_NAMES.put("CREEPER",      "§aSpawner §fde Creeper");
        PT_NAMES.put("SILVERFISH",   "§aSpawner §fde Traça");
        PT_NAMES.put("SLIME",        "§aSpawner §fde Slime");
        PT_NAMES.put("ENDERMAN",     "§aSpawner §fde Enderman");
        PT_NAMES.put("WITCH",        "§aSpawner §fde Bruxa");
        PT_NAMES.put("PIG_ZOMBIE",   "§aSpawner §fde Zumbi Porco");
        PT_NAMES.put("MAGMA_CUBE",   "§aSpawner §fde Cubo de Magma");
        PT_NAMES.put("BLAZE",        "§aSpawner §fde Blaze");
        PT_NAMES.put("GHAST",        "§aSpawner §fde Ghast");
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent e) {
        Block block = e.getBlockPlaced();
        if (block.getType() != Material.MOB_SPAWNER) return;

        String spawnerType = getSpawnerType(e.getItemInHand());
        if (spawnerType == null) return;

        // Set entity type on the placed spawner block
        try {
            CreatureSpawner cs = (CreatureSpawner) block.getState();
            cs.setSpawnedType(EntityType.valueOf(spawnerType));
            cs.update(true);
        } catch (Exception ignored) {}

        Faction faction = BoardColl.get().getFactionAt(PS.valueOf(block));
        if (!faction.isNormal()) return;
        faction.addShopSpawner(locKey(block), spawnerType);
        FactionColl.get().invalidateRankCache();
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        if (block.getType() != Material.MOB_SPAWNER) return;

        String loc = locKey(block);
        String spawnerType = null;

        // Check current faction territory first
        Faction faction = BoardColl.get().getFactionAt(PS.valueOf(block));
        if (faction.isNormal()) {
            spawnerType = faction.removeShopSpawner(loc);
        }

        // Fallback: territory may have changed since placement, search all factions
        if (spawnerType == null) {
            for (Faction f : FactionColl.get().getAll()) {
                if (f.isNormal()) {
                    spawnerType = f.removeShopSpawner(loc);
                    if (spawnerType != null) break;
                }
            }
        }

        if (spawnerType == null) return; // natural spawner — let vanilla handle drops

        e.setCancelled(true);
        block.setType(Material.AIR);
        block.getWorld().dropItemNaturally(block.getLocation(), buildSpawnerItem(spawnerType));
        FactionColl.get().invalidateRankCache();
    }

    private String getSpawnerType(ItemStack item) {
        if (item == null || item.getType() != Material.MOB_SPAWNER) return null;
        try {
            net.minecraft.server.v1_8_R3.ItemStack nms = CraftItemStack.asNMSCopy(item);
            if (nms == null || !nms.hasTag()) return null;
            NBTTagCompound tag = nms.getTag();
            if (!tag.hasKey("hask_spawner_type")) return null;
            return tag.getString("hask_spawner_type").toUpperCase();
        } catch (Exception ex) { return null; }
    }

    public ItemStack buildSpawnerItem(String spawnerType) {
        ItemStack item = new ItemStack(Material.MOB_SPAWNER);
        net.minecraft.server.v1_8_R3.ItemStack nms = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = (nms.hasTag()) ? nms.getTag() : new NBTTagCompound();
        tag.setString("hask_id", "spawner_" + spawnerType.toLowerCase());
        tag.setString("hask_spawner_type", spawnerType.toUpperCase());
        nms.setTag(tag);
        ItemStack result = CraftItemStack.asBukkitCopy(nms);

        ItemMeta meta = result.getItemMeta();
        String displayName = PT_NAMES.containsKey(spawnerType) ? PT_NAMES.get(spawnerType) : "§aSpawner §f" + spawnerType;
        meta.setDisplayName(displayName);
        List<String> lore = new ArrayList<String>();
        Double val = MConf.get().spawnerValues.get(spawnerType.toUpperCase());
        if (val != null) lore.add("§7Valor no ranking: §f$" + String.format("%,.0f", val));
        lore.add("§7Coloque no territorio da sua faccao.");
        meta.setLore(lore);
        result.setItemMeta(meta);
        return result;
    }

    private String locKey(Block block) {
        return block.getWorld().getName() + "," + block.getX() + "," + block.getY() + "," + block.getZ();
    }
}
