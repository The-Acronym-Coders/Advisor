package com.teamacronymcoders.advisor.json.triggerloading;

import com.teamacronymcoders.advisor.api.trigger.Trigger;
import com.teamacronymcoders.advisor.api.trigger.TriggerableResponse;

import java.util.List;

public class TriggerLoading {
    private final Trigger trigger;
    private final List<TriggerableResponse> triggerableResponses;

    public TriggerLoading(Trigger trigger, List<TriggerableResponse> triggerableResponses) {
        this.trigger = trigger;
        this.triggerableResponses = triggerableResponses;
    }

    public List<TriggerableResponse> getTriggerableResponses() {
        return triggerableResponses;
    }

    public Trigger getTrigger() {
        return trigger;
    }
}
