package com.teamacronymcoders.advisor.api.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AdvisorDataProvider implements ICapabilityProvider, INBTSerializable<NBTTagCompound> {
    private final IAdvisorData data = new AdvisorData();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapAdvisorData.ADVISOR_DATA;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return hasCapability(capability, facing) ? CapAdvisorData.ADVISOR_DATA.cast(data) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return data.saveToNBT();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        data.loadFromNBT(nbt);
    }
}
