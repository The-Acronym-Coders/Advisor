package xyz.brassgoggledcoders.advisor.api.effect;

import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IEffectManager {
    @Nullable
    Effect getEffect(@Nonnull ResourceLocation resourceLocation);
}
