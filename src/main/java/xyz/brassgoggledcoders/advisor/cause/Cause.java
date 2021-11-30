package xyz.brassgoggledcoders.advisor.cause;

import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.advisor.api.cause.CauseContext;
import xyz.brassgoggledcoders.advisor.api.cause.ICause;
import xyz.brassgoggledcoders.advisor.api.effect.EffectContext;
import xyz.brassgoggledcoders.advisor.effecttable.EffectTable;

import javax.annotation.Nonnull;

public class Cause implements ICause {
    private final EffectTable effectTable;
    private final ResourceLocation id;

    public Cause(ResourceLocation id, EffectTable effectTable) {
        this.effectTable = effectTable;
        this.id = id;
    }

    @Override
    public void perform(@Nonnull CauseContext context) {
        EffectContext effectContext = EffectContext.fromCauseContext(context);
        this.effectTable.gatherEffects(effectContext, effect -> effect.perform(effectContext));
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }
}
