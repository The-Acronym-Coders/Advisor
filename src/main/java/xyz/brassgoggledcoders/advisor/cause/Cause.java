package xyz.brassgoggledcoders.advisor.cause;

import net.minecraft.loot.LootParameterSets;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.advisor.api.cause.CauseContext;
import xyz.brassgoggledcoders.advisor.api.cause.ICause;
import xyz.brassgoggledcoders.advisor.api.effect.Effect;
import xyz.brassgoggledcoders.advisor.api.effect.EffectContext;
import xyz.brassgoggledcoders.advisor.api.effecttable.IEffectTable;
import xyz.brassgoggledcoders.advisor.effecttable.EffectEntry;
import xyz.brassgoggledcoders.advisor.effecttable.EffectPool;
import xyz.brassgoggledcoders.advisor.effecttable.EffectTable;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Cause implements ICause {
    private final IEffectTable effectTable;
    private final ResourceLocation id;

    public Cause(ResourceLocation id, IEffectTable effectTable) {
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
