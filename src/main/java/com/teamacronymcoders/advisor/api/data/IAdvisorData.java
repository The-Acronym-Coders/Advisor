package com.teamacronymcoders.advisor.api.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface IAdvisorData extends INBTSerializable<CompoundNBT> {
    boolean hasReceivedIntro();

    void setHasReceivedIntro(boolean receivedIntro);
}
