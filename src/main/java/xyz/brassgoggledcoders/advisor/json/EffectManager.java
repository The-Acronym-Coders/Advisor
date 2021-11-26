package xyz.brassgoggledcoders.advisor.json;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.advisor.Advisor;
import xyz.brassgoggledcoders.advisor.api.effect.Effect;
import xyz.brassgoggledcoders.advisor.api.effect.EffectType;
import xyz.brassgoggledcoders.advisor.api.effect.IEffectManager;
import xyz.brassgoggledcoders.advisor.content.AdvisorEffectTypes;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;
import java.util.Map.Entry;

public class EffectManager extends JsonReloadListener implements IEffectManager {
    private static final Codec<Effect> CODEC = AdvisorEffectTypes.CODEC.dispatch(
            "type",
            Effect::getType,
            EffectType::getCodec
    );

    private static final Gson GSON = new Gson();

    private final Map<ResourceLocation, Effect> effects;


    public EffectManager() {
        super(GSON, "advisor/effect");
        this.effects = Maps.newHashMap();
    }

    @Override
    public Effect getEffect(@Nonnull ResourceLocation id) {
        return effects.get(id);
    }

    @Override
    @ParametersAreNonnullByDefault
    protected void apply(Map<ResourceLocation, JsonElement> pObject, IResourceManager pResourceManager, IProfiler pProfiler) {
        for (Entry<ResourceLocation, JsonElement> entry : pObject.entrySet()) {
            ResourceLocation id = entry.getKey();
            JsonElement element = entry.getValue();
            CODEC.parse(JsonOps.INSTANCE, element)
                    .resultOrPartial(error -> Advisor.LOGGER.error("Failed to Parse {}: {}", id, error))
                    .ifPresent(effect -> {
                        effect.setId(id);
                        effects.put(id, effect);
                    });
        }
    }
}
