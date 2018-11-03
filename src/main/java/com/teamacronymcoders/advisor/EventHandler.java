package com.teamacronymcoders.advisor;

import com.teamacronymcoders.advisor.api.data.AdvisorDataProvider;
import com.teamacronymcoders.advisor.api.data.CapAdvisorData;
import com.teamacronymcoders.advisor.api.speech.Response;
import com.teamacronymcoders.advisor.api.trigger.ITriggerHandler;
import com.teamacronymcoders.base.util.CapabilityUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.RegistryBuilder;

@EventBusSubscriber(modid = Advisor.ID)
public class EventHandler {
    @SubscribeEvent
    public static void createRegistries(RegistryEvent.NewRegistry newRegistry) {
        new RegistryBuilder<Response>()
                .allowModification()
                .setType(Response.class)
                .setName(new ResourceLocation(Advisor.ID, "responses"))

                .create();

        new RegistryBuilder<ITriggerHandler>()
                .setType(ITriggerHandler.class)
                .setName(new ResourceLocation(Advisor.ID, "trigger_handlers"))
                .create();
    }

    @SubscribeEvent
    public static void registerResponses(RegistryEvent.Register<Response> responseRegister) {
        Advisor.instance.responseLoader.load()
                .forEach(value -> responseRegister.getRegistry().register(value.getValue()));
    }

    @SubscribeEvent
    public static void registerCap(AttachCapabilitiesEvent<Entity> attachCapabilitiesEvent) {
        if (attachCapabilitiesEvent.getObject() instanceof EntityPlayer) {
            attachCapabilitiesEvent.addCapability(new ResourceLocation(Advisor.ID, "advisor_data"), new AdvisorDataProvider());
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            CapabilityUtils.getCapability(event.getOriginal(), CapAdvisorData.ADVISOR_DATA).ifPresent(oldStore ->
                    CapabilityUtils.getCapability(event.getEntityPlayer(), CapAdvisorData.ADVISOR_DATA)
                            .ifPresent(newStore -> newStore.loadFromNBT(oldStore.saveToNBT())));

        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event) {
        CapabilityUtils.getCapability(event.player, CapAdvisorData.ADVISOR_DATA)
                .filter(iAdvisorData -> !iAdvisorData.hasReceivedIntro())
                .ifPresent(advisorData -> {
                    AdvisorTriggers.INTRO_HANDLER.trigger(event.player)
                            .ifPresent(response -> response.respond(event.player, event.player.getEntityWorld().isRemote));
                    advisorData.setHasReceivedIntro(true);
                });
    }
}
