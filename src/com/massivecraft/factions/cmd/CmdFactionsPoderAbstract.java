/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.MassiveException
 *  com.massivecraft.massivecore.command.Visibility
 *  com.massivecraft.massivecore.command.type.Type
 *  com.massivecraft.massivecore.command.type.TypeNullable
 *  com.massivecraft.massivecore.command.type.primitive.TypeDouble
 *  com.massivecraft.massivecore.util.Txt
 *  org.bukkit.permissions.Permissible
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.FactionsParticipator;
import com.massivecraft.factions.Perm;
import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.Visibility;
import com.massivecraft.massivecore.command.type.Type;
import com.massivecraft.massivecore.command.type.TypeNullable;
import com.massivecraft.massivecore.command.type.primitive.TypeDouble;
import com.massivecraft.massivecore.util.Txt;
import org.bukkit.permissions.Permissible;

public abstract class CmdFactionsPoderAbstract
extends FactionsCommand {
    protected CmdFactionsPoderAbstract(Type<? extends FactionsParticipator> parameterType, String parameterName) {
        this.addParameter(parameterType, parameterName);
        this.addParameter((Type)TypeNullable.get((Type)TypeDouble.get()), "quantidade", "ver");
        this.setVisibility(Visibility.SECRET);
    }

    public void perform() throws MassiveException {
        FactionsParticipator factionsParticipator = (FactionsParticipator)this.readArg();
        Double powerBoost = (Double)this.readArg(factionsParticipator.getPowerBoost());
        boolean updated = this.trySet(factionsParticipator, powerBoost);
        this.informPowerBoost(factionsParticipator, powerBoost, updated);
    }

    private boolean trySet(FactionsParticipator factionsParticipator, Double powerBoost) throws MassiveException {
        if (!this.argIsSet(1)) {
            return false;
        }
        if (!Perm.PODER_SET.has((Permissible)this.sender, true)) {
            throw new MassiveException();
        }
        factionsParticipator.setPowerBoost(powerBoost);
        return true;
    }

    private void informPowerBoost(FactionsParticipator factionsParticipator, Double powerBoost, boolean updated) {
        String participatorDescribe = factionsParticipator.describeTo(this.msender, true);
        powerBoost = powerBoost == null ? factionsParticipator.getPowerBoost() : powerBoost.doubleValue();
        String powerDescription = Txt.parse((String)(Double.compare(powerBoost, 0.0) >= 0 ? "\u00a7aum \u00a72b\u00f4nus" : "\u00a7auma \u00a74penalidade"));
        String when = updated ? "agora " : "";
        String verb = factionsParticipator.equals(this.msender) ? "possui" : "possui";
        String messagePlayer = Txt.parse((String)"\u00a7a%s\u00a7a %s%s\u00a7a %s\u00a7a de poder de \u00a7d%.2f\u00a7a.", (Object[])new Object[]{participatorDescribe, when, verb, powerDescription, powerBoost});
        this.msender.message(messagePlayer);
    }
}

