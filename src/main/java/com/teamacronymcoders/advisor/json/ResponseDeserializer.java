package com.teamacronymcoders.advisor.json;

import com.google.gson.*;
import com.teamacronymcoders.advisor.api.AdvisorRegistries;
import com.teamacronymcoders.advisor.api.speech.Response;
import net.minecraft.util.JsonUtils;

import java.lang.reflect.Type;

public class ResponseDeserializer implements JsonDeserializer<Response> {
    @Override
    public Response deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();
            String type = JsonUtils.getString(jsonObject, "type");
            Class tClass = AdvisorRegistries.RESPONSE_CLASS.get(type);
            if (tClass != null) {
                JsonElement info = jsonObject.get("info");
                if (info != null && info.isJsonObject()) {
                    return context.deserialize(info.getAsJsonObject(), tClass);
                }
                throw new JsonParseException("Failed to  find JsonObject for Info element");
            }
            throw new JsonParseException("Failed to Find Parsable Class for Type");
        }
        throw new JsonParseException("Didn't Find Json Object to Parse");
    }
}
