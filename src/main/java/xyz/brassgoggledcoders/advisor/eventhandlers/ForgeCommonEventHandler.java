package xyz.brassgoggledcoders.advisor.eventhandlers;

import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import xyz.brassgoggledcoders.advisor.Advisor;
import xyz.brassgoggledcoders.advisor.api.AdvisorAPI;
import xyz.brassgoggledcoders.advisor.cause.CauseManager;
import xyz.brassgoggledcoders.advisor.codec.CodecReloadListener;
import xyz.brassgoggledcoders.advisor.command.AdvisorCommand;
import xyz.brassgoggledcoders.advisor.content.AdvisorEffectTypes;
import xyz.brassgoggledcoders.advisor.effecttable.EffectTableManager;

@EventBusSubscriber(modid = Advisor.ID)
public class ForgeCommonEventHandler {

    @SubscribeEvent
    public static void addReloadListeners(AddReloadListenerEvent event) {

        AdvisorAPI.setEffectManager(new CodecReloadListener<>(AdvisorEffectTypes.DISPATCH_CODEC, "advisor/effect"));
        AdvisorAPI.setEffectTableManager(new EffectTableManager(event.getDataPackRegistries().getPredicateManager()));
        AdvisorAPI.setCauseManager(new CauseManager());

        event.addListener(AdvisorAPI.getEffectManager());
        event.addListener(AdvisorAPI.getEffectTableManager());
        event.addListener(AdvisorAPI.getCauseManager());
    }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        event.getDispatcher()
                .register(AdvisorCommand.create());
    }
}
