package xyz.brassgoggledcoders.advisor.cause;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.advisor.api.manager.IManager;
import xyz.brassgoggledcoders.advisor.api.cause.ICause;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CauseManager extends JsonReloadListener implements IManager<ICause> {
    private static final Gson GSON = new Gson();
    private static final String DIRECTORY = "advisor/cause";
    private static final Predicate<String> JSON_FILE = (fileName) -> fileName.endsWith(".json");

    private final Map<ResourceLocation, Cause> causes;

    public CauseManager() {
        super(GSON, DIRECTORY);
        this.causes = Maps.newHashMap();
    }

    @Override
    @ParametersAreNonnullByDefault
    protected void apply(Map<ResourceLocation, JsonElement> pObject, IResourceManager pResourceManager, IProfiler pProfiler) {

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
