package com.teamacronymcoders.advisor.api.speech;

import net.minecraft.entity.player.EntityPlayer;

public class EmptyResponse extends Response {
    @Override
    public boolean canRespond(EntityPlayer entityPlayer) {
        return true;
    }

    @Override
    public void respond(EntityPlayer entityPlayer, boolean client) {

    }
}
