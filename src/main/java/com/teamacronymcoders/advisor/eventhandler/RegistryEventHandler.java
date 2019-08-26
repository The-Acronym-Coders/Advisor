package com.teamacronymcoders.advisor.eventhandler;

import com.teamacronymcoders.advisor.Advisor;
import com.teamacronymcoders.advisor.api.data.AdvisorDataProvider;
import com.teamacronymcoders.advisor.api.data.CapAdvisorData;
import com.teamacronymcoders.advisor.api.response.ResponseProvider;
import com.teamacronymcoders.advisor.api.runcondition.RunConditionProvider;
import com.teamacronymcoders.advisor.api.trigger.Trigger;
import com.teamacronymcoders.advisor.response.text.TextComponentResponseProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.RegistryBuilder;

@EventBusSubscriber(modid = Advisor.ID, bus = Bus.MOD)
public class RegistryEventHandler {
    @SubscribeEvent
    public static void createRegistries(RegistryEvent.NewRegistry newRegistry) {
        new RegistryBuilder<ResponseProvider>()
                .setType(ResponseProvider.class)
                .setName(new ResourceLocation(Advisor.ID, "response_providers"))
                .create();

        new RegistryBuilder<RunConditionProvider>()
                .setType(RunConditionProvider.class)
                .setName(new ResourceLocation(Advisor.ID, "run_condition_providers"))
                .create();

        new RegistryBuilder<Trigger>()
                .setType(Trigger.class)
                .setName(new ResourceLocation(Advisor.ID, "triggers"))
                .create();
    }

    @SubscribeEvent
    public static void registerResponseProviders(RegistryEvent.Register<ResponseProvider> event) {
        event.getRegistry().registerAll(
                new TextComponentResponseProvider().setRegistryName(new ResourceLocation(Advisor.ID, "text_component"))
        );
    }

    @SubscribeEvent
    public static void registerTriggers(RegistryEvent.Register<Trigger> event) {
        event.getRegistry().registerAll(
                new Trigger().setRegistryName(new ResourceLocation(Advisor.ID, "intro")),
                new Trigger().setRegistryName(new ResourceLocation(Advisor.ID, "death"))
        );
    }

    @SubscribeEvent
    public static void registerCap(AttachCapabilitiesEvent<Entity> attachCapabilitiesEvent) {
        if (attachCapabilitiesEvent.getObject() instanceof PlayerEntity) {
            attachCapabilitiesEvent.addCapability(new ResourceLocation(Advisor.ID, "advisor_data"), new AdvisorDataProvider());
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(CapAdvisorData.ADVISOR_DATA).ifPresent(oldStore ->
                    event.getPlayer().getCapability(CapAdvisorData.ADVISOR_DATA)
                            .ifPresent(newStore -> newStore.deserializeNBT(oldStore.serializeNBT())));

        }
    }
}
