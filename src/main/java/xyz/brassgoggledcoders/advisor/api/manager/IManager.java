package xyz.brassgoggledcoders.advisor.api.manager;

import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

public interface IManager<T> extends IFutureReloadListener {
    @Nullable
    T getValue(@Nonnull ResourceLocation resourceLocation);

    @Nonnull
    Collection<ResourceLocation> getIds();

    @Nonnull
    Collection<String> getExamples();
}
