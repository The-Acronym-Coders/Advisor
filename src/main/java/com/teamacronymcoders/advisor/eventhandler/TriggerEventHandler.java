package com.teamacronymcoders.advisor.eventhandler;

import com.teamacronymcoders.advisor.Advisor;
import com.teamacronymcoders.advisor.api.data.CapAdvisorData;
import com.teamacronymcoders.advisor.api.response.Response;
import com.teamacronymcoders.advisor.api.trigger.Trigger;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ObjectHolder;

@EventBusSubscriber(modid = Advisor.ID)
@ObjectHolder(Advisor.ID)
public class TriggerEventHandler {
    @ObjectHolder("intro")
    private static Trigger introHandler;
    @ObjectHolder("death")
    private static Trigger deathHandler;

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        event.getPlayer().getCapability(CapAdvisorData.ADVISOR_DATA)
                .filter(iAdvisorData -> !iAdvisorData.hasReceivedIntro())
                .ifPresent(advisorData -> {
                    doTrigger(introHandler, event.getPlayer());
                    advisorData.setHasReceivedIntro(true);
                });
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            doTrigger(deathHandler, player);
        }
    }

    private static void doTrigger(Trigger trigger, PlayerEntity player) {
        Response response = trigger.trigger(player);
        if (response != null) {
            response.respond(player, player.getEntityWorld().isRemote());
        }
    }
}
