package xyz.brassgoggledcoders.advisor.codec;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import net.minecraft.util.text.ITextComponent;

public class TextComponentCodec implements Codec<ITextComponent> {
    public static final Codec<ITextComponent> CODEC = new TextComponentCodec();

    @Override
    public <T> DataResult<Pair<ITextComponent, T>> decode(DynamicOps<T> ops, T input) {
        JsonElement jsonElement = ops.convertTo(JsonOps.INSTANCE, input);

        try {
            return DataResult.success(Pair.of(
                    ITextComponent.Serializer.fromJson(jsonElement),
                    ops.empty()
            ));
        } catch (JsonParseException e) {
            return DataResult.error(e.getMessage());
        }
    }

    @Override
    public <T> DataResult<T> encode(ITextComponent input, DynamicOps<T> ops, T prefix) {
        try {
            JsonElement jsonElement = ITextComponent.Serializer.toJsonTree(input);
            return DataResult.success(JsonOps.INSTANCE.convertTo(ops, jsonElement));
        } catch (JsonParseException e) {
            return DataResult.error(e.getMessage());
        }
    }
}
