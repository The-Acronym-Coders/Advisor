package xyz.brassgoggledcoders.advisor.cause.entry;

import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.advisor.api.cause.ICause;
import xyz.brassgoggledcoders.advisor.api.effect.Effect;

import java.util.function.Consumer;
import java.util.function.Function;

public interface ICauseEntry {
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean build(Function<ResourceLocation, ICause> getCause,
                  Function<ResourceLocation, Effect> getEffect,
                  Consumer<Effect> consumeEffect
    );
}
