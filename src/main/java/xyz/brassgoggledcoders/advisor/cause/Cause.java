package xyz.brassgoggledcoders.advisor.cause;

import net.minecraft.entity.player.PlayerEntity;
import xyz.brassgoggledcoders.advisor.api.cause.ICause;
import xyz.brassgoggledcoders.advisor.api.effect.Effect;

import javax.annotation.Nonnull;
import java.util.Collection;

public class Cause implements ICause {
    private final Collection<Effect> effects;

    public Cause(Collection<Effect> effects) {
        this.effects = effects;
    }

    @Override
    @Nonnull
    public Collection<Effect> getEffects() {
        return this.effects;
    }

    @Override
    public void perform(@Nonnull PlayerEntity player) {

    }
}
