package xyz.brassgoggledcoders.advisor.cause;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.advisor.api.cause.ICause;
import xyz.brassgoggledcoders.advisor.api.effect.Effect;
import xyz.brassgoggledcoders.advisor.cause.entry.CauseEntry;
import xyz.brassgoggledcoders.advisor.cause.entry.EffectEntry;
import xyz.brassgoggledcoders.advisor.cause.entry.ICauseEntry;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class CauseBuilder {
    private final List<ICauseEntry> entryList;

    public CauseBuilder() {
        this.entryList = Lists.newArrayList();
    }

    public void addFromJson(JsonObject jsonObject) throws JsonParseException {
        JsonArray valuesArray = JSONUtils.getAsJsonArray(jsonObject, "values");

        if (JSONUtils.getAsBoolean(jsonObject, "clear", false)) {
            this.entryList.clear();
        }

        for (JsonElement jsonElement : valuesArray) {
            String value;
            boolean required;

            if (jsonElement.isJsonObject()) {
                JsonObject valueObject = jsonElement.getAsJsonObject();
                value = JSONUtils.getAsString(valueObject, "id");
                required = JSONUtils.getAsBoolean(valueObject, "required", true);
            } else {
                value = JSONUtils.convertToString(jsonElement, "id");
                required = true;
            }

            if (value.startsWith("#")) {
                entryList.add(new CauseEntry(new ResourceLocation(value.substring(1)), required));
            } else {
                entryList.add(new EffectEntry(new ResourceLocation(value), required));
            }
        }
    }

    public Stream<ICauseEntry> getUnresolvedEntries(Function<ResourceLocation, ICause> getCause,
                                                    Function<ResourceLocation, Effect> getEffect) {
        return this.entryList.stream()
                .filter((causeEntry) -> !causeEntry.build(getCause, getEffect, (effect) -> {
                }));
    }

    public Optional<Cause> build(Function<ResourceLocation, ICause> getCause, Function<ResourceLocation, Effect> getEffect) {
        ImmutableSet.Builder<Effect> builder = ImmutableSet.builder();

        for (ICauseEntry causeEntry : this.entryList) {
            if (!causeEntry.build(getCause, getEffect, builder::add)) {
                return Optional.empty();
            }
        }

        return Optional.of(new Cause(builder.build()));
    }
}
