/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.collections.BackstringSet
 *  com.massivecraft.massivecore.collections.WorldExceptionSet
 *  com.massivecraft.massivecore.command.editor.annotation.EditorName
 *  com.massivecraft.massivecore.command.editor.annotation.EditorType
 *  com.massivecraft.massivecore.command.editor.annotation.EditorTypeInner
 *  com.massivecraft.massivecore.command.type.TypeMillisDiff
 *  com.massivecraft.massivecore.store.Entity
 *  com.massivecraft.massivecore.store.EntityInternal
 *  com.massivecraft.massivecore.util.MUtil
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.entity.EntityType
 *  org.bukkit.event.EventPriority
 */
package com.massivecraft.factions.entity;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.engine.EngineChat;
import com.massivecraft.massivecore.collections.BackstringSet;
import com.massivecraft.massivecore.collections.WorldExceptionSet;
import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.command.editor.annotation.EditorType;
import com.massivecraft.massivecore.command.editor.annotation.EditorTypeInner;
import com.massivecraft.massivecore.command.type.TypeMillisDiff;
import com.massivecraft.massivecore.store.Entity;
import com.massivecraft.massivecore.store.EntityInternal;
import com.massivecraft.massivecore.util.MUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventPriority;

@EditorName(value="config")
public class MConf
extends Entity<MConf> {
    protected static transient MConf i;
    public int version = 3;
    public boolean bloquearSpawnersForaDoClaim = false;
    public boolean anunciarMorteAoMorrer = true;
    public int distanciaDoAnuncioEmBlocos = 16;
    public boolean colocarIconeDoFBauNoMenuGUI = false;
    public boolean colocarIconeDoFGeradoresNoMenuGUI = false;
    public List<String> aliasesF = MUtil.list("f");
    public List<String> aliasesChatFaccao = MUtil.list(".", "c");
    public List<String> aliasesChatAliados = MUtil.list("a");
    public int bordaXnegativo = -20001;
    public int bordaZnegativo = -20001;
    public int bordaXpositivo = 20000;
    public int bordaZpositivo = 20000;
    public WorldExceptionSet worldsClaimingEnabled = new WorldExceptionSet();
    public double taskPlayerPowerUpdateMinutes = 1.0;
    public double taskPlayerDataRemoveMinutes = 5.0;
    @EditorType(value=TypeMillisDiff.class)
    public long cleanInactivityToleranceMillis = 864000000L;
    @EditorTypeInner(value={TypeMillisDiff.class, TypeMillisDiff.class})
    public Map<Long, Long> cleanInactivityToleranceMillisPlayerAgeToBonus = MUtil.map(1209600000L, 864000000L);
    @EditorTypeInner(value={TypeMillisDiff.class, TypeMillisDiff.class})
    public Map<Long, Long> cleanInactivityToleranceMillisFactionAgeToBonus = MUtil.map(2419200000L, 864000000L, 1209600000L, 432000000L);
    public String defaultPlayerFactionId = "none";
    public Rel defaultPlayerRole = Rel.RECRUIT;
    public double defaultPlayerPower = 10.0;
    public double powerMax = 10.0;
    public double powerMin = 0.0;
    public double powerPerHour = 2.5;
    public double powerPerDeath = -2.0;
    public int factionMemberLimit = 10;
    public double factionPowerMax = 0.0;
    public int factionNameLengthMin = 5;
    public int factionNameLengthMax = 16;
    public boolean factionNameForceUpperCase = false;
    public int factionAllyLimit = 2;
    public boolean claimsMustBeConnected = true;
    public boolean claimsCanBeUnconnectedIfOwnedByOtherFaction = false;
    public int claimMinimumChunksDistanceToOthers = 3;
    public int claimsRequireMinFactionMembers = 1;
    public int claimedLandsMax = 0;
    public int claimedWorldsMax = -1;
    public boolean homesMustBeInClaimedTerritory = true;
    public boolean homesTeleportAllowedFromEnemyTerritory = true;
    public int homeSeconds = 15;
    public boolean territoryInfoTitlesDefault = true;
    public String territoryInfoTitlesMain = "{relcolor}{name}";
    public String territoryInfoTitlesSub = "<i>{desc}";
    public int territoryInfoTitlesTicksIn = 5;
    public int territoryInfoTitlesTicksStay = 60;
    public int territoryInfoTitleTicksOut = 5;
    public String territoryInfoChat = "<i> ~ {relcolor}{name} <i>{desc}";
    public boolean permanentFactionsDisableLeaderPromotion = false;
    public boolean handlePistonProtectionThroughDenyBuild = true;
    public String colorTagTop1 = "§6";
    public String colorTagTop2 = "§7";
    public String colorTagTop3 = "§c";
    public Map<Rel, List<String>> denyCommandsTerritoryRelation = MUtil.map(Rel.ENEMY, MUtil.list("home", "homes", "sethome", "createhome", "tpahere", "tpaccept", "tpyes", "tpa", "call", "tpask", "warp", "warps", "spawn", "ehome", "ehomes", "esethome", "ecreatehome", "etpahere", "etpaccept", "etpyes", "etpa", "ecall", "etpask", "ewarp", "ewarps", "espawn", "essentials:home", "essentials:homes", "essentials:sethome", "essentials:createhome", "essentials:tpahere", "essentials:tpaccept", "essentials:tpyes", "essentials:tpa", "essentials:call", "essentials:tpask", "essentials:warp", "essentials:warps", "essentials:spawn", "jtp"), Rel.NEUTRAL, new ArrayList<String>(), Rel.TRUCE, new ArrayList<String>(), Rel.ALLY, new ArrayList<String>(), Rel.MEMBER, new ArrayList<String>());
    public EventPriority chatSetFormatAt = EventPriority.LOWEST;
    public String chatSetFormatTo = "<{faction}\u00a7f%1$s> %2$s";
    public EventPriority chatParseTagsAt = EventPriority.LOW;
    public ChatColor colorMember = ChatColor.GREEN;
    public ChatColor colorAlly = ChatColor.AQUA;
    public ChatColor colorTruce = ChatColor.GRAY;
    public ChatColor colorNeutral = ChatColor.WHITE;
    public ChatColor colorEnemy = ChatColor.RED;
    public ChatColor colorNoPVP = ChatColor.GOLD;
    public ChatColor colorFriendlyFire = ChatColor.DARK_RED;
    public String prefixLeader = "**";
    public String prefixOfficer = "*";
    public String prefixMember = "+";
    public String prefixRecruit = "-";
    public int seeChunkSteps = 3;
    public int seeChunkKeepEvery = 5;
    public int seeChunkSkipEvery = 0;
    @EditorType(value=TypeMillisDiff.class)
    public long seeChunkPeriodMillis = 400L;
    public int seeChunkParticleAmount = 10;
    public float seeChunkParticleOffsetY = 2.0f;
    public float seeChunkParticleDeltaY = 2.0f;
    public int unstuckSeconds = 15;
    public int unstuckChunkRadius = 10;
    public BackstringSet<Material> materialsEditOnInteract = new BackstringSet(Material.class);
    public BackstringSet<Material> materialsEditTools = new BackstringSet(Material.class);
    public BackstringSet<Material> materialsDoor = new BackstringSet(Material.class);
    public BackstringSet<Material> materialsContainer = new BackstringSet(Material.class);
    public BackstringSet<EntityType> entityTypesEditOnInteract = new BackstringSet(EntityType.class);
    public BackstringSet<EntityType> entityTypesEditOnDamage = new BackstringSet(EntityType.class);
    public BackstringSet<EntityType> entityTypesContainer = new BackstringSet(EntityType.class);
    public BackstringSet<EntityType> entityTypesMonsters = new BackstringSet(EntityType.class);
    public BackstringSet<EntityType> entityTypesAnimals = new BackstringSet(EntityType.class);

    public static MConf get() {
        return i;
    }

    public MConf load(MConf that) {
        super.load(that);
        EngineChat engine = EngineChat.get();
        if (engine.isActive()) {
            engine.setActive(false);
            engine.setActive(true);
        }
        return this;
    }
}

