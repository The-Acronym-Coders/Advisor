package com.teamacronymcoders.advisor;

import com.teamacronymcoders.advisor.api.speech.IResponse;
import com.teamacronymcoders.advisor.api.trigger.ITriggerHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.RegistryBuilder;

@EventBusSubscriber(modid = Advisor.ID)
public class EventHandler {
    @SubscribeEvent
    public static void createRegistries(RegistryEvent.NewRegistry newRegistry) {
        new RegistryBuilder<IResponse>()
                .allowModification()
                .setType(IResponse.class)
                .setName(new ResourceLocation(Advisor.ID, "responses"))

                .create();

        new RegistryBuilder<ITriggerHandler>()
                .setType(ITriggerHandler.class)
                .setName(new ResourceLocation(Advisor.ID, "trigger_handlers"))
                .create();
    }

    @SubscribeEvent
    public static void registerResponses(RegistryEvent.Register<IResponse> responseRegister) {
        Advisor.instance.responseLoader.load()
                .forEach(responseRegister.getRegistry()::register);
    }
}
