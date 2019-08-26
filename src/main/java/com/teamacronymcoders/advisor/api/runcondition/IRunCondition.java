package com.teamacronymcoders.advisor.api.runcondition;

import net.minecraft.entity.player.PlayerEntity;

public interface IRunCondition {
    boolean isMet(PlayerEntity playerEntity);
}
