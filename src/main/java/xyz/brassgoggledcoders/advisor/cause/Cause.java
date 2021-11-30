package xyz.brassgoggledcoders.advisor.cause;

import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.advisor.api.cause.CauseContext;
import xyz.brassgoggledcoders.advisor.api.cause.ICause;
import xyz.brassgoggledcoders.advisor.api.effect.EffectContext;
import xyz.brassgoggledcoders.advisor.api.effecttable.IEffectTable;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicBoolean;

public class Cause implements ICause {
    private final IEffectTable effectTable;
    private final ResourceLocation id;

    public Cause(ResourceLocation id, IEffectTable effectTable) {
        this.effectTable = effectTable;
        this.id = id;
    }

    @Override
    public boolean perform(@Nonnull CauseContext context) {
        EffectContext effectContext = EffectContext.fromCauseContext(context);
        AtomicBoolean performed = new AtomicBoolean(false);
        this.effectTable.gatherEffects(effectContext, effect -> {
            effect.perform(effectContext);
            performed.set(true);
        });
        return performed.get();
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

}
