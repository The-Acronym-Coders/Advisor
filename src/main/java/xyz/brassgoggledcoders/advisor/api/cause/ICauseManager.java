package xyz.brassgoggledcoders.advisor.api.cause;

import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public interface ICauseManager extends IFutureReloadListener {
    @Nullable
    ICause getCause(ResourceLocation id);
}
