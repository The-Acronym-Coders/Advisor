package com.teamacronymcoders.advisor.json.loader;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public interface IJsonProvider<T> {
    T provide(JsonObject jsonObject) throws JsonParseException;
}
