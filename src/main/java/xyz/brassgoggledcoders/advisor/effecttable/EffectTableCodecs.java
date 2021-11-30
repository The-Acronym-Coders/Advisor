package xyz.brassgoggledcoders.advisor.effecttable;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.advisor.api.AdvisorAPI;
import xyz.brassgoggledcoders.advisor.api.effect.Effect;
import xyz.brassgoggledcoders.advisor.api.effecttable.IEffectTable;
import xyz.brassgoggledcoders.advisor.codec.BuildOrGetCodec;
import xyz.brassgoggledcoders.advisor.codec.GsonCodec;
import xyz.brassgoggledcoders.advisor.content.AdvisorEffectTypes;

public class EffectTableCodecs {
    public static final Codec<ILootCondition> LOOT_CONDITION = new GsonCodec<>(
            ILootCondition.class,
            LootSerializers.createConditionSerializer()
    );

    public static final Codec<IRandomRange> RANDOM_RANGE = new GsonCodec<>(
            IRandomRange.class,
            LootSerializers.createConditionSerializer()
    );

    public static final Codec<RandomValueRange> RANDOM_VALUE_RANGE = new GsonCodec<>(
            RandomValueRange.class,
            LootSerializers.createConditionSerializer()
    );

    public static final Codec<LootParameterSet> LOOT_PARAMETER_SET = ResourceLocation.CODEC.xmap(
            LootParameterSets::get,
            LootParameterSets::getKey
    );

    public static final Codec<Effect> GET_OR_BUILD_EFFECT = new BuildOrGetCodec<>(
            AdvisorAPI.getEffectManager()::getValue,
            AdvisorEffectTypes.DISPATCH_CODEC
    );

    public static final Codec<EffectEntry> ENTRY = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("weight").orElse(1).forGetter(EffectEntry::getWeight),
            Codec.INT.fieldOf("quality").orElse(0).forGetter(EffectEntry::getQuality),
            Codec.list(LOOT_CONDITION).fieldOf("conditions").orElseGet(Lists::newArrayList).forGetter(EffectEntry::getConditions),
            GET_OR_BUILD_EFFECT.fieldOf("effect").forGetter(EffectEntry::getEffect)
    ).apply(instance, EffectEntry::new));

    public static final Codec<EffectPool> POOL = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(ENTRY).fieldOf("entries").forGetter(EffectPool::getEffectEntries),
            Codec.list(LOOT_CONDITION).fieldOf("conditions").orElseGet(Lists::newArrayList).forGetter(EffectPool::getConditions),
            RANDOM_RANGE.fieldOf("rolls").orElseGet(() -> ConstantRange.exactly(1)).forGetter(EffectPool::getRolls),
            RANDOM_VALUE_RANGE.fieldOf("bonusRolls").orElseGet(() -> RandomValueRange.between(0, 0)).forGetter(EffectPool::getBonusRolls)
    ).apply(instance, EffectPool::new));

    public static final Codec<EffectTable> TABLE = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(EffectTable::getId),
            LOOT_PARAMETER_SET.fieldOf("type").orElse(LootParameterSets.ALL_PARAMS).forGetter(EffectTable::getParameterSet),
            Codec.list(POOL).fieldOf("pools").forGetter(EffectTable::getPools)
    ).apply(instance, EffectTable::new));

    public static final Codec<IEffectTable> GET_OR_BUILD_EFFECT_TABLE = new BuildOrGetCodec<>(
            AdvisorAPI.getEffectTableManager()::getValue,
            TABLE.flatXmap(
                    DataResult::success,
                    effectTable -> {
                        if (effectTable instanceof EffectTable) {
                            return DataResult.success((EffectTable) effectTable);
                        } else {
                            return DataResult.error(effectTable.toString() + " is not EffectTable");
                        }
                    }
            )
    );
}
