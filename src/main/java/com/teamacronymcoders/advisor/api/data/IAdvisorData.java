package com.teamacronymcoders.advisor.api.data;

import net.minecraft.nbt.NBTTagCompound;

public interface IAdvisorData {
    boolean hasReceivedIntro();

    void setHasReceivedIntro(boolean receivedIntro);

    void loadFromNBT(NBTTagCompound nbtTagCompound);

    NBTTagCompound saveToNBT();
}
