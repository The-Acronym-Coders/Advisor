package xyz.brassgoggledcoders.advisor.api.effect;

import com.google.gson.JsonObject;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class EffectSerializer<T extends Effect> extends ForgeRegistryEntry<EffectSerializer<?>> {

    public abstract T create(ResourceLocation id, JsonObject jsonObject);
}
