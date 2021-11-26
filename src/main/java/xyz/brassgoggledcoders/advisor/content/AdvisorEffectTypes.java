package xyz.brassgoggledcoders.advisor.content;

import com.mojang.serialization.Codec;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import xyz.brassgoggledcoders.advisor.Advisor;
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

    public static final Codec<EffectType> CODEC = ResourceLocation.CODEC.xmap(
            resourceLocation -> REGISTRY_SUPPLIER.get().getValue(resourceLocation),
            ForgeRegistryEntry::getRegistryName
    );

    public static final RegistryObject<EffectType> MESSAGE = DEFERRED_EFFECTS.register(
            "message",
            () -> EffectType.of(MessageEffect.CODEC)
    );

    public static void setup(IEventBus modBus) {
        DEFERRED_EFFECTS.register(modBus);
    }
}
