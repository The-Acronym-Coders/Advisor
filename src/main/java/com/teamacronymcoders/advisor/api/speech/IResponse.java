package com.teamacronymcoders.advisor.api.speech;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.registries.IForgeRegistryEntry;

public interface IResponse extends IForgeRegistryEntry<IResponse> {
    boolean canRespond(EntityPlayer entityPlayer);

    void respond(EntityPlayer entityPlayer, boolean client);
}
