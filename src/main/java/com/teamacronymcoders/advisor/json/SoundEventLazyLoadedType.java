package com.teamacronymcoders.advisor.json;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundEventLazyLoadedType extends LazyLoadedType<SoundEvent> {
    public SoundEventLazyLoadedType(String valueName) {
        super(valueName);
    }

    @Override
    protected SoundEvent loadValue() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(this.getValueName()));
    }
}
