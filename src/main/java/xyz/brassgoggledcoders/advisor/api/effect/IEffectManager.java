package xyz.brassgoggledcoders.advisor.api.effect;

import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

public interface IEffectManager {
    @Nullable
    Effect getEffect(@Nonnull ResourceLocation resourceLocation);

    @Nonnull
    Collection<Effect> getEffects();

    @Nonnull
    Collection<ResourceLocation> getIds();

    @Nonnull
    Collection<String> getExamples();
}
