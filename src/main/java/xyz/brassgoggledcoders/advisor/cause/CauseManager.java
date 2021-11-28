package xyz.brassgoggledcoders.advisor.cause;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.advisor.api.cause.ICause;
import xyz.brassgoggledcoders.advisor.api.cause.ICauseManager;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;
import java.util.function.Predicate;

public class CauseManager extends JsonReloadListener implements ICauseManager {
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
    public ICause getCause(ResourceLocation id) {
        return this.causes.get(id);
    }
}
