package com.teamacronymcoders.advisor.api.data;

import net.minecraft.nbt.CompoundNBT;

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
    public void deserializeNBT(CompoundNBT CompoundNBT) {
        this.setHasReceivedIntro(CompoundNBT.getBoolean("receivedIntro"));
    }

    @Override
    public CompoundNBT serializeNBT() {
        return new CompoundNBTBuilder()
                .withBoolean("receivedIntro", this.hasReceivedIntro())
                .create();
    }
}
