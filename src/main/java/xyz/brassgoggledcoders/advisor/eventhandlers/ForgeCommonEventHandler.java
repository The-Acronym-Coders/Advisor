package xyz.brassgoggledcoders.advisor.eventhandlers;

import net.minecraft.command.Commands;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import xyz.brassgoggledcoders.advisor.Advisor;
import xyz.brassgoggledcoders.advisor.api.AdvisorAPI;
import xyz.brassgoggledcoders.advisor.command.EffectCommand;

@EventBusSubscriber(modid = Advisor.ID)
public class ForgeCommonEventHandler {

    @SubscribeEvent
    public static void addReloadListeners(AddReloadListenerEvent event) {
        event.addListener(AdvisorAPI.getEffectManager());
        event.addListener(AdvisorAPI.getCauseManager());
    }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        event.getDispatcher()
                .register(Commands.literal("advisor")
                        .then(EffectCommand.create())
                );
    }
}
