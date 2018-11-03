package com.teamacronymcoders.advisor.json.constructor;

import com.google.gson.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

public class ConstructorDeserializer implements JsonDeserializer<Object> {
    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String className = typeOfT.getTypeName();
        JsonObject jsonObject = json.getAsJsonObject();
        if (typeOfT instanceof Class) {
            try {
                Class clazz = Class.forName(className);
                for (Constructor constructor : clazz.getConstructors()) {
                    if (constructor.getDeclaredAnnotation(JsonConstructor.class) != null) {
                        return handleConstructor(constructor, jsonObject, context);
                    }
                }
            } catch (ClassNotFoundException e) {
                throw new JsonParseException(String.format("Failed to Find Class %s", className));
            }
        }
        throw new JsonParseException(String.format("Type %s is not a class", className));
    }

    private Object handleConstructor(Constructor constructor, JsonObject json, JsonDeserializationContext context) {
        try {
            Parameter[] parameters = constructor.getParameters();
            Object[] objects = new Object[parameters.length];

            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                JsonProperty jsonProperty = parameter.getAnnotation(JsonProperty.class);
                if (jsonProperty == null) {
                    throw new JsonParseException(String.format("Parameter field #%d lacks required annotation JsonProperty", i));
                }
                JsonElement parameterElement = json.get(jsonProperty.value());
                if (parameterElement == null) {
                    if (jsonProperty.required()) {
                        throw new JsonParseException(String.format("Field %s is null, but is marked as required", jsonProperty.value()));
                    } else {
                        objects[i] = null;
                    }
                } else {
                    objects[i] = context.deserialize(parameterElement, parameter.getType());
                }

            }
            return constructor.newInstance(objects);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new JsonParseException("Failed to Handled Constructor", e);
        }
    }
}
