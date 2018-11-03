package com.teamacronymcoders.advisor;

import com.teamacronymcoders.advisor.api.trigger.BasicTriggerHandler;
import com.teamacronymcoders.advisor.api.trigger.ITriggerHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

public class AdvisorTriggers {
    public static final ITriggerHandler INTRO_HANDLER = new BasicTriggerHandler()
            .setRegistryName(new ResourceLocation(Advisor.ID, "introduction"));
    public static final ITriggerHandler DEATH_HANDLER = new BasicTriggerHandler()
            .setRegistryName(new ResourceLocation(Advisor.ID, "on_death"));

    public static void registerTriggerHandlers(IForgeRegistry<ITriggerHandler> forgeRegistry) {
        forgeRegistry.register(INTRO_HANDLER);
        forgeRegistry.register(DEATH_HANDLER);
    }
}
