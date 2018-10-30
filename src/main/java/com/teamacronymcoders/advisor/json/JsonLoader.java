package com.teamacronymcoders.advisor.json;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonParseException;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class JsonLoader<T> {
    private final String folders;
    private final Class<T> tClass;
    private final Gson gson;
    private final Supplier<Logger> logger;

    public JsonLoader(String folders, Class<T> tClass, Supplier<Logger> logger) {
        this.folders = folders;
        this.tClass = tClass;
        this.logger = logger;
        this.gson = new Gson();
    }

    public JsonLoader(String folders, Class<T> tClass, Supplier<Logger> logger, JsonDeserializer<T> deserializer) {
        this.folders = folders;
        this.tClass = tClass;
        this.logger = logger;
        this.gson = new GsonBuilder().registerTypeAdapter(tClass, deserializer).create();
    }

    public List<T> load() {
        return Loader.instance().getActiveModList()
                .parallelStream()
                .map(this::loadFilesForMod)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<T> loadFilesForMod(ModContainer modContainer) {
        JsonContext ctx = new JsonContext(modContainer.getModId());
        List<T> loadedObjects = Lists.newArrayList();
        CraftingHelper.findFiles(modContainer, "assets/" + modContainer.getModId() + folders,
                this::handlePreload,
                (root, file) -> {
                    this.handleFile(root, file, ctx)
                            .ifPresent(loadedObjects::add);
                    return true;
                }, true, true);

        return loadedObjects;
    }

    protected boolean handlePreload(Path root) {
        return true;
    }

    protected Optional<T> handleFile(Path root, Path file, JsonContext context) {
        String relative = root.relativize(file).toString();
        if (!"json".equals(FilenameUtils.getExtension(file.toString())) || relative.startsWith("_"))
            return Optional.empty();

        String name = FilenameUtils.removeExtension(relative).replaceAll("\\\\", "/");
        final ResourceLocation key = new ResourceLocation(context.getModId(), name);

        Optional<T> object = handleFile(key, file);
        object.ifPresent(value -> {
            if (value instanceof IForgeRegistryEntry) {
                ((IForgeRegistryEntry) value).setRegistryName(key);
            }
        });
        return object;
    }

    protected Optional<T> handleFile(ResourceLocation key, Path file) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(file)) {
            return Optional.ofNullable(gson.fromJson(bufferedReader, tClass));
        } catch (IOException | JsonParseException e) {
            logger.get().warn("Failed to load File for key: " + key, file);
            return Optional.empty();
        }
    }
}
