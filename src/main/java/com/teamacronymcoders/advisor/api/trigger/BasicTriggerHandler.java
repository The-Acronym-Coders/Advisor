package com.teamacronymcoders.advisor.api.trigger;

import com.google.common.collect.Lists;
import com.teamacronymcoders.advisor.api.speech.IResponse;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.List;

public class BasicTriggerHandler extends IForgeRegistryEntry.Impl<ITriggerHandler> implements ITriggerHandler {
    private final List<IResponse> responses;

    public BasicTriggerHandler() {
        this.responses = Lists.newArrayList();
    }

    @Override
    public void clearResponses() {
        this.responses.clear();
    }

    @Override
    public void addResponse(IResponse response, ITriggerInfo triggerInfo) {
        this.responses.add(response);
    }

    @Override
    public List<IResponse> getAllResponses() {
        return this.responses;
    }
}
