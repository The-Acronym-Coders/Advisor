package com.teamacronymcoders.advisor.api.data;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapAdvisorData {
    @CapabilityInject(IAdvisorData.class)
    public static Capability<IAdvisorData> ADVISOR_DATA;

    public static void register() {
        CapabilityManager.INSTANCE.register(IAdvisorData.class, new Capability.IStorage<IAdvisorData>() {
            @Override
            public NBTTagCompound writeNBT(Capability<IAdvisorData> capability, IAdvisorData instance, EnumFacing side) {
                return instance.saveToNBT();
            }

            @Override
            public void readNBT(Capability<IAdvisorData> capability, IAdvisorData instance, EnumFacing side, NBTBase nbt) {
                instance.loadFromNBT((NBTTagCompound) nbt);
            }
        }, AdvisorData::new);
    }
}
