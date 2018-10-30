package com.teamacronymcoders.advisor.api;

import com.google.common.collect.Maps;
import com.teamacronymcoders.advisor.api.speech.IResponse;
import com.teamacronymcoders.advisor.api.trigger.ITriggerHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Map;

public class AdvisorRegistries {
    public final static IForgeRegistry<IResponse> RESPONSES = GameRegistry.findRegistry(IResponse.class);
    public final static IForgeRegistry<ITriggerHandler> TRIGGER_HANDLERS = GameRegistry.findRegistry(ITriggerHandler.class);
    public final static Map<String, Class> RESPONSE_CLASS = Maps.newHashMap();
}
