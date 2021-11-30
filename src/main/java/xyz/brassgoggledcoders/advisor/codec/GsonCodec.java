package xyz.brassgoggledcoders.advisor.codec;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;

public class GsonCodec<T> implements Codec<T> {
    private final Class<T> tClass;
    private final Gson gson;

    public GsonCodec(Class<T> tClass, GsonBuilder gson) {
        this(tClass, gson.create());
    }

    public GsonCodec(Class<T> tClass, Gson gson) {
        this.tClass = tClass;
        this.gson = gson;
    }

    @Override
    public <T1> DataResult<Pair<T, T1>> decode(DynamicOps<T1> ops, T1 input) {
        JsonElement jsonElement = ops.convertTo(JsonOps.INSTANCE, input);

        try {
            return DataResult.success(Pair.of(gson.fromJson(jsonElement, tClass), ops.empty()));
        } catch (JsonParseException e) {
            return DataResult.error(e.getMessage());
        }
    }

    @Override
    public <T1> DataResult<T1> encode(T input, DynamicOps<T1> ops, T1 prefix) {
        JsonElement jsonElement = gson.toJsonTree(input);
        return DataResult.success(JsonOps.INSTANCE.convertTo(ops, jsonElement));
    }
}
