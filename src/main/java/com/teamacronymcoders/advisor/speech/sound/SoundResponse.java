package com.teamacronymcoders.advisor.speech.sound;

import com.teamacronymcoders.advisor.api.speech.Response;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class SoundResponse implements Response {
    private final SoundEvent soundEvent;
    private final String name;
    private final boolean playerOnly;

    public SoundResponse(String name, SoundEvent soundEvent, boolean playerOnly) {
        this.soundEvent = soundEvent;
        this.name = name;
        this.playerOnly = playerOnly;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean canRespond(EntityPlayer entityPlayer) {
        return true;
    }

    @Override
    public void respond(EntityPlayer entityPlayer, boolean client) {
        if (client) {
            entityPlayer.getEntityWorld().playSound(entityPlayer, entityPlayer.getPosition(), soundEvent,
                    SoundCategory.VOICE, 1.0F, 1.0F);
        } else {
            if (!playerOnly) {
                entityPlayer.getEntityWorld().playSound(entityPlayer, entityPlayer.getPosition(), soundEvent,
                        SoundCategory.VOICE, 1.0F, 1.0F);
            }
        }

    }
}
