package xyz.brassgoggledcoders.advisor.api.effect;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public abstract class Effect {
    private ResourceLocation id;

    public Effect() {

    }

    public abstract boolean perform(EffectContext context);

    public ResourceLocation getId() {
        return id;
    }

    public void setId(ResourceLocation id) {
        this.id = id;
    }

    public abstract EffectType getType();
}
