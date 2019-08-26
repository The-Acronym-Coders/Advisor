package com.teamacronymcoders.advisor.api.trigger;

import com.teamacronymcoders.advisor.api.response.Response;
import com.teamacronymcoders.advisor.api.runcondition.IRunCondition;
import com.teamacronymcoders.advisor.api.weightedlist.IWeightedItem;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;

public class TriggerableResponse implements IWeightedItem<TriggerableResponse> {
    private final Response response;
    private final List<IRunCondition> conditions;
    private final double weight;

    public TriggerableResponse(Response response, List<IRunCondition> conditions, double weight) {
        this.response = response;
        this.conditions = conditions;
        this.weight = weight;
    }

    public boolean meetsConditions(PlayerEntity playerEntity) {
        return isMet(playerEntity);
    }

    public Response getResponse() {
        return response;
    }

    public Response handleResponse(PlayerEntity playerEntity) {
        return meetsConditions(playerEntity) ? response : null;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public TriggerableResponse getItem() {
        return this;
    }

    @Override
    public boolean isConditional() {
        return !conditions.isEmpty();
    }

    @Override
    public boolean isMet(PlayerEntity playerEntity) {
        return conditions.stream().allMatch(condition -> condition.isMet(playerEntity));
    }
}
