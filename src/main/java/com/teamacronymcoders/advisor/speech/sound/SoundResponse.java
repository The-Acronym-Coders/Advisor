package com.teamacronymcoders.advisor.speech.sound;

import com.google.gson.annotations.JsonAdapter;
import com.teamacronymcoders.advisor.api.speech.Response;
import com.teamacronymcoders.advisor.json.SoundEventLazyLoadedType;
import com.teamacronymcoders.advisor.json.constructor.ConstructorDeserializer;
import com.teamacronymcoders.advisor.json.constructor.JsonConstructor;
import com.teamacronymcoders.advisor.json.constructor.JsonProperty;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;

import java.util.Optional;

@JsonAdapter(ConstructorDeserializer.class)
public class SoundResponse extends Response {
    private final SoundEventLazyLoadedType sound;
    private final boolean playerOnly;

    @JsonConstructor
    public SoundResponse(@JsonProperty(value = "sound", required = true) String sound,
                         @JsonProperty(value = "playerOnly") Boolean playerOnly) {
        this.sound = new SoundEventLazyLoadedType(sound);
        this.playerOnly = Optional.ofNullable(playerOnly)
                .orElse(true);
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
