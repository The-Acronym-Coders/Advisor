package xyz.brassgoggledcoders.advisor.eventhandlers;

import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import xyz.brassgoggledcoders.advisor.Advisor;
import xyz.brassgoggledcoders.advisor.json.EffectManager;

@EventBusSubscriber(modid = Advisor.ID)
public class ForgeCommonEventHandler {

    @SubscribeEvent
    public static void addReloadListeners(AddReloadListenerEvent event) {
        EffectManager effectManager = new EffectManager();
        event.addListener(effectManager);
    }
}
