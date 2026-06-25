/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.Identified
 *  com.massivecraft.massivecore.util.PermissionUtil
 *  org.bukkit.permissions.Permissible
 *  org.bukkit.plugin.Plugin
 */
package com.massivecraft.factions;

import com.massivecraft.factions.Factions;
import com.massivecraft.massivecore.Identified;
import com.massivecraft.massivecore.util.PermissionUtil;
import org.bukkit.permissions.Permissible;
import org.bukkit.plugin.Plugin;

public enum Perm implements Identified
{
    ADMIN,
    BASECOMMAND,
    BAU,
    CHATALIADOS,
    CHATFACCAO,
    CLAIM,
    CLAIM_ALL,
    CLAIM_AUTO,
    CLAIM_ONE,
    CONVITE,
    CONVITE_ADD,
    CONVITE_DEL,
    CONVITE_LISTAR,
    CRIAR,
    DELHOME,
    DESC,
    DESFAZER,
    ENTRAR,
    ESCAPAR,
    GERADORES,
    HOME,
    INFO,
    KICK,
    LISTAR,
    MAPA,
    MEMBROS,
    MOTD,
    NOME,
    OPEN,
    SIGLA,
    TAG,
    PERFIL,
    PERM,
    PERMISSOES,
    PODER,
    PODER_FACTION,
    PODER_PLAYER,
    PODER_SET,
    PROMOVER,
    REBAIXAR,
    RELACAO,
    RELACAO_DEFINIR,
    RELACAO_LISTAR,
    SAIR,
    SETHOME,
    SOBATAQUE,
    TITULOS,
    TOP,
    TRANSFERIR,
    UNCLAIM,
    UNCLAIM_ALL,
    UNCLAIM_ONE,
    VERSION,
    VERTERRAS,
    VOAR;

    private final String id = PermissionUtil.createPermissionId((Plugin)Factions.get(), (Enum)this);

    public String getId() {
        return this.id;
    }

    public boolean has(Permissible permissible, boolean verboose) {
        return PermissionUtil.hasPermission((Permissible)permissible, (Object)((Object)this), (boolean)verboose);
    }

    public boolean has(Permissible permissible) {
        return PermissionUtil.hasPermission((Permissible)permissible, (Object)((Object)this));
    }
}

