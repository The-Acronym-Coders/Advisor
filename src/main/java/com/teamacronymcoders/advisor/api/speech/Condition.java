package com.teamacronymcoders.advisor.api.speech;

import net.minecraft.entity.player.EntityPlayer;

@FunctionalInterface
public interface Condition {
    boolean canRespond(EntityPlayer entityPlayer);
}
