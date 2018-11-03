package com.teamacronymcoders.advisor.api.data;

import net.minecraft.nbt.NBTTagCompound;

public class AdvisorData implements IAdvisorData {
    private boolean receivedIntro;

    @Override
    public boolean hasReceivedIntro() {
        return receivedIntro;
    }

    @Override
    public void setHasReceivedIntro(boolean receivedIntro) {
        this.receivedIntro = receivedIntro;
    }

    @Override
    public void loadFromNBT(NBTTagCompound nbtTagCompound) {
        this.setHasReceivedIntro(nbtTagCompound.getBoolean("receivedIntro"));
    }

    @Override
    public NBTTagCompound saveToNBT() {
        return new NBTTagCompoundBuilder()
                .withBoolean("receivedIntro", this.hasReceivedIntro())
                .create();
    }
}
