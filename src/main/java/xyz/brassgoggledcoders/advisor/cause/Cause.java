package xyz.brassgoggledcoders.advisor.cause;

import xyz.brassgoggledcoders.advisor.api.cause.CauseContext;
import xyz.brassgoggledcoders.advisor.api.cause.ICause;
import xyz.brassgoggledcoders.advisor.api.effect.EffectContext;
import xyz.brassgoggledcoders.advisor.effecttable.EffectTable;

import javax.annotation.Nonnull;

public class Cause implements ICause {
    private final EffectTable effectTable;

    public Cause(EffectTable effectTable) {
        this.effectTable = effectTable;
    }

    @Override
    public void perform(@Nonnull CauseContext context) {
        this.effectTable.getEffects(EffectContext.fromCauseContext(context))
                .forEach(effect -> effect.perform(context.getPlayer()));
    }
}
