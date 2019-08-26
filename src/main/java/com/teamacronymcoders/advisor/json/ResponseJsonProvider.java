package com.teamacronymcoders.advisor.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.teamacronymcoders.advisor.api.AdvisorAPI;
import com.teamacronymcoders.advisor.api.response.Response;
import com.teamacronymcoders.advisor.api.response.ResponseProvider;
import com.teamacronymcoders.advisor.json.loader.IJsonProvider;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class ResponseJsonProvider implements IJsonProvider<Response> {
    @Override
    public Response provide(JsonObject jsonObject) throws JsonParseException {
        ResourceLocation name = new ResourceLocation(JSONUtils.getString(jsonObject, "type"));
        ResponseProvider provider = AdvisorAPI.RESPONSE_PROVIDERS.getValue(name);
        if (provider != null) {
            return provider.provide(jsonObject);
        } else {
            throw new JsonParseException("No Provider found for type: " + name);
        }
    }
}
