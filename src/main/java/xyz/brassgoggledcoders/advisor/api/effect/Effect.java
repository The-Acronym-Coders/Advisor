package xyz.brassgoggledcoders.advisor.api.effect;

import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.advisor.api.manager.IManagerEntry;

public abstract class Effect implements IManagerEntry {
    private final ResourceLocation id;

    public Effect(ResourceLocation id) {
        this.id = id;
    }

    public abstract boolean perform(EffectContext context);

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public abstract EffectType getType();
}
