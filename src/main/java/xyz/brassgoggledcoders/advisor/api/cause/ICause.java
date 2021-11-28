package xyz.brassgoggledcoders.advisor.api.cause;

import net.minecraft.entity.player.PlayerEntity;
import xyz.brassgoggledcoders.advisor.api.effect.Effect;

import javax.annotation.Nonnull;
import java.util.Collection;

public interface ICause {
    @Nonnull
    Collection<Effect> getEffects();

    void perform(@Nonnull PlayerEntity player);
}
