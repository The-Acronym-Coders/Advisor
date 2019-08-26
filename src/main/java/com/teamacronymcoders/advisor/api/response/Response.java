package com.teamacronymcoders.advisor.api.response;

import net.minecraft.entity.player.PlayerEntity;

public abstract class Response {
    public abstract void respond(PlayerEntity PlayerEntity, boolean client);
}
