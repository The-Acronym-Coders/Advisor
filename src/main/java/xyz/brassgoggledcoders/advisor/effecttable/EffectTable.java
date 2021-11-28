package xyz.brassgoggledcoders.advisor.effecttable;

import net.minecraft.loot.LootParameterSet;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.advisor.Advisor;
import xyz.brassgoggledcoders.advisor.api.effect.Effect;
import xyz.brassgoggledcoders.advisor.api.effect.EffectContext;

import java.util.List;
import java.util.function.Consumer;

public class EffectTable {
    private final ResourceLocation id;
    private final LootParameterSet parameterSet;
    private final List<EffectPool> effectPools;

    public EffectTable(ResourceLocation id, LootParameterSet parameterSet, List<EffectPool> effectPools) {
        this.id = id;
        this.parameterSet = parameterSet;
        this.effectPools = effectPools;
    }

    public void getEffects(EffectContext context, Consumer<Effect> effectConsumer) {
        if (context.visitTable(this)) {
            for (EffectPool effectPool : effectPools) {
                effectPool.getEffects(context, effectConsumer);
            }
        } else {
            Advisor.LOGGER.error("Found infinite loop in LootTable: " + this.getId());
        }
    }

    public ResourceLocation getId() {
        return id;
    }

    public LootParameterSet getParameterSet() {
        return parameterSet;
    }
}
