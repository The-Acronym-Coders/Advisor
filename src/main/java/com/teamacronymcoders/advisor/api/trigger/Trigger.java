package com.teamacronymcoders.advisor.api.trigger;

import com.google.gson.annotations.JsonAdapter;
import com.teamacronymcoders.advisor.json.constructor.ConstructorDeserializer;
import com.teamacronymcoders.advisor.json.constructor.JsonConstructor;
import com.teamacronymcoders.advisor.json.constructor.JsonProperty;
import net.minecraft.util.ResourceLocation;

@JsonAdapter(ConstructorDeserializer.class)
public class Trigger {
    public final ResourceLocation triggerHandler;
    public final ResourceLocation[] responses;
    public final ITriggerInfo triggerInfo;

    @JsonConstructor
    public Trigger(@JsonProperty(value = "triggerHandler", required = true) ResourceLocation triggerHandler,
                   @JsonProperty(value = "responses", required = true) ResourceLocation[] responses,
                   @JsonProperty(value = "triggerInfo") ITriggerInfo triggerInfo) {
        this.triggerHandler = triggerHandler;
        this.responses = responses;
        this.triggerInfo = triggerInfo;
    }
}
