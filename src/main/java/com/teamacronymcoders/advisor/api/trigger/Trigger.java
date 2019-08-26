package com.teamacronymcoders.advisor.api.trigger;

import com.teamacronymcoders.advisor.api.response.Response;
import com.teamacronymcoders.advisor.api.weightedlist.WeightedList;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class Trigger extends ForgeRegistryEntry<Trigger> {
    private final WeightedList<TriggerableResponse, TriggerableResponse> list;

    public Trigger() {
        this.list = new WeightedList<>();
    }

    @Nullable
    public Response trigger(PlayerEntity playerEntity) {
        TriggerableResponse triggerableResponse = this.list.next(playerEntity);
        if (triggerableResponse != null) {
            return triggerableResponse.handleResponse(playerEntity);
        }
        return null;
    }

    public void add(TriggerableResponse triggerableResponse) {
        list.add(triggerableResponse);
    }

    public void clear() {
        list.clear();
    }
}
