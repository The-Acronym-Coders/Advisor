package xyz.brassgoggledcoders.advisor.codec;

import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.minecraft.util.text.ITextComponent;

import java.util.Objects;

public class TextComponentCodec implements Codec<ITextComponent> {
    public static final Codec<ITextComponent> CODEC = new TextComponentCodec();

    @Override
    public <T> DataResult<Pair<ITextComponent, T>> decode(DynamicOps<T> ops, T input) {
        return ops.getMap(input)
                .map(Objects::toString)
                .flatMap(string -> {
                    try {
                        string = string.substring(8, string.length() - 1);
                        return DataResult.success(Pair.of(
                                ITextComponent.Serializer.fromJson(string),
                                ops.empty())
                        );
                    } catch (JsonParseException e) {
                        return DataResult.error(e.getMessage());
                    }
                });
    }

    @Override
    public <T> DataResult<T> encode(ITextComponent input, DynamicOps<T> ops, T prefix) {
        try {
            String text = ITextComponent.Serializer.toJson(input);
            return DataResult.success(ops.createString(text));
        } catch (JsonParseException e) {
            return DataResult.error(e.getMessage());
        }
    }
}
