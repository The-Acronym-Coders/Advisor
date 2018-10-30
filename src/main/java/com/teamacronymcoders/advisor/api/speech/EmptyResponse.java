package com.teamacronymcoders.advisor.api.speech;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class EmptyResponse extends IForgeRegistryEntry.Impl<IResponse> implements IResponse {
    @Override
    public boolean canRespond(EntityPlayer entityPlayer) {
        return true;
    }

    @Override
    public void respond(EntityPlayer entityPlayer, boolean client) {

    }
}
