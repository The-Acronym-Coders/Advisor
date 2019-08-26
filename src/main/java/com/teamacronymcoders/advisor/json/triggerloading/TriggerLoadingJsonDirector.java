package com.teamacronymcoders.advisor.json.triggerloading;

import com.teamacronymcoders.advisor.api.AdvisorAPI;
import com.teamacronymcoders.advisor.api.trigger.Trigger;
import com.teamacronymcoders.advisor.json.loader.IJsonDirector;
import net.minecraft.util.ResourceLocation;

public class TriggerLoadingJsonDirector implements IJsonDirector<TriggerLoading> {
    @Override
    public void put(ResourceLocation resourceLocation, TriggerLoading value) {
        value.getTriggerableResponses()
                .forEach(value.getTrigger()::add);
    }

    @Override
    public void clear() {
        AdvisorAPI.TRIGGERS.getValues()
                .forEach(Trigger::clear);
    }
}
