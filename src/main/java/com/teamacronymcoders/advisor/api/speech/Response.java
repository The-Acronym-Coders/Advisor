package com.teamacronymcoders.advisor.api.speech;

import net.minecraft.entity.player.EntityPlayer;

public interface Response {
    String getName();

    boolean canRespond(EntityPlayer entityPlayer);

    void respond(EntityPlayer entityPlayer, boolean client);
}
