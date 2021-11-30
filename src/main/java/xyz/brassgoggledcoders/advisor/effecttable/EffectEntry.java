package xyz.brassgoggledcoders.advisor.effecttable;

import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.LootConditionManager;
import net.minecraft.util.math.MathHelper;
import xyz.brassgoggledcoders.advisor.api.effect.Effect;
import xyz.brassgoggledcoders.advisor.api.effect.EffectContext;
import xyz.brassgoggledcoders.advisor.api.effecttable.EffectValidationTracker;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class EffectEntry {
    private final int quality;
    private final int weight;
    private final List<ILootCondition> conditions;
    private final Predicate<LootContext> compositeCondition;
    private final Effect effect;

    public EffectEntry(int weight, int quality, List<ILootCondition> conditions, Effect effect) {
        this.quality = quality;
        this.weight = weight;
        this.conditions = conditions;
        this.compositeCondition = LootConditionManager.andConditions(this.conditions.toArray(new ILootCondition[0]));
        this.effect = effect;
    }

    public EffectEntry(Effect effect) {
        this(1, 0, Collections.emptyList(), effect);
    }

    public List<ILootCondition> getConditions() {
        return this.conditions;
    }

    public Effect getEffect() {
        return effect;
    }

    public int getWeight() {
        return this.weight;
    }

    public int getQuality() {
        return this.quality;
    }

    public int getLuckyWeight(float luck) {
        return Math.max(MathHelper.floor((float) this.weight + (float) this.quality * luck), 0);
    }

    public Effect getEffectFromContext(EffectContext context) {
        if (compositeCondition.test(context.getLootContext())) {
            return effect;
        } else {
            return null;
        }
    }

    public void validate(EffectValidationTracker tracker) {
        for (int i = 0; i < this.conditions.size(); ++i) {
            this.conditions.get(i).validate(tracker.forLootChild(".condition[" + i + "]"));
        }
    }


}
