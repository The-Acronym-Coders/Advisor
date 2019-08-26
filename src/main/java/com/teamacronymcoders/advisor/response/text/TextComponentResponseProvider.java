package com.teamacronymcoders.advisor.response.text;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.teamacronymcoders.advisor.api.response.Response;
import com.teamacronymcoders.advisor.api.response.ResponseProvider;
import net.minecraft.util.text.ITextComponent;

public class TextComponentResponseProvider extends ResponseProvider {
    @Override
    public Response provide(JsonObject jsonObject) {
        if (jsonObject.has("text")) {
            return new TextComponentResponse(ITextComponent.Serializer.fromJson(jsonObject));
        }
        throw new JsonParseException("Missing field text");
    }
}
