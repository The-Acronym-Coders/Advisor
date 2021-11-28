package xyz.brassgoggledcoders.advisor.effecttable;

import net.minecraft.loot.IRandomRange;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.LootConditionManager;
import net.minecraft.util.math.MathHelper;
import xyz.brassgoggledcoders.advisor.api.effect.Effect;
import xyz.brassgoggledcoders.advisor.api.effect.EffectContext;

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

    public void getEffects(EffectContext context, Consumer<Effect> effectConsumer) {
        if (this.compositeConditions.test(context.getLootContext())) {
            Random random = context.getLootContext().getRandom();
            int i = this.rolls.getInt(random) + MathHelper.floor(this.bonusRolls.getFloat(random) * context.getLootContext().getLuck());


            for (int j = 0; j < i; ++j) {
                this.addRandomItem(consumer, pLootContext);
            }

        }
    }
}
