package xyz.brassgoggledcoders.advisor.effecttable;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;

import java.util.function.Function;
import java.util.function.Supplier;

public class EffectValidationTracker {
    private final Multimap<String, String> problems;
    private final Supplier<String> context;
    private final LootParameterSet parameterSet;
    private final Function<ResourceLocation, ILootCondition> conditionResolver;

    private String contextCache;

    public EffectValidationTracker(LootParameterSet parameterSet, Function<ResourceLocation, ILootCondition> conditionResolver) {
        this(HashMultimap.create(), () -> "", parameterSet, conditionResolver);
    }

    public EffectValidationTracker(Multimap<String, String> problems,
                                   Supplier<String> context,
                                   LootParameterSet parameterSet,
                                   Function<ResourceLocation, ILootCondition> conditionResolver) {
        this.problems = problems;
        this.context = context;
        this.parameterSet = parameterSet;
        this.conditionResolver = conditionResolver;
    }

    private String getContext() {
        if (this.contextCache == null) {
            this.contextCache = this.context.get();
        }

        return this.contextCache;
    }

    public void reportProblem(String pProblem) {
        this.problems.put(this.getContext(), pProblem);
    }

    public EffectValidationTracker enterTable(String tableContext, LootParameterSet lootParameterSet) {
        return new EffectValidationTracker(
                this.problems,
                () -> this.getContext() + tableContext,
                lootParameterSet,
                conditionResolver
        );
    }

    public EffectValidationTracker forChild(String childContext) {
        return new EffectValidationTracker(
                this.problems,
                () -> this.getContext() + childContext,
                parameterSet,
                conditionResolver
        );
    }

    public ValidationTracker forLootChild(String childContext) {
        return new ValidationTracker(
                this.problems,
                () -> this.getContext() + childContext,
                parameterSet,
                conditionResolver,
                Sets.newHashSet(),
                id -> null,
                Sets.newHashSet()
        );
    }

    public Multimap<String, String> getProblems() {
        return ImmutableMultimap.copyOf(this.problems);
    }
}
