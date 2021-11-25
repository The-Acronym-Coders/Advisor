package xyz.brassgoggledcoders.advisor.json;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;
import xyz.brassgoggledcoders.advisor.Advisor;
import xyz.brassgoggledcoders.advisor.api.effect.Effect;
import xyz.brassgoggledcoders.advisor.api.effect.EffectSerializer;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;
import java.util.Map.Entry;

public class EffectJsonReloadListener extends JsonReloadListener {
    private static final Lazy<IForgeRegistry<EffectSerializer<?>>> EFFECT_SERIALIZERS = Lazy.concurrentOf(
            () -> RegistryManager.ACTIVE.getRegistry(EffectSerializer.class)
    );
    private static final Gson GSON = new Gson();

    private final Map<ResourceLocation, Effect> effects;

    public EffectJsonReloadListener() {
        super(GSON, "advisor/effect");
        this.effects = Maps.newHashMap();
    }

    public Effect getEffect(ResourceLocation id) {
        return effects.get(id);
    }

    @Override
    @ParametersAreNonnullByDefault
    protected void apply(Map<ResourceLocation, JsonElement> pObject, IResourceManager pResourceManager, IProfiler pProfiler) {
        for (Entry<ResourceLocation, JsonElement> entry : pObject.entrySet()) {
            ResourceLocation id = entry.getKey();
            JsonElement element = entry.getValue();
            Effect effect = loadEffect(id, element);
            if (effect != null) {
                effects.put(id, effect);
            }
        }
    }

    public static Effect loadEffect(ResourceLocation id, JsonElement element) {
        try {
            JsonObject jsonObject = JSONUtils.convertToJsonObject(element, "top element");
            if (CraftingHelper.processConditions(jsonObject, "conditions")) {
                ResourceLocation serializerTypeRL = ResourceLocation.tryParse(JSONUtils.getAsString(jsonObject, "type"));
                if (serializerTypeRL != null) {
                    EffectSerializer<?> effectSerializer = EFFECT_SERIALIZERS.get().getValue(serializerTypeRL);
                    if (effectSerializer != null) {
                        return effectSerializer.create(id, jsonObject);
                    } else {
                        Advisor.LOGGER.debug("");
                    }
                } else {
                    Advisor.LOGGER.debug("type must be match ResourceLocation info");
                }
            } else {
                Advisor.LOGGER.debug("Conditions Not Met for Effect {}", id);
            }
        } catch (IllegalArgumentException | JsonParseException exception) {
            Advisor.LOGGER.debug("Failed to Load Effect: " + id, exception);
        }
        return null;
    }
}
