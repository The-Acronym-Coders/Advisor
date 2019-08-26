package com.teamacronymcoders.advisor.json.loader;

import net.minecraft.util.ResourceLocation;

public interface IJsonDirector<T> {
    void put(ResourceLocation resourceLocation, T value);

    void clear();
}
