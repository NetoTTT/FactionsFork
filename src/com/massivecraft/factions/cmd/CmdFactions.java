/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.command.MassiveCommand
 *  com.massivecraft.massivecore.command.MassiveCommandDeprecated
 *  com.massivecraft.massivecore.command.MassiveCommandVersion
 *  com.massivecraft.massivecore.command.requirement.Requirement
 *  com.massivecraft.massivecore.command.requirement.RequirementHasPerm
 *  org.bukkit.plugin.Plugin
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.Perm;
import com.massivecraft.factions.cmd.CmdFactionsAdmin;
import com.massivecraft.factions.cmd.CmdFactionsBau;
import com.massivecraft.factions.cmd.CmdFactionsChatAliados;
import com.massivecraft.factions.cmd.CmdFactionsChatFaccao;
import com.massivecraft.factions.cmd.CmdFactionsClaim;
import com.massivecraft.factions.cmd.CmdFactionsConvite;
import com.massivecraft.factions.cmd.CmdFactionsCriar;
import com.massivecraft.factions.cmd.CmdFactionsDelhome;
import com.massivecraft.factions.cmd.CmdFactionsDesc;
import com.massivecraft.factions.cmd.CmdFactionsDesfazer;
import com.massivecraft.factions.cmd.CmdFactionsEntrar;
import com.massivecraft.factions.cmd.CmdFactionsEscapar;
import com.massivecraft.factions.cmd.CmdFactionsGeradores;
import com.massivecraft.factions.cmd.CmdFactionsHome;
import com.massivecraft.factions.cmd.CmdFactionsInfo;
import com.massivecraft.factions.cmd.CmdFactionsKick;
import com.massivecraft.factions.cmd.CmdFactionsListar;
import com.massivecraft.factions.cmd.CmdFactionsMapa;
import com.massivecraft.factions.cmd.CmdFactionsMembros;
import com.massivecraft.factions.cmd.CmdFactionsMotd;
import com.massivecraft.factions.cmd.CmdFactionsNome;
import com.massivecraft.factions.cmd.CmdFactionsTag;
import com.massivecraft.factions.cmd.CmdFactionsPerfil;
import com.massivecraft.factions.cmd.CmdFactionsPermissoes;
import com.massivecraft.factions.cmd.CmdFactionsPoder;
import com.massivecraft.factions.cmd.CmdFactionsPromover;
import com.massivecraft.factions.cmd.CmdFactionsRebaixar;
import com.massivecraft.factions.cmd.CmdFactionsRelacao;
import com.massivecraft.factions.cmd.CmdFactionsRelacaoOld;
import com.massivecraft.factions.cmd.CmdFactionsSair;
import com.massivecraft.factions.cmd.CmdFactionsSethome;
import com.massivecraft.factions.cmd.CmdFactionsSobAtaque;
import com.massivecraft.factions.cmd.CmdFactionsTitulos;
import com.massivecraft.factions.cmd.CmdFactionsTop;
import com.massivecraft.factions.cmd.CmdFactionsTransferir;
import com.massivecraft.factions.cmd.CmdFactionsUnclaim;
import com.massivecraft.factions.cmd.CmdFactionsVerTerras;
import com.massivecraft.factions.cmd.CmdFactionsVoar;
import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.massivecore.command.MassiveCommand;
import com.massivecraft.massivecore.command.MassiveCommandDeprecated;
import com.massivecraft.massivecore.command.MassiveCommandVersion;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import java.util.List;
import org.bukkit.plugin.Plugin;

public class CmdFactions
extends FactionsCommand {
    private static CmdFactions i = new CmdFactions();
    public CmdFactionsChatFaccao cmdFactionsChatFaccao = new CmdFactionsChatFaccao();
    public CmdFactionsChatAliados cmdFactionsChatAliados = new CmdFactionsChatAliados();
    public CmdFactionsCriar cmdFactionsCriar = new CmdFactionsCriar();
    public CmdFactionsEntrar cmdFactionsEntrar = new CmdFactionsEntrar();
    public CmdFactionsInfo cmdFactionsInfo = new CmdFactionsInfo();
    public CmdFactionsMembros cmdFactionsMembros = new CmdFactionsMembros();
    public CmdFactionsPerfil cmdFactionsPerfil = new CmdFactionsPerfil();
    public CmdFactionsListar cmdFactionsListar = new CmdFactionsListar();
    public CmdFactionsHome cmdFactionsHome = new CmdFactionsHome();
    public CmdFactionsMapa cmdFactionsMapa = new CmdFactionsMapa();
    public CmdFactionsPromover cmdFactionsPromover = new CmdFactionsPromover();
    public CmdFactionsTransferir cmdFactionsTransferir = new CmdFactionsTransferir();
    public CmdFactionsRebaixar cmdFactionsRebaixar = new CmdFactionsRebaixar();
    public CmdFactionsKick cmdFactionsKick = new CmdFactionsKick();
    public CmdFactionsNome cmdFactionsNome = new CmdFactionsNome();
    public CmdFactionsTag cmdFactionsTag = new CmdFactionsTag();
    public CmdFactionsDesc cmdFactionsDesc = new CmdFactionsDesc();
    public CmdFactionsMotd cmdFactionsMotd = new CmdFactionsMotd();
    public CmdFactionsSethome cmdFactionsSethome = new CmdFactionsSethome();
    public CmdFactionsDelhome cmdFactionsDelhome = new CmdFactionsDelhome();
    public CmdFactionsBau cmdFactionsBau = new CmdFactionsBau();
    public CmdFactionsGeradores cmdFactionsGeradores = new CmdFactionsGeradores();
    public CmdFactionsPermissoes cmdFactionsPermissoes = new CmdFactionsPermissoes();
    public CmdFactionsRelacao cmdFactionsRelacao = new CmdFactionsRelacao();
    public CmdFactionsRelacaoOld cmdFactionsRelacaoOldAlly = (CmdFactionsRelacaoOld)new CmdFactionsRelacaoOld("aliado").setAliases(new String[]{"ally", "aliado"});
    public CmdFactionsRelacaoOld cmdFactionsRelacaoOldNeutral = (CmdFactionsRelacaoOld)new CmdFactionsRelacaoOld("neutro").setAliases(new String[]{"neutral", "tregua", "neutro"});
    public CmdFactionsRelacaoOld cmdFactionsRelacaoOldEnemy = (CmdFactionsRelacaoOld)new CmdFactionsRelacaoOld("inimigo").setAliases(new String[]{"enemy", "rival", "inimigo"});
    public CmdFactionsConvite cmdFactionsConvite = new CmdFactionsConvite();
    public CmdFactionsClaim cmdFactionsClaim = new CmdFactionsClaim();
    public CmdFactionsUnclaim cmdFactionsUnclaim = new CmdFactionsUnclaim();
    public CmdFactionsDesfazer cmdFactionsDesfazer = new CmdFactionsDesfazer();
    public CmdFactionsSair cmdFactionsSair = new CmdFactionsSair();
    public CmdFactionsEscapar cmdFactionsEscapar = new CmdFactionsEscapar();
    public CmdFactionsVoar cmdFactionsVoar = new CmdFactionsVoar();
    public CmdFactionsSobAtaque cmdFactionsSobAtaque = new CmdFactionsSobAtaque();
    public CmdFactionsTop cmdFactionsTop = new CmdFactionsTop();
    public CmdFactionsVerTerras cmdFactionsVerTerras = new CmdFactionsVerTerras();
    public CmdFactionsTitulos cmdFactionsTitulos = new CmdFactionsTitulos();
    public MassiveCommandVersion cmdFactionsVersion = (MassiveCommandVersion)new MassiveCommandVersion((Plugin)Factions.get()).setAliases(new String[]{"v", "version"}).addRequirements(new Requirement[]{RequirementHasPerm.get((Object)((Object)Perm.VERSION))});
    public CmdFactionsAdmin cmdFactionsAdmin = new CmdFactionsAdmin();
    public CmdFactionsPoder cmdFactionsPoder = new CmdFactionsPoder();

    public static CmdFactions get() {
        return i;
    }

    public CmdFactions() {
        this.addChild((MassiveCommand)new MassiveCommandDeprecated((MassiveCommand)this.cmdFactionsUnclaim.cmdFactionsUnclaimAll, new String[]{"unclaimall"}));
        this.addChild((MassiveCommand)new MassiveCommandDeprecated((MassiveCommand)this.cmdFactionsInfo, new String[]{"ver", "quem"}));
    }

    public List<String> getAliases() {
        return MConf.get().aliasesF;
    }
}

