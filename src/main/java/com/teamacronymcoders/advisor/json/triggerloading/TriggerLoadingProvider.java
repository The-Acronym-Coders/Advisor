package com.teamacronymcoders.advisor.json.triggerloading;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.teamacronymcoders.advisor.api.AdvisorAPI;
import com.teamacronymcoders.advisor.api.response.Response;
import com.teamacronymcoders.advisor.api.runcondition.IRunCondition;
import com.teamacronymcoders.advisor.api.runcondition.RunConditionProvider;
import com.teamacronymcoders.advisor.api.trigger.Trigger;
import com.teamacronymcoders.advisor.api.trigger.TriggerableResponse;
import com.teamacronymcoders.advisor.json.ResponseJsonProvider;
import com.teamacronymcoders.advisor.json.loader.IJsonProvider;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class TriggerLoadingProvider implements IJsonProvider<TriggerLoading> {
    private final ResponseJsonProvider responseJsonProvider;

    public TriggerLoadingProvider() {
        responseJsonProvider = new ResponseJsonProvider();
    }

    @Override
    public TriggerLoading provide(JsonObject jsonObject) throws JsonParseException {
        ResourceLocation triggerType = new ResourceLocation(JSONUtils.getString(jsonObject, "type"));
        Trigger trigger = AdvisorAPI.TRIGGERS.getValue(triggerType);
        if (trigger != null) {
            List<TriggerableResponse> triggerableResponses = Lists.newArrayList();
            JsonElement responsesElement = jsonObject.get("responses");
            if (responsesElement.isJsonArray()) {
                JsonArray responsesArray = responsesElement.getAsJsonArray();
                for (JsonElement response : responsesArray) {
                    triggerableResponses.add(parseResponseElement(response));
                }
                return new TriggerLoading(trigger, triggerableResponses);
            } else {
                throw new JsonParseException("responses must be an Array");
            }
        } else {
            throw new JsonParseException("Not Trigger found for type: " + triggerType.toString());
        }
    }

    private TriggerableResponse parseResponseElement(JsonElement response) {
        if (response.isJsonPrimitive()) {
            return handleResponseString(response.getAsJsonPrimitive().getAsString());
        } else if (response.isJsonObject()) {
            return handleResponseObject(response.getAsJsonObject());
        }
        throw new JsonParseException("responses Array Elements must be String or Object");
    }

    private TriggerableResponse handleResponseObject(JsonObject responseObject) {
        double weight = JSONUtils.getFloat(responseObject, "weight", 1.0F);
        List<IRunCondition> runConditions = loadConditions(responseObject.get("conditions"));
        Response response = loadResponse(responseObject.get("response"));
        return new TriggerableResponse(response, runConditions, weight);
    }

    private Response loadResponse(JsonElement responseElement) {
        Response response;
        if (responseElement.isJsonPrimitive()) {
            ResourceLocation responseName = new ResourceLocation(responseElement.getAsString());
            response = AdvisorAPI.RESPONSES.get(responseName);
            if (response == null) {
                throw new JsonParseException("Failed to find Response for " + responseName);
            }
        } else if (responseElement.isJsonObject()) {
            response = responseJsonProvider.provide(responseElement.getAsJsonObject());
        } else {
            throw new JsonParseException("response must be a String or JsonObject");
        }
        return response;
    }

    private List<IRunCondition> loadConditions(JsonElement conditions) {
        List<IRunCondition> runConditions = Lists.newArrayList();
        if (conditions != null) {
            if (conditions.isJsonArray()) {
                for (JsonElement condition : conditions.getAsJsonArray()) {
                    runConditions.add(parseCondition(condition));
                }
            } else {
                throw new JsonParseException("conditions must be an Array");
            }
        }
        return runConditions;
    }

    private IRunCondition parseCondition(JsonElement conditionElement) {
        if (conditionElement.isJsonObject()) {
            JsonObject conditionObject = conditionElement.getAsJsonObject();
            ResourceLocation type = new ResourceLocation(JSONUtils.getString(conditionObject, "type"));
            RunConditionProvider provider = AdvisorAPI.RUN_CONDITION_PROVIDERS.getValue(type);
            if (provider != null) {
                return provider.provide(conditionObject);
            } else {
                throw new JsonParseException("No Condition Provider found for type" + type.toString());
            }
        } else {
            throw new JsonParseException("conditions elements must be Objects");
        }
    }

    private TriggerableResponse handleResponseString(String string) {
        Response response = AdvisorAPI.RESPONSES.get(new ResourceLocation(string));
        if (response != null) {
            return new TriggerableResponse(response, Lists.newArrayList(), 1.0D);
        } else {
            throw new JsonParseException("No Response found for : " + string);
        }
    }
}
