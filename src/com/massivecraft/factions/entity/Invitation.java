/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.store.EntityInternal
 */
package com.massivecraft.factions.entity;

import com.massivecraft.massivecore.store.EntityInternal;

public class Invitation
extends EntityInternal<Invitation> {
    private String inviterId;
    private Long creationMillis;

    public Invitation load(Invitation that) {
        this.inviterId = that.inviterId;
        this.creationMillis = that.creationMillis;
        return this;
    }

    public String getInviterId() {
        return this.inviterId;
    }

    public void setInviterId(String inviterId) {
        this.inviterId = inviterId;
    }

    public Long getCreationMillis() {
        return this.creationMillis;
    }

    public void setCreationMillis(Long creationMillis) {
        this.creationMillis = creationMillis;
    }

    public Invitation() {
        this(null, null);
    }

    public Invitation(String inviterId, Long creationMillis) {
        this.inviterId = inviterId;
        this.creationMillis = creationMillis;
    }
}

