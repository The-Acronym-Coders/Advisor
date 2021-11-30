package xyz.brassgoggledcoders.advisor.cause;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.advisor.Advisor;
import xyz.brassgoggledcoders.advisor.api.cause.ICause;
import xyz.brassgoggledcoders.advisor.api.effect.Effect;
import xyz.brassgoggledcoders.advisor.api.manager.IManager;
import xyz.brassgoggledcoders.advisor.effecttable.EffectTable;
import xyz.brassgoggledcoders.advisor.effecttable.EffectTableCodecs;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CauseManager extends JsonReloadListener implements IManager<ICause> {
    private static final Gson GSON = new Gson();
    private static final String DIRECTORY = "advisor/cause";

    private static final Codec<List<Effect>> EFFECT_LIST = EffectTableCodecs.GET_OR_BUILD_EFFECT.listOf();

    private final Map<ResourceLocation, Cause> causes;

    public CauseManager() {
        super(GSON, DIRECTORY);
        this.causes = Maps.newHashMap();
    }

    @Override
    @ParametersAreNonnullByDefault
    protected void apply(Map<ResourceLocation, JsonElement> pObject, IResourceManager pResourceManager, IProfiler pProfiler) {
        for (Map.Entry<ResourceLocation, JsonElement> entry : pObject.entrySet()) {
            try {
                ResourceLocation id = entry.getKey();
                JsonObject jsonObject = JSONUtils.convertToJsonObject(entry.getValue(), "root");
                if (jsonObject.has("table")) {
                    EffectTableCodecs.GET_OR_BUILD_EFFECT_TABLE.parse(JsonOps.INSTANCE, jsonObject.get("table"))
                            .resultOrPartial(error -> Advisor.LOGGER.error("Failed to Parse {}.table: {}", id, error))
                            .ifPresent(value -> causes.put(id, new Cause(id, value)));
                } else if (jsonObject.has("effects")) {
                    EFFECT_LIST.parse(JsonOps.INSTANCE, jsonObject.get("effects"))
                            .resultOrPartial(error -> Advisor.LOGGER.error("Failed to Parse {}.table: {}", id, error))
                            .map(EffectTable::new)
                            .ifPresent(value -> causes.put(id, new Cause(id, value)));
                } else {
                    throw new JsonParseException("Missing 'table' or 'effects'");
                }
            } catch (JsonParseException e) {
                Advisor.LOGGER.error("Failed to Parse Cause {}: {}", entry.getKey(), e.getMessage(), e);
            }
        }
    }

    @Nullable
    @Override
    public ICause getValue(@Nonnull ResourceLocation resourceLocation) {
        return causes.get(resourceLocation);
    }

    @Nonnull
    @Override
    public Collection<ResourceLocation> getIds() {
        return causes.keySet();
    }

    @Nonnull
    @Override
    public Collection<String> getExamples() {
        return causes.keySet()
                .parallelStream()
                .map(ResourceLocation::toString)
                .limit(5)
                .collect(Collectors.toSet());
    }
}
