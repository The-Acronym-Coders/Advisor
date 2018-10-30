package com.teamacronymcoders.advisor.speech.sound;

import com.google.gson.annotations.JsonAdapter;
import com.teamacronymcoders.advisor.api.speech.IResponse;
import com.teamacronymcoders.advisor.json.SoundEventLazyLoadedDeserializer;
import com.teamacronymcoders.advisor.json.SoundEventLazyLoadedType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class SoundResponse extends IForgeRegistryEntry.Impl<IResponse> implements IResponse {
    @JsonAdapter(SoundEventLazyLoadedDeserializer.class)
    private final SoundEventLazyLoadedType sound;
    private final boolean playerOnly;

    public SoundResponse(SoundEventLazyLoadedType sound, boolean playerOnly) {
        this.sound = sound;
        this.playerOnly = playerOnly;
    }

    @Override
    public boolean canRespond(EntityPlayer entityPlayer) {
        return true;
    }

    @Override
    public void respond(EntityPlayer entityPlayer, boolean client) {
        if (client) {
            entityPlayer.getEntityWorld().playSound(entityPlayer, entityPlayer.getPosition(), sound.get(),
                    SoundCategory.VOICE, 1.0F, 1.0F);
        } else {
            if (!playerOnly) {
                entityPlayer.getEntityWorld().playSound(entityPlayer, entityPlayer.getPosition(), sound.get(),
                        SoundCategory.VOICE, 1.0F, 1.0F);
            }
        }

    }
}
