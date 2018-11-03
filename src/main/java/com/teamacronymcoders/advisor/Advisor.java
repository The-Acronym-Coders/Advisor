package com.teamacronymcoders.advisor;

import com.teamacronymcoders.advisor.api.AdvisorRegistries;
import com.teamacronymcoders.advisor.api.data.CapAdvisorData;
import com.teamacronymcoders.advisor.api.speech.Response;
import com.teamacronymcoders.advisor.api.trigger.ITriggerHandler;
import com.teamacronymcoders.advisor.api.trigger.Trigger;
import com.teamacronymcoders.advisor.command.RespondCommand;
import com.teamacronymcoders.advisor.json.JsonLoader;
import com.teamacronymcoders.advisor.json.ResponseDeserializer;
import com.teamacronymcoders.advisor.network.ResponseHandler;
import com.teamacronymcoders.advisor.network.ResponsePacket;
import com.teamacronymcoders.advisor.proxy.IProxy;
import com.teamacronymcoders.advisor.speech.sound.SoundResponse;
import com.teamacronymcoders.advisor.speech.text.LangResponse;
import com.teamacronymcoders.base.BaseModFoundation;
import com.teamacronymcoders.base.command.CommandSubBase;
import com.teamacronymcoders.base.registrysystem.SoundEventRegistry;
import com.teamacronymcoders.base.sound.CustomSoundEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.server.command.CommandTreeBase;

import java.util.Map;

import static com.teamacronymcoders.advisor.Advisor.*;

@Mod(modid = ID, name = NAME, version = VERSION, dependencies = DEPENDENCIES)
public class Advisor extends BaseModFoundation<Advisor> {
    public static final String ID = "advisor";
    public static final String NAME = "Advisor";
    public static final String VERSION = "@VERSION@";
    public static final String DEPENDENCIES = "required-after:base@[0.0.0,)";

    @Instance
    public static Advisor instance;
    @SidedProxy(clientSide = "com.teamacronymcoders.advisor.proxy.ClientProxy",
            serverSide = "com.teamacronymcoders.advisor.proxy.ServerProxy")
    public static IProxy proxy;
    public final JsonLoader<Response> responseLoader = new JsonLoader<>("/advisor/responses", Response.class,
            () -> this.getLogger().getLogger(), new ResponseDeserializer());
    public final JsonLoader<Trigger> triggerLoader = new JsonLoader<>("/advisor/triggers", Trigger.class,
            () -> this.getLogger().getLogger());
    private final CommandTreeBase advisorCommand = new CommandSubBase(ID);

    public Advisor() {
        super(ID, NAME, VERSION, CreativeTabs.MISC);
    }

    @Override
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        this.getPacketHandler().registerPacket(ResponseHandler.class, ResponsePacket.class, Side.CLIENT);
        AdvisorRegistries.RESPONSE_CLASS.put("advisor:sound", SoundResponse.class);
        AdvisorRegistries.RESPONSE_CLASS.put("advisor:lang", LangResponse.class);
        CapAdvisorData.register();
    }

    @Override
    public void afterModuleHandlerInit(FMLPreInitializationEvent event) {
        super.afterModuleHandlerInit(event);
        this.getRegistry(SoundEventRegistry.class, "SOUND_EVENT")
                .register(new ResourceLocation("advisor", "intro_voice"),
                        new CustomSoundEvent(new ResourceLocation("advisor", "intro_voice")));
    }

    @Override
    @EventHandler
    public void init(FMLInitializationEvent event) {
        super.init(event);
        triggerLoader.load().parallelStream()
                .map(Map.Entry::getValue)
                .forEach(trigger -> {
                    ITriggerHandler handler = AdvisorRegistries.TRIGGER_HANDLERS.getValue(trigger.triggerHandler);
                    if (handler != null) {
                        for (ResourceLocation responseName : trigger.responses) {
                            Response response = AdvisorRegistries.RESPONSES.getValue(responseName);
                            if (response != null) {
                                handler.addResponse(response, trigger.triggerInfo);
                            } else {
                                this.getLogger().warning("Failed to Find Response with Name " + responseName.toString());
                            }
                        }
                    } else {
                        this.getLogger().warning("Failed to Find Trigger Handler with Name " + trigger.triggerHandler.toString());
                    }
                });
    }

    @Override
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    public Advisor getInstance() {
        return this;
    }

    @Override
    public boolean hasExternalResources() {
        return true;
    }

    @EventHandler
    public void serverInit(FMLServerStartingEvent event) {
        this.advisorCommand.addSubcommand(new RespondCommand());
        event.registerServerCommand(this.advisorCommand);
    }
}
