package xyz.brassgoggledcoders.advisor.codec;

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
import xyz.brassgoggledcoders.advisor.api.manager.IManager;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class CodecReloadListener<U, T extends U> extends JsonReloadListener implements IManager<U> {
    private final Codec<T> codec;
    private final Map<ResourceLocation, T> values;

    public CodecReloadListener(Codec<T> codec, String directory) {
        super(new Gson(), directory);
        this.codec = codec;
        this.values = Maps.newHashMap();
    }

    @Override
    public T getValue(@Nonnull ResourceLocation id) {
        return values.get(id);
    }

    @Nonnull
    @Override
    public Collection<ResourceLocation> getIds() {
        return values.keySet();
    }

    @Nonnull
    @Override
    public Collection<String> getExamples() {
        return values.keySet()
                .parallelStream()
                .map(ResourceLocation::toString)
                .limit(5)
                .collect(Collectors.toSet());
    }

    protected Collection<T> getValues() {
        return this.values.values();
    }

    @Override
    @ParametersAreNonnullByDefault
    protected void apply(Map<ResourceLocation, JsonElement> pObject, IResourceManager pResourceManager, IProfiler pProfiler) {
        values.clear();
        for (Map.Entry<ResourceLocation, JsonElement> entry : pObject.entrySet()) {
            ResourceLocation id = entry.getKey();
            JsonElement element = entry.getValue();
            if (element.isJsonObject()) {
                element.getAsJsonObject()
                        .addProperty("id", id.toString());
                codec.parse(JsonOps.INSTANCE, element)
                        .resultOrPartial(error -> Advisor.LOGGER.error("Failed to Parse {}: {}", id, error))
                        .ifPresent(value -> values.put(id, value));
            } else {
                Advisor.LOGGER.error("Failed to Parse {}: Element must be an Object", id);
            }
        }
        this.validate();
    }

    protected void validate() {

    }
}
