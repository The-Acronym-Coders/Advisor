package xyz.brassgoggledcoders.advisor.content;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import xyz.brassgoggledcoders.advisor.Advisor;
import xyz.brassgoggledcoders.advisor.api.effect.Effect;
import xyz.brassgoggledcoders.advisor.api.effect.EffectType;
import xyz.brassgoggledcoders.advisor.effect.message.MessageEffect;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class AdvisorEffectTypes {
    private static final DeferredRegister<EffectType> DEFERRED_EFFECTS = DeferredRegister.create(
            EffectType.class,
            Advisor.ID
    );

    public static final Supplier<IForgeRegistry<EffectType>> REGISTRY_SUPPLIER = DEFERRED_EFFECTS.makeRegistry(
            "effect_types",
            RegistryBuilder::new
    );

    public static final Codec<EffectType> CODEC = ResourceLocation.CODEC.flatXmap(
            resourceLocation -> {
                EffectType effectType = REGISTRY_SUPPLIER.get().getValue(resourceLocation);
                if (effectType != null) {
                    return DataResult.success(effectType);
                } else {
                    return DataResult.error("No type found for " + resourceLocation.toString());
                }
            },
            entry -> DataResult.success(entry.getRegistryName())
    );

    public static final Codec<Effect> DISPATCH_CODEC = AdvisorEffectTypes.CODEC.dispatch(
            "type",
            Effect::getType,
            EffectType::getCodec
    );

    public static final RegistryObject<EffectType> MESSAGE = DEFERRED_EFFECTS.register(
            "message",
            () -> EffectType.of(MessageEffect.CODEC)
    );

    public static void setup(IEventBus modBus) {
        DEFERRED_EFFECTS.register(modBus);
    }
}
