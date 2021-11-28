package xyz.brassgoggledcoders.advisor.api;

import com.mojang.serialization.Codec;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;
import xyz.brassgoggledcoders.advisor.api.effect.EffectType;

public class AdvisorCodecs {
    private static final Lazy<IForgeRegistry<EffectType>> LAZY_EFFECT_TYPE = Lazy.of(
            () -> RegistryManager.ACTIVE.getRegistry(EffectType.class)
    );

    public static final Codec<EffectType> EFFECT_TYPE_CODEC = ResourceLocation.CODEC.xmap(
            resourceLocation -> LAZY_EFFECT_TYPE.get().getValue(resourceLocation),
            ForgeRegistryEntry::getRegistryName
    );
}
