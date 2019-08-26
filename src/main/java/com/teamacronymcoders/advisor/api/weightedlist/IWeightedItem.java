package com.teamacronymcoders.advisor.api.weightedlist;

import com.teamacronymcoders.advisor.api.runcondition.IRunCondition;
import net.minecraft.entity.player.PlayerEntity;

public interface IWeightedItem<T> extends IRunCondition {
    double getWeight();

    T getItem();

    default boolean isConditional() {
        return false;
    }

    @Override
    default boolean isMet(PlayerEntity playerEntity) {
        return true;
    }
}
