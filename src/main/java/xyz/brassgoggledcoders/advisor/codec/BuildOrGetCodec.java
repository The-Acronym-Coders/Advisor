package xyz.brassgoggledcoders.advisor.codec;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.advisor.api.manager.IManagerEntry;

import java.util.function.Function;

public class BuildOrGetCodec<T extends IManagerEntry> implements Codec<T> {
    private final Function<ResourceLocation, T> getter;
    private final Codec<T> buildCodec;

    public BuildOrGetCodec(Function<ResourceLocation, T> getter, Codec<T> buildCodec) {
        this.getter = getter;
        this.buildCodec = buildCodec;
    }

    @Override
    public <T1> DataResult<Pair<T, T1>> decode(DynamicOps<T1> ops, T1 input) {
        DataResult<ResourceLocation> idResult = ResourceLocation.CODEC.parse(ops, input);
        if (idResult.result().isPresent()) {
            return idResult.flatMap(id -> {
                T value = getter.apply(id);
                if (value != null) {
                    return DataResult.success(Pair.of(value, ops.empty()));
                } else {
                    return DataResult.error("Failed to find Value for id: " + id);
                }
            });
        }
        return buildCodec.parse(ops, input)
                .map(value -> Pair.of(value, ops.empty()));
    }

    @Override
    public <T1> DataResult<T1> encode(T input, DynamicOps<T1> ops, T1 prefix) {
        if (input.getId() == null) {
            return buildCodec.encode(input, ops, prefix);
        } else {
            return DataResult.success(ops.createString(input.getId().toString()));
        }
    }
}
