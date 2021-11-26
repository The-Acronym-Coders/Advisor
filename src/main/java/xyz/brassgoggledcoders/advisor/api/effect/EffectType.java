package xyz.brassgoggledcoders.advisor.api.effect;

import com.mojang.serialization.Codec;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class EffectType extends ForgeRegistryEntry<EffectType> {
    private final Codec<Effect> codec;

    public EffectType(Codec<Effect> codec) {
        this.codec = codec;
    }

    public Codec<Effect> getCodec() {
        return codec;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Effect> EffectType of(Codec<T> codec) {
        return new EffectType((Codec<Effect>) codec);
    }
}
