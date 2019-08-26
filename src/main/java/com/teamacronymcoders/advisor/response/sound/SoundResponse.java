package com.teamacronymcoders.advisor.response.sound;

import com.teamacronymcoders.advisor.api.response.Response;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class SoundResponse extends Response {
    private final SoundEvent sound;
    private final boolean playerOnly;

    public SoundResponse(SoundEvent sound, boolean playerOnly) {
        this.sound = sound;
        this.playerOnly = playerOnly;
    }

    @Override
    public void respond(PlayerEntity PlayerEntity, boolean client) {
        if (client) {
            PlayerEntity.getEntityWorld().playSound(PlayerEntity, PlayerEntity.getPosition(), sound,
                    SoundCategory.VOICE, 1.0F, 1.0F);
        } else {
            if (!playerOnly) {
                PlayerEntity.getEntityWorld().playSound(PlayerEntity, PlayerEntity.getPosition(), sound,
                        SoundCategory.VOICE, 1.0F, 1.0F);
            }
        }

    }
}
