package xyz.brassgoggledcoders.advisor.cause.entry;

import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.advisor.api.cause.ICause;
import xyz.brassgoggledcoders.advisor.api.effect.Effect;

import java.util.function.Consumer;
import java.util.function.Function;

public class EffectEntry implements ICauseEntry {
    private final ResourceLocation effectId;
    private final boolean required;

    public EffectEntry(ResourceLocation effectId, boolean required) {
        this.effectId = effectId;
        this.required = required;
    }

    @Override
    public boolean build(Function<ResourceLocation, ICause> getCause, Function<ResourceLocation, Effect> getEffect, Consumer<Effect> consumeEffect) {
        Effect effect = getEffect.apply(effectId);

        if (effect != null) {
            consumeEffect.accept(effect);
            return true;
        } else {
            return !required;
        }
    }
}
