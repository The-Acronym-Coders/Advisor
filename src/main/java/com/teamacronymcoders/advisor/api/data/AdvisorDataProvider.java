package com.teamacronymcoders.advisor.api.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AdvisorDataProvider implements ICapabilityProvider, INBTSerializable<CompoundNBT> {
    private final IAdvisorData data;
    private final LazyOptional<IAdvisorData> optional;

    public AdvisorDataProvider() {
        this.data = new AdvisorData();
        this.optional = LazyOptional.of(() -> data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        return capability == CapAdvisorData.ADVISOR_DATA ? optional.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        return data.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        data.deserializeNBT(nbt);
    }
}
