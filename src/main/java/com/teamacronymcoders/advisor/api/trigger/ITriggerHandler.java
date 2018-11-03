package com.teamacronymcoders.advisor.api.trigger;

import com.teamacronymcoders.advisor.api.speech.Response;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface ITriggerHandler extends IForgeRegistryEntry<ITriggerHandler> {
    default Optional<Response> trigger(EntityPlayer entityPlayer) {
        List<Response> responses = this.getPossibleResponses(entityPlayer);
        if (!responses.isEmpty()) {
            return Optional.of(responses.get(entityPlayer.getEntityWorld().rand.nextInt(responses.size())));
        }
        return Optional.empty();
    }

    void clearResponses();

    void addResponse(Response response, @Nullable ITriggerInfo triggerInfo);

    List<Response> getAllResponses();

    default List<Response> getPossibleResponses(EntityPlayer entityPlayer) {
        return this.getAllResponses().parallelStream()
                .filter(response -> response.canRespond(entityPlayer))
                .collect(Collectors.toList());
    }
}
