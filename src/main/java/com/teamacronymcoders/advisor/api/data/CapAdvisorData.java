package com.teamacronymcoders.advisor.api.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapAdvisorData {
    @CapabilityInject(IAdvisorData.class)
    public static Capability<IAdvisorData> ADVISOR_DATA;

    public static void register() {
        CapabilityManager.INSTANCE.register(IAdvisorData.class, new Capability.IStorage<IAdvisorData>() {
            @Override
            public CompoundNBT writeNBT(Capability<IAdvisorData> capability, IAdvisorData instance, Direction direction) {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<IAdvisorData> capability, IAdvisorData instance, Direction direction, INBT nbt) {
                instance.deserializeNBT((CompoundNBT) nbt);
            }
        }, AdvisorData::new);
    }
}
