package com.teamacronymcoders.advisor;

import com.hrznstudio.titanium.module.ModuleController;
import com.teamacronymcoders.advisor.api.AdvisorAPI;
import com.teamacronymcoders.advisor.api.data.CapAdvisorData;
import com.teamacronymcoders.advisor.command.AdvisorCommand;
import com.teamacronymcoders.advisor.json.ResponseJsonProvider;
import com.teamacronymcoders.advisor.json.loader.JsonLoader;
import com.teamacronymcoders.advisor.json.loader.MapJsonDirector;
import com.teamacronymcoders.advisor.json.triggerloading.TriggerLoadingJsonDirector;
import com.teamacronymcoders.advisor.json.triggerloading.TriggerLoadingProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.teamacronymcoders.advisor.Advisor.ID;

@Mod(ID)
public class Advisor extends ModuleController {
    public static final String ID = "advisor";
    public static final Logger LOGGER = LogManager.getLogger(ID);

    public Advisor() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.addListener(this::serverStart);
    }

    @Override
    protected void initModules() {

    }

    public void commonSetup(FMLCommonSetupEvent event) {
        CapAdvisorData.register();
    }

    public void serverStart(FMLServerAboutToStartEvent event) {
        event.getServer().getResourceManager().addReloadListener(
                new JsonLoader<>("advisor/responses", LOGGER,
                        new MapJsonDirector<>(AdvisorAPI.RESPONSES),
                        new ResponseJsonProvider()));

        event.getServer().getResourceManager().addReloadListener(
                new JsonLoader<>("advisor/triggers", LOGGER,
                        new TriggerLoadingJsonDirector(),
                        new TriggerLoadingProvider()));

        event.getServer().getCommandManager().getDispatcher().register(AdvisorCommand.get());
    }
}
