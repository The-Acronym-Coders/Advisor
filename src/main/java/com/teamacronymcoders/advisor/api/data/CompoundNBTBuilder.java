package com.teamacronymcoders.advisor.api.data;

import net.minecraft.nbt.CompoundNBT;

public class CompoundNBTBuilder {
    private CompoundNBT compoundNBT;

    public CompoundNBTBuilder() {
        compoundNBT = new CompoundNBT();
    }

    public CompoundNBTBuilder withBoolean(String name, boolean value) {
        compoundNBT.putBoolean(name, value);
        return this;
    }

    public CompoundNBT create() {
        return compoundNBT;
    }
}
