package xyz.brassgoggledcoders.advisor.effecttable;

import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.advisor.Advisor;
import xyz.brassgoggledcoders.advisor.api.effect.Effect;
import xyz.brassgoggledcoders.advisor.api.effect.EffectContext;
import xyz.brassgoggledcoders.advisor.api.effecttable.EffectValidationTracker;
import xyz.brassgoggledcoders.advisor.api.effecttable.IEffectTable;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class EffectTable implements IEffectTable {
    private final ResourceLocation id;
    private final LootParameterSet parameterSet;
    private final List<EffectPool> effectPools;

    public EffectTable(ResourceLocation id, LootParameterSet parameterSet, List<EffectPool> effectPools) {
        this.id = id;
        this.parameterSet = parameterSet;
        this.effectPools = effectPools;
    }

    public EffectTable(List<Effect> effects) {
        this(null, LootParameterSets.ALL_PARAMS, Collections.singletonList(
                new EffectPool(
                        effects.stream()
                                .map(EffectEntry::new)
                                .collect(Collectors.toList())
                )
        ));
    }

    @Override
    public void gatherEffects(EffectContext context, Consumer<Effect> effectConsumer) {
        if (context.visitTable(this)) {
            for (EffectPool effectPool : effectPools) {
                effectPool.gatherEffects(context, effectConsumer);
            }
        } else {
            Advisor.LOGGER.error("Found infinite loop in LootTable: " + this.getId());
        }
    }

    public void validate(EffectValidationTracker validationTracker) {
        if (this.effectPools.isEmpty()) {
            validationTracker.reportProblem("'pools' is empty");
        }
        for (int i = 0; i < this.effectPools.size(); ++i) {
            this.effectPools.get(i).validate(validationTracker.forChild(".pools[" + i + "]"));
        }
    }

    public ResourceLocation getId() {
        return id;
    }

    public LootParameterSet getParameterSet() {
        return parameterSet;
    }

    public List<EffectPool> getPools() {
        return effectPools;
    }
}
