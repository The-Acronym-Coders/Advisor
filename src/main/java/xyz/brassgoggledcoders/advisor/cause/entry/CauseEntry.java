package xyz.brassgoggledcoders.advisor.cause.entry;

import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.advisor.api.cause.ICause;
import xyz.brassgoggledcoders.advisor.api.effect.Effect;
import xyz.brassgoggledcoders.advisor.cause.Cause;

import java.util.function.Consumer;
import java.util.function.Function;

public class CauseEntry implements ICauseEntry {
    private final ResourceLocation causeId;
    private final boolean required;

    public CauseEntry(ResourceLocation causeId, boolean required) {
        this.causeId = causeId;
        this.required = required;
    }

    @Override
    public boolean build(Function<ResourceLocation, ICause> getCause, Function<ResourceLocation, Effect> getEffect, Consumer<Effect> consumeEffect) {
        ICause cause = getCause.apply(causeId);
        if (cause != null) {
            cause.getEffects()
                    .forEach(consumeEffect);
            return true;
        } else {
            return !required;
        }
    }
}
