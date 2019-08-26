package com.teamacronymcoders.advisor.api.response;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class ResponseProvider extends ForgeRegistryEntry<ResponseProvider> {
    public abstract Response provide(JsonObject jsonObject) throws JsonParseException;
}
