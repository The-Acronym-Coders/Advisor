package xyz.brassgoggledcoders.advisor.cause;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.advisor.Advisor;
import xyz.brassgoggledcoders.advisor.api.AdvisorAPI;
import xyz.brassgoggledcoders.advisor.api.cause.ICause;
import xyz.brassgoggledcoders.advisor.api.cause.ICauseManager;
import xyz.brassgoggledcoders.advisor.api.effect.Effect;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CauseManager extends ReloadListener<Map<ResourceLocation, CauseBuilder>> implements ICauseManager {
    private static final Gson GSON = new Gson();
    private static final String DIRECTORY = "advisor/cause";
    private static final Predicate<String> JSON_FILE = (fileName) -> fileName.endsWith(".json");

    private final Map<ResourceLocation, Cause> causes;

    public CauseManager() {
        this.causes = Maps.newHashMap();
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    protected Map<ResourceLocation, CauseBuilder> prepare(IResourceManager pResourceManager, IProfiler pProfiler) {
        Map<ResourceLocation, CauseBuilder> causeBuilderMap = Maps.newHashMap();

        for (ResourceLocation resourceLocation : pResourceManager.listResources(DIRECTORY, JSON_FILE)) {
            ResourceLocation causeId = new ResourceLocation(
                    resourceLocation.getNamespace(),
                    resourceLocation.getPath()
                            .substring(DIRECTORY.length() + 1, resourceLocation.getPath().length() - 5)
            );

            try {
                for (IResource resource : pResourceManager.getResources(resourceLocation)) {
                    try (
                            InputStream inputstream = resource.getInputStream();
                            Reader reader = new BufferedReader(new InputStreamReader(inputstream, StandardCharsets.UTF_8))
                    ) {
                        JsonObject jsonObject = JSONUtils.fromJson(GSON, reader, JsonObject.class);
                        if (jsonObject == null) {
                            Advisor.LOGGER.error("Couldn't read Cause {} from {} in Datapack {} as it is null or empty",
                                    causeId, resourceLocation, resource.getSourceName());
                        } else {
                            causeBuilderMap.computeIfAbsent(causeId, id -> new CauseBuilder())
                                    .addFromJson(jsonObject);
                        }
                    } catch (IOException | JsonParseException exception) {
                        Advisor.LOGGER.error("Couldn't read Cause {} from {} in Datapack {}", causeId, resourceLocation,
                                resource.getSourceName(), exception);
                    }
                }
            } catch (IOException exception) {
                Advisor.LOGGER.error("Couldn't read Cause {} from {}", causeId, resourceLocation, exception);
            }
        }

        return causeBuilderMap;
    }

    @Override
    @ParametersAreNonnullByDefault
    protected void apply(Map<ResourceLocation, CauseBuilder> pObject, IResourceManager pResourceManager, IProfiler pProfiler) {
        causes.clear();
        Function<ResourceLocation, ICause> getCause = this::getCause;
        Function<ResourceLocation, Effect> getEffect = AdvisorAPI.getEffectManager()::getEffect;

        while (!pObject.isEmpty()) {
            boolean addedCause = false;
            Iterator<Map.Entry<ResourceLocation, CauseBuilder>> iterator = pObject.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<ResourceLocation, CauseBuilder> entry = iterator.next();
                Optional<Cause> optional = entry.getValue().build(getCause, getEffect);
                if (optional.isPresent()) {
                    causes.put(entry.getKey(), optional.get());
                    iterator.remove();
                    addedCause = true;
                }
            }

            if (!addedCause) {
                break;
            }
        }

        pObject.forEach((id, causeBuilder) ->
                Advisor.LOGGER.error(
                        "Couldn't load Cause {} as it is missing following references: {}",
                        id,
                        causeBuilder.getUnresolvedEntries(getCause, getEffect)
                                .map(Objects::toString)
                                .collect(Collectors.joining(","))
                )
        );
    }

    @Nullable
    @Override
    public ICause getCause(ResourceLocation id) {
        return this.causes.get(id);
    }
}
