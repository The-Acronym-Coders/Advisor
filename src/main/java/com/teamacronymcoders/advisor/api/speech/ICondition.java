package com.teamacronymcoders.advisor.api.speech;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.registries.IForgeRegistryEntry;

public interface ICondition extends IForgeRegistryEntry<ICondition> {
    boolean canRespond(EntityPlayer entityPlayer);
}
