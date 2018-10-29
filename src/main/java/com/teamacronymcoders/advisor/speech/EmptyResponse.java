package com.teamacronymcoders.advisor.speech;

import com.teamacronymcoders.advisor.api.speech.Response;
import net.minecraft.entity.player.EntityPlayer;

public class EmptyResponse implements Response {
    @Override
    public String getName() {
        return "advisor:empty";
    }

    @Override
    public boolean canRespond(EntityPlayer entityPlayer) {
        return true;
    }

    @Override
    public void respond(EntityPlayer entityPlayer, boolean client) {

    }
}
