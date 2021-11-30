package xyz.brassgoggledcoders.advisor.effecttable;

import com.google.common.collect.Lists;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.IRandomRange;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.LootConditionManager;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.tuple.Pair;
import xyz.brassgoggledcoders.advisor.api.effect.Effect;
import xyz.brassgoggledcoders.advisor.api.effect.EffectContext;
import xyz.brassgoggledcoders.advisor.api.effecttable.EffectValidationTracker;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class EffectPool {
    private final List<EffectEntry> effectEntries;
    private final List<ILootCondition> conditions;
    private final Predicate<LootContext> compositeConditions;
    private final IRandomRange rolls;
    private final RandomValueRange bonusRolls;

    public EffectPool(List<EffectEntry> effectEntries, List<ILootCondition> conditions, IRandomRange rolls, RandomValueRange bonusRolls) {
        this.effectEntries = effectEntries;
        this.conditions = conditions;
        this.compositeConditions = LootConditionManager.andConditions(conditions.toArray(new ILootCondition[0]));
        this.rolls = rolls;
        this.bonusRolls = bonusRolls;
    }

    public EffectPool(List<EffectEntry> effectEntries) {
        this(effectEntries, Collections.emptyList(), ConstantRange.exactly(1), RandomValueRange.between(0, 0));
    }

    public void validate(EffectValidationTracker tracker) {
        for (int i = 0; i < this.conditions.size(); ++i) {
            this.conditions.get(i).validate(tracker.forLootChild(".condition[" + i + "]"));
        }

        if (this.effectEntries.isEmpty()) {
            tracker.reportProblem("'entries' is empty");
        }

        for (int i = 0; i < this.effectEntries.size(); ++i) {
            this.effectEntries.get(i).validate(tracker.forChild(".entries[" + i + "]"));
        }
    }

    public void gatherEffects(EffectContext context, Consumer<Effect> gatherer) {
        if (this.compositeConditions.test(context.getLootContext())) {
            Random random = context.getLootContext().getRandom();
            int rolls = this.rolls.getInt(random) + MathHelper.floor(this.bonusRolls.getFloat(random) * context.getLootContext().getLuck());

            List<Pair<Effect, Integer>> effects = Lists.newArrayList();
            MutableInt totalWeight = new MutableInt(0);
            for (EffectEntry entry : effectEntries) {
                Effect effect = entry.getEffectFromContext(context);
                if (effect != null) {
                    int weight = entry.getLuckyWeight(context.getLootContext().getLuck());
                    effects.add(Pair.of(effect, weight));
                    totalWeight.add(weight);
                }
            }

            for (int i = 0; i < rolls; ++i) {
                int effectSize = effects.size();
                if (totalWeight.intValue() != 0 && effectSize != 0) {
                    if (effectSize == 1) {
                        gatherer.accept(effects.get(0).getLeft());
                    } else {
                        int nextWeight = random.nextInt(totalWeight.intValue());

                        for (Pair<Effect, Integer> effect : effects) {
                            nextWeight -= effect.getRight();
                            if (nextWeight < 0) {
                                gatherer.accept(effect.getLeft());
                            }
                        }
                    }
                }
            }
        }
    }

    public List<EffectEntry> getEffectEntries() {
        return effectEntries;
    }

    public List<ILootCondition> getConditions() {
        return conditions;
    }

    public IRandomRange getRolls() {
        return rolls;
    }

    public RandomValueRange getBonusRolls() {
        return bonusRolls;
    }
}
