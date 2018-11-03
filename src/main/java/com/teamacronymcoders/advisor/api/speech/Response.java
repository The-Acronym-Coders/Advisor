package com.teamacronymcoders.advisor.api.speech;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Response extends IForgeRegistryEntry.Impl<Response> {
    public boolean canRespond(EntityPlayer entityPlayer) {
        return true;
    }

    public void respond(EntityPlayer entityPlayer, boolean client) {

    }
}
