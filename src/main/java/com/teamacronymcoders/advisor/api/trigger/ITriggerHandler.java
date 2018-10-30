package com.teamacronymcoders.advisor.api.trigger;

import com.teamacronymcoders.advisor.api.speech.IResponse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface ITriggerHandler extends IForgeRegistryEntry<ITriggerHandler> {
    default Optional<IResponse> trigger(EntityPlayer entityPlayer) {
        List<IResponse> responses = this.getPossibleResponses(entityPlayer);
        if (!responses.isEmpty()) {
            return Optional.of(responses.get(entityPlayer.getEntityWorld().rand.nextInt(responses.size())));
        }
        return Optional.empty();
    }

    void clearResponses();

    void addResponse(IResponse response, @Nullable ITriggerInfo triggerInfo);

    List<IResponse> getAllResponses();

    default List<IResponse> getPossibleResponses(EntityPlayer entityPlayer) {
        return this.getAllResponses().parallelStream()
                .filter(response -> response.canRespond(entityPlayer))
                .collect(Collectors.toList());
    }
}
