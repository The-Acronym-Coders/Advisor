package xyz.brassgoggledcoders.advisor.effect;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.advisor.api.AdvisorAPI;
import xyz.brassgoggledcoders.advisor.api.AdvisorCodecs;
import xyz.brassgoggledcoders.advisor.api.effect.Effect;
import xyz.brassgoggledcoders.advisor.api.effect.EffectType;
import xyz.brassgoggledcoders.advisor.api.effect.IEffectManager;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class EffectManager extends JsonReloadListener implements IEffectManager {
    private static final Codec<Effect> CODEC = AdvisorCodecs.EFFECT_TYPE_CODEC.dispatch(
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
    @Nonnull
    public Collection<Effect> getEffects() {
        return effects.values();
    }

    @Nonnull
    @Override
    public Collection<ResourceLocation> getIds() {
        return effects.keySet();
    }

    @Nonnull
    @Override
    public Collection<String> getExamples() {
        return effects.keySet()
                .parallelStream()
                .limit(5)
                .map(ResourceLocation::toString)
                .collect(Collectors.toSet());
    }

    @Override
    @ParametersAreNonnullByDefault
    protected void apply(Map<ResourceLocation, JsonElement> pObject, IResourceManager pResourceManager, IProfiler pProfiler) {
        effects.clear();
        for (Entry<ResourceLocation, JsonElement> entry : pObject.entrySet()) {
            ResourceLocation id = entry.getKey();
            JsonElement element = entry.getValue();
            CODEC.parse(JsonOps.INSTANCE, element)
                    .resultOrPartial(error -> AdvisorAPI.LOGGER.error("Failed to Parse {}: {}", id, error))
                    .ifPresent(effect -> {
                        effect.setId(id);
                        effects.put(id, effect);
                    });
        }
    }
}
